package com.rasmoo.api.rasfood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rasmoo.api.rasfood.entity.Categoria;
import com.rasmoo.api.rasfood.repository.CategoriaRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoriaController {
    
    private final CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<Categoria>> findAll() {
        List<Categoria> categoriaList = this.categoriaRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(categoriaList);
    }

}
