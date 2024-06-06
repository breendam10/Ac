package entidades;
 
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
 
public class Venda {
   
    private LocalDate data;
    private String nome;
    private Map<Produto, Integer> itens;
 
    public Venda(LocalDate data, String nome) {
        this.data = data;
        this.nome = nome;
        this.itens = new HashMap<>();
    }
 
    // GETTERS E SETTERS
 
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
        if (this.itens.containsKey(produto)) {
            quantidade += this.itens.get(produto);
        }
        this.itens.put(produto, quantidade);
    }
 
    // Remover um item da venda
    public void removerItem(Produto produto) {
        this.itens.remove(produto);
    }
 
    // Método para calcular o total da venda
    public double calcularTotal() {
        double total = 0;
        for (Map.Entry<Produto, Integer> entry : this.itens.entrySet()) {
            Produto produto = entry.getKey();
            int quantidade = entry.getValue();
            total += produto.getPreco() * quantidade;
        }
        return total;
    }
 
    // toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Data: ").append(this.data).append("\n");
        sb.append("Nome: ").append(this.nome).append("\n");
        sb.append("Itens:\n");
        for (Map.Entry<Produto, Integer> entry : this.itens.entrySet()) {
            Produto produto = entry.getKey();
            int quantidade = entry.getValue();
            sb.append("- ").append(produto.getDescricao()).append(" (").append(quantidade).append(" unidade(s))\n");
        }
        sb.append("Total: R$").append(calcularTotal());
        return sb.toString();
    }
 
}
 