package com.example.marketplacemain.marketplacemain.products.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.marketplacemain.marketplacemain.products.entitites.Atributo;
import com.example.marketplacemain.marketplacemain.products.entitites.Descuento;
import com.example.marketplacemain.marketplacemain.products.repositories.AtributoRepository;

import jakarta.transaction.Transactional;

@Service
public class AtributoService {

    @Autowired
    private AtributoRepository atributoRepository;

     public List<Atributo> getAllDescuentos(){
        return (List<Atributo>) atributoRepository.findAll();
    }

    public List<Atributo>  getAllBySubcategoria(Long id){
        return atributoRepository.findByIdSubcategoria(id);
    }

    public Atributo  getAtributoById(Long id){
        Optional<Atributo> atributo =  atributoRepository.findById(id);

        if (atributo.isPresent()) {
            return atributo.get();
        }
        return null;
    }
    
    @Transactional
    public Atributo saveAtributo(Atributo atributo){
        return atributoRepository.save(atributo);
    }



}
