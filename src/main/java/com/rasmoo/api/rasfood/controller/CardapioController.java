package com.rasmoo.api.rasfood.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasmoo.api.rasfood.dto.CardapioDto;
import com.rasmoo.api.rasfood.entity.Cardapio;
import com.rasmoo.api.rasfood.repository.CardapioRepository;
import com.rasmoo.api.rasfood.repository.projection.CardapioProjection;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cardapios")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CardapioController {

    private final CardapioRepository cardapioRepository;
    private final ObjectMapper objectMapper;
    
    @GetMapping
    public ResponseEntity<List<Cardapio>> findAll() {
        List<Cardapio> cardapioList = this.cardapioRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(cardapioList);
    }

    @GetMapping("/categoria/{id}/disponivel")
    public ResponseEntity<List<Cardapio>> findAllByCategoria(@PathVariable("id") final Integer id) {
        List<Cardapio> cardapioList = this.cardapioRepository.findAllByCategoria(id);
        return ResponseEntity.status(HttpStatus.OK).body(cardapioList);
    }

    @GetMapping("/categoria/native-query/{id}/disponivel")
    public ResponseEntity<List<CardapioProjection>> findAllByCategoriaNativeQuery(@PathVariable("id") final Integer id) {
        List<CardapioProjection> cardapioList = this.cardapioRepository.findAllByCategoriaNativeQuery(id);
        return ResponseEntity.status(HttpStatus.OK).body(cardapioList);
    }

    @GetMapping("/nome/{nome}/disponivel")
    public ResponseEntity<List<CardapioDto>> findAllByNome(@PathVariable("nome") final String nome) {
        List<CardapioDto> cardapioDtoList = this.cardapioRepository.findAllByNome(nome);
        return ResponseEntity.status(HttpStatus.OK).body(cardapioDtoList);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Cardapio> findById(@PathVariable("id") final Integer id) {
        Optional<Cardapio> cardapioOptional = this.cardapioRepository.findById(id);
        return cardapioOptional.map(cardapio -> ResponseEntity.status(HttpStatus.OK).body(cardapio))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping
    public ResponseEntity<Cardapio> save(@RequestBody final Cardapio cardapio) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.cardapioRepository.save(cardapio));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Cardapio> update(@PathVariable("id") final Integer id, @RequestBody final Cardapio cardapio) 
        throws JsonMappingException {

        Cardapio cardapioEncontrado = findById(id).getBody();
        if(Objects.nonNull(cardapioEncontrado)) {
            this.objectMapper.updateValue(cardapioEncontrado, cardapio);
            this.cardapioRepository.save(cardapioEncontrado);
            return ResponseEntity.status(HttpStatus.OK).body(cardapioEncontrado);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") final Integer id) {

        Cardapio cardapioEncontrado = findById(id).getBody();
        if(Objects.nonNull(cardapioEncontrado)) {
            this.cardapioRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }



}
