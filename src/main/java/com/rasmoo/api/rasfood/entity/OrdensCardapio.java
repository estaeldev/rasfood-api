package com.rasmoo.api.rasfood.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ordens_cardapio")
public class OrdensCardapio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ordem ordem;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cardapio cardapio;

    @Column(name = "valor_de_registro")
    private BigDecimal valorDeRegistro;

    private Integer quantidade;

    public OrdensCardapio( Cardapio cardapio, Integer quantidade) {
        this.cardapio = cardapio;
        this.quantidade = quantidade;
        this.valorDeRegistro = cardapio.getValor();
    }
    
}
