package br.com.controleestoque.ControleEstoque.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    @Query(value = "select (select IFNULL(SUM(e.quantidade),0) from entrada e where e.produto_id=?1)  -  IFNULL( SUM(s.quantidade) ,0) from saida s where s.produto_id=?1", nativeQuery = true)
    Integer findByQuantidadeProduto(Long idProduto);
}
