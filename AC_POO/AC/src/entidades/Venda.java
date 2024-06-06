package entidades;
 
// Importação das bibliotecas cujas ferramentas serão necessárias para o funcionamento do código
import java.time.LocalDate; // Biblioteca para registrar data de um acontecimento (no caso, da venda)
import java.util.HashMap; // Facilita a visualização da interface
import java.util.Map; // O MAP permite dar um valor para a chave
 
// Criação da classe Venda
public class Venda {
   
    private LocalDate data; // Declaração do atributo data da venda
    private String nome; // Declaração do atributo nome do cliente que realizou a compra
    private Map<Produto, Integer> itens; // Declaração do atributo do tipo array para armazenar os itens de uma compra ("carrinho")
 
    // Criação do construtor que possibilita a criação dos objetos da classe Venda, instanciando-a 
    public Venda(LocalDate data, String nome) {
        this.data = data; 
        this.nome = nome;
        this.itens = new HashMap<>();
    }

    // GETTERS E SETTERS - métodos para ver ou mudar a data, o nome do cliente, ou a lista de coisas no carrinho
 
    public LocalDate getData() {
        return this.data;
    }
 
    public void setData(LocalDate novaData) {
        this.data = novaData;
    }
 
    public String getNome() {
        return this.nome;
    }
 
    public void setNome(String novoNome) {
        this.nome = novoNome;
    }
 
    public Map<Produto, Integer> getItens() {
        return this.itens;
    }
 
    public void setItens(Map<Produto, Integer> novosItens) {
        this.itens = novosItens;
    }
 
    // Adicionar um novo item à venda com uma determinada quantidade
    public void adicionarItem(Produto produto, int quantidade) {
        if (this.itens.containsKey(produto)) { // Verifica se o produto já está na lista de compras ("carrinho")
            quantidade += this.itens.get(produto); // Se o produto já está no "carrinho", pegamos a quantidade que já está lá (this.itens.get(produto)) e somamos com a nova quantidade que queremos adicionar (quantidade += ...)

        }
        this.itens.put(produto, quantidade); // Adiciona o produto com a quantidade correta ao carrinho. Se o produto já estava lá, atualiza a quantidade
    }
 
    // Remover um item da venda
    public void removerItem(Produto produto) {
        this.itens.remove(produto); // Procura o produto na lista de itens da venda e o remove da lista
    }
 
    // Método para calcular o total da venda
    public double calcularTotal() {
        double total = 0; // Variável iniciada em 0 que armazena o valor total da venda
        for (Map.Entry<Produto, Integer> entry : this.itens.entrySet()) { // Gera uma lista de todos os produtos no carrinho e quantas unidades de cada um
            Produto produto = entry.getKey(); //  Dá o produto atual do carrinho
            int quantidade = entry.getValue(); // Dá a quantidade desse produto no carrinho
            total += produto.getPreco() * quantidade; // Multiplica o preço do produto (produto.getPreco()) pela quantidade que está sendo comprada e adiciona esse valor ao total
        }
        return total; // Depois de passar por todos os produtos no carrinho e somar os preços, retorna o valor total
    }
 
    // toString - como um recibo, que mostra a data, seu nome, o que tem no carrinho, e o total a pagar
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); // ferramenta usada para construir o texto do recibo eficientemente. sb é como uma "folha de papel" onde será escrito o "recibo"
        sb.append("Data: ").append(this.data).append("\n"); // Escreve "Data: " e a data da compra no "recibo"
        sb.append("Nome: ").append(this.nome).append("\n"); // Escreve "Nome: " e o nome do cliente no "recibo"
        sb.append("Itens:\n"); // Escreve "Itens:" para começar a lista de produtos no "recibo"
        for (Map.Entry<Produto, Integer> entry : this.itens.entrySet()) { //  Dá uma lista de todos os produtos no carrinho e quantas unidades de cada um
            Produto produto = entry.getKey(); // Dá produto (Produto) atual do carrinho
            int quantidade = entry.getValue(); // Dá a quantidade desse produto no carrinho
            sb.append("- ").append(produto.getDescricao()).append(" (").append(quantidade).append(" unidade(s))\n"); // Escreve o nome do produto, a quantidade e a palavra "unidade(s)" no recibo
        }
        sb.append("Total: R$").append(calcularTotal()); // Calcula o valor total de todos os produtos usando o método calcularTotal e escrevem no recibo
        return sb.toString(); // Converte o StringBuilder em uma string completa e retorna essa string
    }
 
}
