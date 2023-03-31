package com.rasmoo.api.rasfood.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cardapios")
@JsonInclude(value = Include.NON_NULL)
public class Cardapio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String descricao;

    private Boolean disponivel;

    private BigDecimal valor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Categoria categoria;

    @Column(name = "data_de_registro")
    private LocalDateTime dataDeRegistro = LocalDateTime.now();

    @Lob
    private byte[] img;

    public Cardapio(String nome, String descricao, Boolean disponivel, BigDecimal valor, Categoria categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.disponivel = disponivel;
        this.valor = valor;
        this.categoria = categoria;
    }
    
}
