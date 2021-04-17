package br.com.controleestoque.ControleEstoque.produto;

import br.com.controleestoque.ControleEstoque.entradas.EntradaProduto;
import br.com.controleestoque.ControleEstoque.saidas.SaidaProduto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ActiveProfiles("test")
class ProdutoRepositoryTest {
    @Autowired
    private ProdutoRepository repository;
    @PersistenceContext
    private EntityManager manager;

    @Test
    public void deveRetornarAQuantidadeZeroDoProduto(){
        Produto produto=new Produto("Livro","livro do spring",2,10);
        repository.save(produto);
        Integer quantidade=repository.findByQuantidadeProduto(produto.getId());
        Assertions.assertEquals(0,quantidade);
    }

    @Test
    @Transactional
    public void deveRetornarAQuantidade5DoProduto(){
        Produto produto=new Produto("Livro","livro do spring",2,10);
        repository.save(produto);
        EntradaProduto entradaProduto=new EntradaProduto(produto, LocalDateTime.now(),23.2,5);
        manager.persist(entradaProduto);
        Integer quantidade=repository.findByQuantidadeProduto(produto.getId());
        Assertions.assertEquals(5,quantidade);
    }
    @Test
    @Transactional
    public void deveRetornarAQuantidade3DoProduto(){
        Produto produto=new Produto("Livro","livro do spring",2,10);
        repository.save(produto);
        EntradaProduto entradaProduto=new EntradaProduto(produto, LocalDateTime.now(),23.2,5);
        manager.persist(entradaProduto);
        SaidaProduto saidaProduto= new SaidaProduto(produto,2,LocalDateTime.now());
        manager.persist(saidaProduto);
        Integer quantidade=repository.findByQuantidadeProduto(produto.getId());
        Assertions.assertEquals(3,quantidade);
    }
}