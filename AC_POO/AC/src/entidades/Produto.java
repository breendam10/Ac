
package entidades;
 
public class Produto {
   
    private int codigo;
    private String descricao;
    private double preco;
    private int qntdEstoque;
 
    public Produto(int codigo, String descricao, double preco, int qntdEstoque) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.preco = preco;
        this.qntdEstoque = qntdEstoque;
    }
 
 
// GETTERS
 
public int getCodigo() {
    return this.codigo;
}
 
public String getDescricao() {
    return this.descricao;
}
 
public double getPreco() {
    return this.preco;
}
 
public int getQntdEstoque() {
    return this.qntdEstoque;
}
 
// SETTERS
 
public void setCodigo(int novoCodigo) {
    this.codigo = novoCodigo;
}
 
public void setDescricao(String novaDescricao) {
    this.descricao = novaDescricao;
}
 
public void setPreco(double novoPreco) {
    this.preco = novoPreco;
}
 
public void setQuantidade(int novaQuantidade) {
    this.qntdEstoque = novaQuantidade;
}
 
// toString
 
public String toString() {
    return "Código: " + this.codigo + "\n" +
        "Descrição: " + this.descricao + "\n" +
        "Preço: R$ " + this.preco + "\n" +
        "Quantidade em estoque: " + this.qntdEstoque;
}
 
 
}