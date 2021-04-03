package br.com.controleestoque.ControleEstoque.produto;

import br.com.controleestoque.ControleEstoque.entradas.EntradaProduto;
import br.com.controleestoque.ControleEstoque.entradas.EntradaProdutoRequest;
import br.com.controleestoque.ControleEstoque.entradas.EntradaProdutoResponse;
import br.com.controleestoque.ControleEstoque.saidas.SaidaProduto;
import br.com.controleestoque.ControleEstoque.saidas.SaidaProdutoRequest;
import br.com.controleestoque.ControleEstoque.saidas.SaidaProdutoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @PersistenceContext
    private EntityManager entityManager;


    @PostMapping
    public ResponseEntity<?>cadastrarProduto(@RequestBody @Valid ProdutoRequest produtoRequest, UriComponentsBuilder uriBuilder){
        Produto produto= produtoRequest.toModelo();
        entityManager.persist(produto);
        URI uri=uriBuilder.path("/produtos/{id}").buildAndExpand(produto.getId()).toUri();
        return ResponseEntity.created(uri).body(new ProdutoResponse(produto));
    }
    @PostMapping("/entradas")
    @Transactional
    public ResponseEntity<?> realizarEntrada(@RequestBody @Valid EntradaProdutoRequest entrada,UriComponentsBuilder uriBuilder){
        EntradaProduto entradaProduto=entrada.toModelo();
        entityManager.persist(entradaProduto);
        URI uri=uriBuilder.path("/produtos/entradas/{id}").buildAndExpand(entradaProduto.getId()).toUri();
        return ResponseEntity.created(uri).body(new EntradaProdutoResponse(entradaProduto));
    }
    @PostMapping("/saidas")
    @Transactional
    public ResponseEntity<?> realizarSaida(@RequestBody @Valid SaidaProdutoRequest saida, UriComponentsBuilder uriBuilder){
        SaidaProduto saidaProduto=saida.toModelo();
        entityManager.persist(saidaProduto);
        URI uri=uriBuilder.path("/produtos/saidas/{id}").buildAndExpand(saidaProduto.getId()).toUri();
        return ResponseEntity.created(uri).body(new SaidaProdutoResponse(saidaProduto));
    }

}
