package br.com.controleestoque.ControleEstoque.produto;

import br.com.controleestoque.ControleEstoque.entradas.EntradaProduto;
import br.com.controleestoque.ControleEstoque.entradas.EntradaProdutoRequest;
import br.com.controleestoque.ControleEstoque.entradas.EntradaProdutoResponse;
import br.com.controleestoque.ControleEstoque.handler.Erros;
import br.com.controleestoque.ControleEstoque.saidas.SaidaProduto;
import br.com.controleestoque.ControleEstoque.saidas.SaidaProdutoRequest;
import br.com.controleestoque.ControleEstoque.saidas.SaidaProdutoResponse;
import br.com.controleestoque.ControleEstoque.validator.ExisteProduto;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.net.URI;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoRepository repository;
    private final EntityManager manager;

    public ProdutoController(ProdutoRepository repository,EntityManager manager) {
        this.manager=manager;
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<?>cadastrarProduto(@RequestBody @Valid ProdutoRequest produtoRequest, UriComponentsBuilder uriBuilder){
        Produto produto= produtoRequest.toModelo();
        repository.save(produto);
        URI uri=uriBuilder.path("/produtos/{id}").buildAndExpand(produto.getId()).toUri();
        return ResponseEntity.created(uri).body(new ProdutoResponse(produto));
    }
    @PostMapping("/{id}/entradas")
    @Transactional
    public ResponseEntity<?> realizarEntrada(@RequestBody @Valid EntradaProdutoRequest entrada,@PathVariable Long id, UriComponentsBuilder uriBuilder){
        Optional<Produto> produto= repository.findById(id);
        if(produto.isEmpty()){
            Erros error=new Erros("Produto", "Não existe cadastro deste produto");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        EntradaProduto entradaProduto=entrada.toModelo(produto.get());
        manager.persist(entradaProduto);
        produto.get().adicionarEntrada(entradaProduto);
        URI uri=uriBuilder.path("/produtos/{id}/entradas/{idEntrada}").buildAndExpand(Map.of("id",produto.get().getId(),"idEntrada",entradaProduto.getId())).toUri();
        return ResponseEntity.created(uri).body(new EntradaProdutoResponse(entradaProduto));
    }
    @PostMapping("/{id}/saidas")
    @Transactional
    public ResponseEntity<?> realizarSaida(@RequestBody @Valid SaidaProdutoRequest saida, @PathVariable @ExisteProduto Long id, UriComponentsBuilder uriBuilder){
        Optional<Produto> produto= repository.findById(id);
        if(produto.isEmpty()){
            Erros error=new Erros("Produto", "Não existe cadastro deste produto");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        Integer quantidadeDisponivel= repository.findByQuantidadeProduto(id);
        if (saida.getQuantidade()>quantidadeDisponivel){
            Erros error=new Erros("quantidade","Quantidade deve ser menor ou igual a "+quantidadeDisponivel);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        SaidaProduto saidaProduto=saida.toModelo(produto.get());
        manager.persist(saidaProduto);
        produto.get().adicionarSaida(saidaProduto);
        URI uri=uriBuilder.path("/produtos/{id}/saidas/{idSaida}").buildAndExpand(produto.get().getId(), saidaProduto.getId()).toUri();
        return ResponseEntity.created(uri).body(new SaidaProdutoResponse(saidaProduto));
    }

}
