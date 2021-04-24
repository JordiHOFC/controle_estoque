package br.com.controleestoque.ControleEstoque.validator;

import br.com.controleestoque.ControleEstoque.produto.Produto;
import org.junit.jupiter.api.*;

import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintValidatorContext;


@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExisteProdutoValidatorTest {

    @PersistenceContext
    private EntityManager manager;

    ExisteProdutoValidator validator;
    ConstraintValidatorContext context;


    @BeforeEach
    public void setUp() {

        validator=new ExisteProdutoValidator(manager);
        context= Mockito.mock(ConstraintValidatorContext.class);
    }
    @Test
    @Order(2)
    public void naoDeveExistirOProduto(){
        boolean existe=validator.isValid(1L,context);
        Assertions.assertFalse(existe);
    }
    @Test
    @Transactional
    @Order(1)
    public void deveExistirOProduto(){
        Produto produto=new Produto("Livro","livro do spring",2,10);
        manager.persist(produto);
        boolean existe=validator.isValid(1L,context);
        Assertions.assertTrue(existe);
    }
}