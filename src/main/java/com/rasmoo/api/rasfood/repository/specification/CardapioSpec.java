package com.rasmoo.api.rasfood.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.rasmoo.api.rasfood.entity.Cardapio;

public class CardapioSpec {
    
    private CardapioSpec() {}

    public static Specification<Cardapio> nome(String nome) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.like(root.get("nome"), "%"+nome+"%");
    }

    public static Specification<Cardapio> categoria(Integer categoria) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(root.get("categoria"), categoria);
    }

    public static Specification<Cardapio> disponivel(Boolean disponivel) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(root.get("disponivel"), disponivel);
    }


}
