package br.com.controleestoque.ControleEstoque.produto;

public class ProdutoResponse {
    private Long id;
    private String nome;
    private Integer quantidade;

    public ProdutoResponse(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

}
