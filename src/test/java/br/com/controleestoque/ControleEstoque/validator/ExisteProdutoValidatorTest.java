package br.com.controleestoque.ControleEstoque.validator;

import br.com.controleestoque.ControleEstoque.produto.Produto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Annotation;

@SpringBootTest
@ActiveProfiles("test")
class ExisteProdutoValidatorTest {

    @PersistenceContext
    private EntityManager manager;
    ExisteProdutoTestCase testeCase;
    ExisteProdutoValidator validator;
    ConstraintValidatorContext context;


    @BeforeEach
    public void setUp() {
        testeCase= new ExisteProdutoTestCase();
        validator=new ExisteProdutoValidator(manager);
        context= Mockito.mock(ConstraintValidatorContext.class);
    }

    @Test
    @Transactional
    public void deveExistirOProduto(){
        Produto produto=new Produto("Livro","livro do spring",2,10);
        manager.persist(produto);
        validator.initialize(testeCase);
        boolean existe=validator.isValid(1L,context);
        Assertions.assertTrue(existe);
    }
    @Test
    public void naoDeveExistirOProduto(){
        validator.initialize(testeCase);
        boolean existe=validator.isValid(1L,context);
        Assertions.assertFalse(existe);
    }

    private static class ExisteProdutoTestCase implements ExisteProduto{

        @Override
        public String message() {
            return "Deveria existir o produto";
        }

        @Override
        public Class<?>[] groups() {
            return new Class[]{};
        }

        @Override
        public Class<? extends Payload>[] payload() {
            return new Class[]{};
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return ExisteProduto.class;
        }
    }
}