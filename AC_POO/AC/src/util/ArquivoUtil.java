package util;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import entidades.Produto;
import entidades.Venda;
import gestor.Gestor;

public class ArquivoUtil {

    private static final String FILE_PATH = "cadastro.txt";
    private static final String VENDAS_FILE_PATH = "vendas.txt";

    public static boolean codigoProdutoExiste(int codigo) {
        ensureFileExists(FILE_PATH); // Garante que o arquivo existe
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int cod = Integer.parseInt(parts[0]);
                if (cod == codigo) {
                    return true; // Código encontrado
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Código não encontrado
    }

    public static void adicionarProduto(Produto produto) {
        ensureFileExists(FILE_PATH); // Garante que o arquivo existe
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(produto.getCodigo() + "," + produto.getDescricao() + "," + produto.getPreco() + "," + produto.getQntdEstoque());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void carregarProdutos(List<Produto> produtos) {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return; // Arquivo ainda não existe, então não há produtos para carregar
        }
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int codigo = Integer.parseInt(parts[0]);
                String descricao = parts[1];
                double preco = Double.parseDouble(parts[2]);
                int qntdEstoque = Integer.parseInt(parts[3]);
                Produto produto = new Produto(codigo, descricao, preco, qntdEstoque);
                produtos.add(produto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void atualizarArquivoProdutos(List<Produto> produtos) {
        ensureFileExists(FILE_PATH); // Garante que o arquivo existe
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Produto produto : produtos) {
                bw.write(produto.getCodigo() + "," + produto.getDescricao() + "," + produto.getPreco() + "," + produto.getQntdEstoque());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void carregarVendas(List<Venda> vendas, List<Produto> produtos) {
        Gestor gestor = new Gestor();
        File file = new File(VENDAS_FILE_PATH);
        if (!file.exists()) {
            return; // Arquivo ainda não existe, então não há vendas para carregar
        }
        try (BufferedReader br = new BufferedReader(new FileReader(VENDAS_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                LocalDate data = LocalDate.parse(parts[0]);
                String nome = parts[1];
                Map<Produto, Integer> itens = new HashMap<>();
                int codigoIndex = line.indexOf("Código: ") + "Código: ".length();
                int descricaoIndex = line.indexOf("Descrição: ");
                int quantidade;
                int quantidadeIndex = line.indexOf("=") + 1;
                int indiceFechamentoChave = line.indexOf("}");
                if (indiceFechamentoChave != -1) {
                    quantidade = Integer.parseInt(line.substring(quantidadeIndex, indiceFechamentoChave));
                } else {
                    // Lida com o caso em que o caractere '}' não é encontrado na linha
                    quantidade = 0; // ou qualquer outro valor padrão adequado
                }

                String codigoSubstring = line.substring(codigoIndex, descricaoIndex).trim();
                int codigoProduto = Integer.parseInt(codigoSubstring);
                Produto produto = gestor.encontrarProdutoPorCodigo(produtos, codigoProduto);
                if (produto != null) {
                    itens.put(produto, quantidade);
                }
                Venda venda = new Venda(data, nome);
                venda.setItens(itens);
                vendas.add(venda);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void adicionarVenda(Venda venda) {
        ensureFileExists(VENDAS_FILE_PATH); // Garante que o arquivo existe
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(VENDAS_FILE_PATH, true))) {
            bw.write(venda.getData() + "," + venda.getNome() + "," + venda.getItens());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void atualizarArquivoVendas(List<Venda> vendas) {
        ensureFileExists(VENDAS_FILE_PATH); // Garante que o arquivo existe
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(VENDAS_FILE_PATH))) {
            for (Venda venda : vendas) {
                bw.write(venda.getData() + "," + venda.getNome() + "," + venda.getItens());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ensureFileExists(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
