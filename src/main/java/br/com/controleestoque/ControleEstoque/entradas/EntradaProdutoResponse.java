package br.com.controleestoque.ControleEstoque.entradas;

public class EntradaProdutoResponse {
    private Long produto;
    private Integer quantidadeEntrada;

    public EntradaProdutoResponse(EntradaProduto entradaProduto) {
        this.produto=entradaProduto.getProduto().getId();
        this.quantidadeEntrada=entradaProduto.getQuantidade();
    }

    public Long getProduto() {
        return produto;
    }

    public Integer getQuantidadeEntrada() {
        return quantidadeEntrada;
    }
}
