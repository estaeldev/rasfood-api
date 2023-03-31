package com.rasmoo.api.rasfood.controller;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasmoo.api.rasfood.entity.Cardapio;
import com.rasmoo.api.rasfood.repository.CardapioRepository;
import com.rasmoo.api.rasfood.repository.projection.CardapioProjection;
import com.rasmoo.api.rasfood.repository.specification.CardapioSpec;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cardapios")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CardapioController {

    private final CardapioRepository cardapioRepository;
    private final ObjectMapper objectMapper;
    
    @GetMapping
    public ResponseEntity<Page<Cardapio>> findAll(@RequestParam("page") final Integer page, 
                                                  @RequestParam("size") final Integer size,
                                                  @RequestParam(value = "sort", required = false) final Direction sort,
                                                  @RequestParam(value = "property", required = false) final String property) {

        Pageable pageable = Objects.nonNull(sort) ? PageRequest.of(page, size, Sort.by(sort, property)) : PageRequest.of(page, size);
        Page<Cardapio> cardapioList = this.cardapioRepository.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(cardapioList);
    }

    @GetMapping("/categoria/{id}/disponivel")
    public ResponseEntity<List<Cardapio>> findAllByCategoria(@PathVariable("id") final Integer id) {
        List<Cardapio> cardapioList = this.cardapioRepository.findAll(Specification
                                                                    .where(CardapioSpec.categoria(id))
                                                                    .and(CardapioSpec.disponivel(true)));
        return ResponseEntity.status(HttpStatus.OK).body(cardapioList);
    }

    @GetMapping("/categoria/native-query/{id}/disponivel")
    public ResponseEntity<Page<CardapioProjection>> findAllByCategoriaNativeQuery(@PathVariable("id") final Integer id, 
        @RequestParam("page") final int page, @RequestParam("size") final int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<CardapioProjection> cardapioList = this.cardapioRepository.findAllByCategoriaNativeQuery(id, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(cardapioList);
    }

    @GetMapping("/nome/{nome}/disponivel")
    public ResponseEntity<List<Cardapio>> findAllByNome(@PathVariable("nome") final String nome, 
                                                           @RequestParam("page") final int page, 
                                                           @RequestParam("size") final int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Cardapio> cardapioDtoList = this.cardapioRepository.findAll(Specification
                                                                .where(CardapioSpec.nome(nome))
                                                                .and(CardapioSpec.disponivel(true)), pageable);
        return ResponseEntity.status(HttpStatus.OK).body(cardapioDtoList.getContent());
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

    @PatchMapping(path =  "/{id}/img", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Cardapio> salvarImg(@PathVariable("id") final Integer id, @RequestPart final MultipartFile file ) 
        throws IOException {

        Cardapio cardapioEncontrado = findById(id).getBody();
        if(Objects.nonNull(cardapioEncontrado)) {
            cardapioEncontrado.setImg(file.getBytes());
            this.cardapioRepository.save(cardapioEncontrado);
            return ResponseEntity.status(HttpStatus.OK).body(cardapioEncontrado);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

}
