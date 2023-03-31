package com.rasmoo.api.rasfood;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rasmoo.api.rasfood.repository.CardapioRepository;

@SpringBootTest
class RasfoodTest {

    @Autowired
    private CardapioRepository cardapioRepository;
    
    @Test
    void teste() {
        Integer resultado = this.cardapioRepository.updateDisponibilidade(2);
        assertEquals(1, resultado);
    }

}
