package br.com.controleestoque.ControleEstoque.produto;

import br.com.controleestoque.ControleEstoque.entradas.EntradaProduto;
import br.com.controleestoque.ControleEstoque.saidas.SaidaProduto;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.annotations.Subselect;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Produto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nome;
    @Column(nullable = false)
    private String descricao;
    @Column(nullable = false)
    private Integer quantidadeMinima;
    @Column(nullable = false)
    private Integer quantidadeMaxima;
    @Column(nullable = false)
    private LocalDateTime criadoEm;
   
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private List<SaidaProduto> saidas= new ArrayList<>();

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private List<EntradaProduto> entradas= new ArrayList<>();


    public Produto(String nome, String descricao, Integer quantidadeMinima, Integer quantidadeMaxima) {
        this.nome = nome;
        this.descricao = descricao;
        this.quantidadeMinima = quantidadeMinima;
        this.quantidadeMaxima = quantidadeMaxima;
        this.criadoEm  =LocalDateTime.now();
    }

    public Produto() {
    }

    public Produto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void adicionarEntrada(EntradaProduto entrada){
        this.entradas.add(entrada);
    }
    public void adicionarSaida(SaidaProduto saida){
        this.saidas.add(saida);
    }
}
