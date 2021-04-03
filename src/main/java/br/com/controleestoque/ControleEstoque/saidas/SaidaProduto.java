package br.com.controleestoque.ControleEstoque.saidas;

import br.com.controleestoque.ControleEstoque.produto.Produto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Saida")
public class SaidaProduto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Produto produto;
    @Column(nullable = false)
    private Integer quantidade;
    @Column(nullable = false)
    private LocalDateTime dataSaida;

    public SaidaProduto(Produto produto, Integer quantidade, LocalDateTime dataSaida) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.dataSaida = dataSaida;
    }

    public SaidaProduto() {
    }

    public Long getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }
}
