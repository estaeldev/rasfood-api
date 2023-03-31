package com.rasmoo.api.rasfood.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clientes")
@JsonInclude(value = Include.NON_NULL)
public class Cliente {
    
    @EmbeddedId
    private ClienteId clienteId;

    private String nome;

    @Embedded
    private Contato contato;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Endereco> enderecoList = new ArrayList<>();

    public Cliente(String cpf, String email, String nome) {
        this.clienteId = new ClienteId(email,cpf);
        this.nome = nome;
    }

    public void addEndereco(Endereco endereco){
        endereco.setCliente(this);
        this.enderecoList.add(endereco);
    }

}
