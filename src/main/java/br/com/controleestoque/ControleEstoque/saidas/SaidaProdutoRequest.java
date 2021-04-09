package br.com.controleestoque.ControleEstoque.saidas;

import br.com.controleestoque.ControleEstoque.produto.Produto;
import br.com.controleestoque.ControleEstoque.validator.ExisteProduto;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class SaidaProdutoRequest {

    @NotNull
    @PositiveOrZero
    private Integer quantidade;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private String dataSaida;

    public SaidaProdutoRequest(@NotNull Integer quantidade, String dataSaida) {

        this.quantidade = quantidade;
        this.dataSaida=dataSaida;
    }
    public SaidaProduto toModelo(Produto produto){
        @PastOrPresent
        LocalDateTime data=LocalDateTime.parse(dataSaida, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        return new SaidaProduto(produto,quantidade,data);
    }



    public Integer getQuantidade() {
        return quantidade;
    }


}
