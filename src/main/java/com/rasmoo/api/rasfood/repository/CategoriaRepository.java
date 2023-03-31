package com.rasmoo.api.rasfood.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rasmoo.api.rasfood.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    
}
