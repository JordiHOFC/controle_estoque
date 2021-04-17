package br.com.controleestoque.ControleEstoque.entradas;

import br.com.controleestoque.ControleEstoque.produto.Produto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Entrada")
public class EntradaProduto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private LocalDateTime dataEntrada;

    @Column(nullable = false)
    private Double precoUnitario;

    @Column(nullable = false)
    private Integer quantidade;

    public EntradaProduto(Produto produto, LocalDateTime dataEntrada, Double precoUnitario, Integer quantidade) {
        this.produto = produto;
        this.dataEntrada = dataEntrada;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
    }

    public EntradaProduto() {

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
