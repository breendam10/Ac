package gestor;
 
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import entidades.Venda;
import entidades.Produto;
import util.ArquivoUtil;
 
public class Gestor {
   
    private List<Produto> produtos;
    private List<Venda> vendas;
    private Scanner scanner;
 
    public Gestor() {
        this.produtos = new ArrayList<>();
        this.vendas = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }
 
    public void exibirMenu() {
        System.out.println("===== E-commerce =====");
        System.out.println("1. Cadastrar Produto");
        System.out.println("2. Inventário de Produtos");
        System.out.println("3. Realizar Venda");
        System.out.println("4. Alterar produto");
        System.out.println("5. Relatório de Vendas Geral");
        System.out.println("6. Relatório de Vendas do Dia");
        System.out.println("7. Relatório Itens Mais e Menos Vendidos");
        System.out.println("8. Relatório Clientes Que Mais Compram");
        System.out.println("9. Sair");
        System.out.println("================");
    }
 
    public void cadastrarProduto() {
        System.out.println("===== Cadastro de Produto =====");
        System.out.print("Código: ");
        int codigo = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
   
        // Verifica se o código já existe no arquivo
        if (ArquivoUtil.codigoProdutoExiste(codigo)) {
            System.out.println("Já existe um produto com esse código.");
            return;
        }
   
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("Preço: ");
        double preco = scanner.nextDouble();
        System.out.print("Quantidade em Estoque: ");
        int qntdEstoque = scanner.nextInt();
   
        Produto produto = new Produto(codigo, descricao, preco, qntdEstoque);
        produtos.add(produto);
   
        // Adiciona o produto ao arquivo
        ArquivoUtil.adicionarProduto(produto);
   
        System.out.println("Produto cadastrado com sucesso!\n");
    }
 
    public void listarProdutos() {
        System.out.println("===== Lista de Produtos =====");
        for (Produto produto : produtos) {
            System.out.println(produto);
            System.out.println("----------------");
        }
    }
 
    public void alterarProduto() {
        System.out.println("===== Alterar Produto =====");
        System.out.print("Digite o código do produto que deseja alterar: ");
        int codigoProduto = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha pendente
        Produto produto = encontrarProdutoPorCodigo(produtos, codigoProduto);
        if (produto == null) {
            System.out.println("Produto não encontrado.");
            return;
        }
   
        System.out.println("Produto encontrado:");
        System.out.println(produto);
        System.out.println("----------------");
   
        System.out.println("Digite as novas informações (ou deixe em branco para manter):");
   
        System.out.print("Nova Descrição: ");
        String novaDescricao = scanner.nextLine();
        if (!novaDescricao.isEmpty()) {
            produto.setDescricao(novaDescricao);
        }
   
        System.out.print("Novo Preço: ");
        String novoPrecoStr = scanner.nextLine();
        if (!novoPrecoStr.isEmpty()) {
            double novoPreco = Double.parseDouble(novoPrecoStr);
            produto.setPreco(novoPreco);
        }
   
        System.out.print("Nova Quantidade em Estoque: ");
        String novaQuantidadeStr = scanner.nextLine();
        if (!novaQuantidadeStr.isEmpty()) {
            int novaQuantidade = Integer.parseInt(novaQuantidadeStr);
            produto.setQuantidade(novaQuantidade);
        }
   
        ArquivoUtil.atualizarArquivoProdutos(produtos);
        ArquivoUtil.atualizarArquivoVendas(vendas);
        System.out.println("Produto alterado com sucesso!\n");
    }
   
   
   
 
    public void realizarVenda() {
        System.out.println("===== Realizar Venda =====");
        System.out.print("Nome do Cliente: ");
        String nomeCliente = scanner.nextLine();
        LocalDate data = LocalDate.now();
        Venda venda = new Venda(data, nomeCliente);
 
        boolean continuarVenda = true;
        while (continuarVenda) {
            System.out.println("===== Adicionar Item à Venda =====");
            System.out.print("Código do Produto: ");
            int codigoProduto = scanner.nextInt();
            Produto produto = encontrarProdutoPorCodigo(produtos, codigoProduto);
            if (produto == null) {
                System.out.println("Produto não encontrado.");
                continue;
            }
            System.out.print("Quantidade: ");
            int quantidade = scanner.nextInt();
            if (quantidade > produto.getQntdEstoque()) {
                System.out.println("Quantidade indisponível em estoque.");
                continue;
            }
            venda.adicionarItem(produto, quantidade);
            produto.setQuantidade(produto.getQntdEstoque() - quantidade);
 
            System.out.print("Deseja adicionar mais itens à venda? (S/N): ");
            String resposta = scanner.next();
            continuarVenda = resposta.equalsIgnoreCase("S");
        }
 
        vendas.add(venda);
        ArquivoUtil.atualizarArquivoProdutos(produtos);
        ArquivoUtil.atualizarArquivoVendas(vendas);
        System.out.println("Venda realizada com sucesso!\n");
    }
 
    public void listarVendas() {
        System.out.println("===== Lista de Vendas =====");
        for (Venda venda : vendas) {
            System.out.println(venda);
            System.out.println("----------------");
        }
    }
 
    public Produto encontrarProdutoPorCodigo(List<Produto> produtos, int codigo) {
        for (Produto produto : produtos) {
            if (produto.getCodigo() == codigo) {
                return produto;
            }
        }
        return null;
    }
 
    public void iniciar() {
        ArquivoUtil.carregarProdutos(produtos);
        ArquivoUtil.carregarVendas(vendas, produtos);
        int opcao;
        do {
            exibirMenu();
            System.out.print("Digite a opção desejada: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
 
            switch (opcao) {
                case 1:
                    cadastrarProduto();
                    break;
                case 2:
                    listarProdutos();
                    break;
                case 3:
                    realizarVenda();
                    break;
                case 4:
                    alterarProduto();
                    break;
                case 5:
                    listarVendas();
                    break;
                case 6:
                    relatorioVendasDoDia();
                    break;                   
                case 7:
                    relatorioItensMaisEMenosVendidos();
                    break;
                case 8:
                    relatorioClientesQueMaisCompram();
                    break;
                case 9:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 9);
 
        scanner.close();
    }

    public List<Venda> relatorioVendasDooDia(LocalDate data) {
        return vendas.stream()
                        .filter(venda -> venda.getData().equals(data))
                        .toList();
    }

    public void relatorioVendasDoDia() {
        System.out.print("Digite a data no formato AAAA-MM-DD: ");
        String dataStr = scanner.nextLine();
        LocalDate data = LocalDate.parse(dataStr);
        List<Venda> vendasDia = relatorioVendasDooDia(data);
        if (vendasDia.isEmpty()) {
            System.out.println("Nenhuma venda realizada nesta data.");
        } else {
            System.out.println("===== Relatório de Vendas do Dia =====");
            for (Venda venda : vendasDia) {
                System.out.println(venda);
                System.out.println("----------------");
            }
        }
    }



    public Map<Produto, Integer> relatorioItenssMaisEMenosVendidos() {
        Map<Produto, Integer> vendasPorProduto = new HashMap<>();
        for (Venda venda : vendas) {
            for (Map.Entry<Produto, Integer> entry : venda.getItens().entrySet()) {
                Produto produto = entry.getKey();
                int quantidade = entry.getValue();
                vendasPorProduto.merge(produto, quantidade, Integer::sum);
            }
        }
        return vendasPorProduto;
    }

    public void relatorioItensMaisEMenosVendidos() {
        Map<Produto, Integer> relatorioItens = relatorioItenssMaisEMenosVendidos();
        if (relatorioItens.isEmpty()) {
            System.out.println("Nenhuma venda realizada ainda.");
            return;
        }
        System.out.println("===== Relatório de Itens Mais e Menos Vendidos =====");
        System.out.println("Itens mais vendidos:");
        // Print products with the highest sales
        relatorioItens.entrySet().stream()
                                     .max(Map.Entry.comparingByValue())
                                     .ifPresent(entry -> System.out.println(entry.getKey() + " - " + entry.getValue()));
    
        System.out.println("Itens menos vendidos:");
        // Print products with the lowest sales
        relatorioItens.entrySet().stream()
                                     .min(Map.Entry.comparingByValue())
                                     .ifPresent(entry -> System.out.println(entry.getKey() + " - " + entry.getValue()));
    }

    public String relatorioClientesQueMaisCoompram() {
        Map<String, Double> totalCompraPorCliente = new HashMap<>();
        for (Venda venda : vendas) {
            String cliente = venda.getNome();
            double totalCompra = venda.calcularTotal();
            totalCompraPorCliente.merge(cliente, totalCompra, Double::sum);
        }

        // Find the customer(s) with the highest total purchase amount
        double maxTotal = totalCompraPorCliente.values().stream()
                                                .max(Double::compareTo)
                                                .orElse(0.0);
        StringBuilder result = new StringBuilder();
        totalCompraPorCliente.forEach((cliente, totalCompra) -> {
            if (totalCompra == maxTotal) {
                result.append(cliente).append(" (").append(totalCompra).append(")\n");
            }
        });
        return result.toString();
    }

    public void relatorioClientesQueMaisCompram() {
        String clienteMaisCompras = relatorioClientesQueMaisCoompram();
        if (clienteMaisCompras.isEmpty()) {
            System.out.println("Nenhuma venda realizada ainda.");
        } else {
            System.out.println("===== Relatório de Clientes que Mais Compram =====");
            System.out.println(clienteMaisCompras);
        }
    }
 
}
 