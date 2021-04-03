package br.com.controleestoque.ControleEstoque.entradas;

import br.com.controleestoque.ControleEstoque.validator.ExisteProduto;
import br.com.controleestoque.ControleEstoque.produto.Produto;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EntradaProdutoRequest {
    @NotNull
    @ExisteProduto
    private Long produto;
    @NotNull
    private Integer quantidade;
    @NotNull
    private Double  precoUnitario;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private String dataEntrada;

    public EntradaProdutoRequest(@NotNull Long produto, @NotNull Integer quantidade, @NotNull Double precoUnitario, String dataEntrada) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.dataEntrada = dataEntrada;
    }
    public EntradaProduto toModelo(){
        Produto produto=new Produto(this.produto);
        @FutureOrPresent
        LocalDateTime data=LocalDateTime.parse(dataEntrada, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        return new EntradaProduto(produto,data,precoUnitario,quantidade);
    }

    public Long getProduto() {
        return produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public String getDataEntrada() {
        return dataEntrada;
    }
}
