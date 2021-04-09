package br.com.controleestoque.ControleEstoque.entradas;

import br.com.controleestoque.ControleEstoque.validator.ExisteProduto;
import br.com.controleestoque.ControleEstoque.produto.Produto;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EntradaProdutoRequest {
    @NotNull
    @Positive
    private Integer quantidade;
    @NotNull
    @PositiveOrZero
    private Double  precoUnitario;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private String dataEntrada;

    public EntradaProdutoRequest(@NotNull Integer quantidade, @NotNull Double precoUnitario, String dataEntrada) {

        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.dataEntrada = dataEntrada;
    }
    public EntradaProduto toModelo(Produto produto){
        @PastOrPresent
        LocalDateTime data=LocalDateTime.parse(dataEntrada, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        return new EntradaProduto(produto,data,precoUnitario,quantidade);
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
