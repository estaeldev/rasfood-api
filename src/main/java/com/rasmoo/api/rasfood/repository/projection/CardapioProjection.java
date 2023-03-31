package com.rasmoo.api.rasfood.repository.projection;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface CardapioProjection {
    
    String getNome();
    String getDescricao();
    BigDecimal getValor();
    String getNomeCategoria();

}
