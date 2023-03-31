package com.rasmoo.api.rasfood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rasmoo.api.rasfood.dto.CardapioDto;
import com.rasmoo.api.rasfood.entity.Cardapio;
import com.rasmoo.api.rasfood.repository.projection.CardapioProjection;

import jakarta.transaction.Transactional;

public interface CardapioRepository extends JpaRepository<Cardapio, Integer>  {

    @Query("SELECT new com.rasmoo.api.rasfood.dto.CardapioDto(c.nome, c.descricao, c.valor, c.categoria.nome) " +
    "FROM Cardapio c WHERE c.nome LIKE %?1% AND c.disponivel = TRUE")
    List<CardapioDto> findAllByNome(@Param("nome") final String nome);

    @Query(value = "SELECT * FROM cardapios c WHERE c.categoria_id = ?1 AND c.disponivel = TRUE", nativeQuery = true)
    List<Cardapio> findAllByCategoria(@Param("id") final Integer categoriaId);

    @Query(value = "SELECT c.nome as nome, c.descricao as descricao, c.valor as valor, cat.nome as nomeCategoria " +
    "FROM cardapios c " +
    "INNER JOIN categorias cat ON c.categoria_id = cat.id " +
    "WHERE c.categoria_id = ?1 AND c.disponivel = TRUE", nativeQuery = true)
    List<CardapioProjection> findAllByCategoriaNativeQuery(@Param("id") final Integer categoriaId);

    @Transactional
    @Modifying
    @Query("UPDATE Cardapio c SET c.disponivel = CASE c.disponivel WHEN TRUE THEN FALSE ELSE TRUE END WHERE c.id = ?1")
    Integer updateDisponibilidade(@Param("id") final Integer id);

}
