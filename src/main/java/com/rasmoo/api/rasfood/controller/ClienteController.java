package com.rasmoo.api.rasfood.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasmoo.api.rasfood.entity.Cliente;
import com.rasmoo.api.rasfood.entity.ClienteId;
import com.rasmoo.api.rasfood.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClienteController {
    
    private final ClienteRepository clienteRepository;
    private final ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<List<Cliente>> findAll() {
        List<Cliente> clientes = clienteRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(clientes);
    }
    
    @GetMapping("/{email}/{cpf}")
    public ResponseEntity<Cliente> findByEmailCpf(@PathVariable("email") final String email, @PathVariable("cpf") final String cpf ) {
        ClienteId clienteId = new ClienteId(email, cpf);
        Optional<Cliente> clienteOptional = this.clienteRepository.findById(clienteId);
        return clienteOptional.map(cliente -> ResponseEntity.status(HttpStatus.OK).body(cliente))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable("id") final String id, @RequestBody final Cliente cliente) 
        throws JsonMappingException {
        
        Optional<Cliente> clienteOptional = this.clienteRepository.findByEmailOrCpf(id);
        if(clienteOptional.isPresent()){
            Cliente clienteUpdate = this.objectMapper.updateValue(clienteOptional.get(), cliente);
            Cliente clienteSaved = this.clienteRepository.save(clienteUpdate);
            return ResponseEntity.status(HttpStatus.OK).body(clienteSaved);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

}
