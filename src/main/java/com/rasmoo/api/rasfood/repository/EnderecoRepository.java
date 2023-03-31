package com.rasmoo.api.rasfood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rasmoo.api.rasfood.entity.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
    
    List<Endereco> findByCep(String cep);

}
