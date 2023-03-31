package com.rasmoo.api.rasfood.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardapioDto {
    
    private String nome;
    private String descricao;
    private BigDecimal valor;
    private String nomeCategoria;

}
