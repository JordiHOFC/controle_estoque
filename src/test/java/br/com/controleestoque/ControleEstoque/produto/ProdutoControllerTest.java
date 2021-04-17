package br.com.controleestoque.ControleEstoque.produto;

import br.com.controleestoque.ControleEstoque.entradas.EntradaProduto;
import br.com.controleestoque.ControleEstoque.entradas.EntradaProdutoRequest;
import br.com.controleestoque.ControleEstoque.entradas.EntradaProdutoResponse;
import br.com.controleestoque.ControleEstoque.handler.Erros;
import br.com.controleestoque.ControleEstoque.saidas.SaidaProduto;
import br.com.controleestoque.ControleEstoque.saidas.SaidaProdutoRequest;
import br.com.controleestoque.ControleEstoque.saidas.SaidaProdutoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProdutoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @PersistenceContext
    private EntityManager manager;
    @Autowired
    private ProdutoRepository repository;

    @Order(1)
    @Test
    @Transactional
    public void deveRetorno201eLocation() throws Exception {
        ProdutoRequest produtoRequest = new ProdutoRequest("Livro aprenda o spring", "Aprendendo spring boot da massa", 1, 20);
        ProdutoResponse response = new ProdutoResponse(produtoRequest.toModelo());
        response.setId(1L);
        String jsonRequest = mapper.writeValueAsString(produtoRequest);
        String jsonResponse= mapper.writeValueAsString(response);
        URI uri = UriComponentsBuilder.fromUriString("http://localhost:8080/produtos").build().toUri();
        String location = UriComponentsBuilder.fromUriString("http://localhost:8080/produtos/1").build().toUriString();
        mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andExpect(MockMvcResultMatchers.header().string("location",location))
                .andExpect(MockMvcResultMatchers.content().json(jsonResponse));


    }

    @Test
    @Transactional
    public void deveRetornar400eErrorProdutoNaoExisteNoCadastroEntrada() throws Exception{
        EntradaProdutoRequest request= new EntradaProdutoRequest(2,2.5, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")).toString());
        String jsonRequest = mapper.writeValueAsString(request);
        Erros error=new Erros("Produto", "Não existe cadastro deste produto");
        String jsonResponse = mapper.writeValueAsString(error);
        URI uri = UriComponentsBuilder.fromUriString("http://localhost:8080/produtos/").path("{id}/entradas").buildAndExpand(1L).toUri();
        mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(jsonResponse));
    }
    @Test
    @Transactional
    @Order(2)
    public void deveRetornar201eLocationEntradaProduto() throws Exception{
        Produto produto=new Produto("prafuso","de rosaca",2,5);
        produto=repository.save(produto);
        EntradaProdutoRequest request= new EntradaProdutoRequest(2,2.5, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")).toString());
        String jsonRequest = mapper.writeValueAsString(request);
        URI uri = UriComponentsBuilder.fromUriString("http://localhost:8080/produtos/").path("{id}/entradas").buildAndExpand(produto.getId()).toUri();
        String location = UriComponentsBuilder.fromUriString("http://localhost:8080/produtos/").path("{id}/entradas/{idEntrada}").buildAndExpand(produto.getId(),1L).toUriString();
        EntradaProduto entradaProduto = request.toModelo(produto);
        EntradaProdutoResponse response=new EntradaProdutoResponse(entradaProduto);
        String jsonResponse= mapper.writeValueAsString(response);
        mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(jsonResponse))
                .andExpect(MockMvcResultMatchers.header().string("location",location));
    }
    @Test
    @Transactional
    public void deveRetornar400eErrorProdutoNaoExisteNoCadastroSaida() throws Exception{
        SaidaProdutoRequest request= new SaidaProdutoRequest(2, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")).toString());
        String jsonRequest = mapper.writeValueAsString(request);
        Erros error=new Erros("Produto", "Não existe cadastro deste produto");
        String jsonResponse = mapper.writeValueAsString(error);
        URI uri = UriComponentsBuilder.fromUriString("http://localhost:8080/produtos/").path("{id}/saidas").buildAndExpand(1L).toUri();
        mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(jsonResponse));
    }
    @Test
    @Transactional
    public void deveRetornar201eLocationSaidaProduto() throws Exception{
        Produto produto=new Produto("prafuso","de rosaca",2,5);
        manager.persist(produto);
        EntradaProdutoRequest requestEntrada= new EntradaProdutoRequest(2,2.5, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")).toString());
        manager.persist(requestEntrada.toModelo(produto));
        SaidaProdutoRequest request= new SaidaProdutoRequest(2, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")).toString());
        String jsonRequest = mapper.writeValueAsString(request);
        URI uri = UriComponentsBuilder.fromUriString("http://localhost:8080/produtos/").path("{id}/saidas").buildAndExpand(produto.getId()).toUri();
        String location = UriComponentsBuilder.fromUriString("http://localhost:8080/produtos/").path("{id}/saidas/{idSaida}").buildAndExpand(produto.getId(),1L).toUriString();
        SaidaProduto saidaProdutoo = request.toModelo(produto);
        SaidaProdutoResponse response=new SaidaProdutoResponse(saidaProdutoo);
        String jsonResponse= mapper.writeValueAsString(response);
        mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(jsonResponse))
                .andExpect(MockMvcResultMatchers.header().string("location",location));

    }
    @Test
    @Transactional
    public void deveRetornar400eErroSaidaProduto() throws Exception{
        Produto produto=new Produto("prafuso","de rosaca",2,5);
        manager.persist(produto);
        SaidaProdutoRequest request= new SaidaProdutoRequest(2, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")).toString());
        String jsonRequest = mapper.writeValueAsString(request);
        URI uri = UriComponentsBuilder.fromUriString("http://localhost:8080/produtos/").path("{id}/saidas").buildAndExpand(produto.getId()).toUri();
        SaidaProduto saidaProdutoo = request.toModelo(produto);
        Integer qtdDisponivel=repository.findByQuantidadeProduto(produto.getId());
        Erros response=new Erros("quantidade","Quantidade deve ser menor ou igual a "+qtdDisponivel);
        String jsonResponse= mapper.writeValueAsString(response);
        mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(jsonResponse));

    }




}