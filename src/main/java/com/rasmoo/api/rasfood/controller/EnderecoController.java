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
import com.rasmoo.api.rasfood.entity.Endereco;
import com.rasmoo.api.rasfood.repository.EnderecoRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/enderecos")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EnderecoController {

    private final EnderecoRepository enderecoRepository;
    private final ObjectMapper objectMapper;
    
    @GetMapping
    public ResponseEntity<List<Endereco>> findAll() {
        List<Endereco> enderecoList = this.enderecoRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(enderecoList);
    }

    @GetMapping("/cep/{cep}")
    public ResponseEntity<List<Endereco>> findAllByCep(@PathVariable("cep") final String cep) {
        List<Endereco> enderecoList = this.enderecoRepository.findByCep(cep);
        return ResponseEntity.status(HttpStatus.OK).body(enderecoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> findById(@PathVariable("id") final Integer id) {
        Optional<Endereco> enderecoOptional = this.enderecoRepository.findById(id);
        return enderecoOptional.map(endereco -> ResponseEntity.status(HttpStatus.OK).body(endereco))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Endereco> update(@PathVariable("id") final Integer id, @RequestBody final Endereco endereco) 
        throws JsonMappingException {

        Optional<Endereco> enderecoOptional = this.enderecoRepository.findById(id);
        if(enderecoOptional.isPresent()) {
            this.objectMapper.updateValue(enderecoOptional.get(), endereco);
            return ResponseEntity.status(HttpStatus.OK).body(this.enderecoRepository.save(enderecoOptional.get()));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }


}
