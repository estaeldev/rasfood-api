package com.rasmoo.api.rasfood.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "enderecos")
@JsonInclude(value = Include.NON_NULL)
public class Endereco {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String cep;

    private String rua;

    private String complemento;

    private String cidade;

    private String estado;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

    public Endereco(String cep, String rua, String complemento, String cidade, String estado) {
        this.cep = cep;
        this.rua = rua;
        this.complemento = complemento;
        this.cidade = cidade;
        this.estado = estado;
    }

}
