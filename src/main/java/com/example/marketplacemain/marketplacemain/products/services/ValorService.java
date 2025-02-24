package com.example.marketplacemain.marketplacemain.products.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.marketplacemain.marketplacemain.products.entitites.Valor;
import com.example.marketplacemain.marketplacemain.products.repositories.ValorRepository;

import jakarta.transaction.Transactional;

@Service
public class ValorService {

    @Autowired
    private ValorRepository atributoRepository;

    
    
    @Transactional
    public Valor saveValor(Valor atributo){
        return atributoRepository.save(atributo);
    }
}
