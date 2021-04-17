package br.com.controleestoque.ControleEstoque.validator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ExisteProdutoValidator implements ConstraintValidator<ExisteProduto, Long> {

    private final  EntityManager manager;

    public ExisteProdutoValidator(EntityManager manager) {
        this.manager = manager;
    }

    public void initialize(ExisteProduto constraint) {
    }

    public boolean isValid(Long idProduto, ConstraintValidatorContext context) {
        String jpql = "select r from Produto r where r.id=:id";
        Query query = manager.createQuery(jpql);
        query.setParameter("id", idProduto);
        List<?> result=query.getResultList();
        return !result.isEmpty();
    }
}
