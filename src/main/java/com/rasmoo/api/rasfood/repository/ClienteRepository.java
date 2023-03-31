package com.rasmoo.api.rasfood.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rasmoo.api.rasfood.entity.Cliente;
import com.rasmoo.api.rasfood.entity.ClienteId;

public interface ClienteRepository extends JpaRepository<Cliente, ClienteId> {
    
    @Query("SELECT c FROM Cliente c WHERE c.clienteId.email = :id OR c.clienteId.cpf = :id")
    Optional<Cliente> findByEmailOrCpf(@Param("id") final String id);

}
