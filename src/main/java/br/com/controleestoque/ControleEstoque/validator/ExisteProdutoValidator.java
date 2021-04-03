package br.com.controleestoque.ControleEstoque.validator;

import br.com.controleestoque.ControleEstoque.produto.Produto;
import br.com.controleestoque.ControleEstoque.produto.ProdutoRepository;
import br.com.controleestoque.ControleEstoque.produto.ProdutoResponse;
import br.com.controleestoque.ControleEstoque.validator.ExisteProduto;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class ExisteProdutoValidator implements ConstraintValidator<ExisteProduto, Long> {
    @PersistenceContext
    private EntityManager manager;

    public void initialize(ExisteProduto constraint) {
    }

    public boolean isValid(Long idProduto, ConstraintValidatorContext context) {
        String jpql="select r from Produto r where r.id=:id";
        Query query= manager.createQuery(jpql);
        query.setParameter("id",idProduto);
        return !query.getResultList().isEmpty();
    }
}
