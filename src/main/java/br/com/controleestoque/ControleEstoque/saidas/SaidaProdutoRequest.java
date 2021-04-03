package br.com.controleestoque.ControleEstoque.saidas;

import br.com.controleestoque.ControleEstoque.entradas.EntradaProduto;
import br.com.controleestoque.ControleEstoque.produto.Produto;
import br.com.controleestoque.ControleEstoque.validator.ExisteProduto;
import br.com.controleestoque.ControleEstoque.validator.QuantidadeDisponivel;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@QuantidadeDisponivel
public class SaidaProdutoRequest {
    @NotNull
    @ExisteProduto
    private Long produto;
    @NotNull
    private Integer quantidade;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private String dataSaida;

    public SaidaProdutoRequest(@NotNull Long produto, @NotNull Integer quantidade, String dataSaida) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.dataSaida=dataSaida;
    }
    public SaidaProduto toModelo(){
        Produto produto=new Produto(this.produto);
        @FutureOrPresent
        LocalDateTime data=LocalDateTime.parse(dataSaida, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        return new SaidaProduto(produto,quantidade,data);
    }

    public Long getProduto() {
        return produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }


}
