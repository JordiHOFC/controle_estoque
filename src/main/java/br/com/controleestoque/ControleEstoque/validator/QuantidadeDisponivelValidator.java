package br.com.controleestoque.ControleEstoque.validator;

import br.com.controleestoque.ControleEstoque.produto.Produto;
import br.com.controleestoque.ControleEstoque.produto.ProdutoRepository;
import br.com.controleestoque.ControleEstoque.saidas.SaidaProdutoRequest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class QuantidadeDisponivelValidator implements ConstraintValidator<QuantidadeDisponivel, SaidaProdutoRequest> {
    @Autowired
    private ProdutoRepository repository;


    @Override
    public boolean isValid(SaidaProdutoRequest saidaProdutoRequest, ConstraintValidatorContext constraintValidatorContext) {
        Integer qtdDisponivel= repository.findByQuantidadeProduto(saidaProdutoRequest.getProduto());
        return saidaProdutoRequest.getQuantidade()<=qtdDisponivel;
    }
}
