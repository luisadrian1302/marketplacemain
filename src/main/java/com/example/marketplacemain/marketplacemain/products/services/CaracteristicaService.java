package com.example.marketplacemain.marketplacemain.products.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.marketplacemain.marketplacemain.products.DTO.ValorPropiedadDTO;
import com.example.marketplacemain.marketplacemain.products.entitites.Caracteristicas;
import com.example.marketplacemain.marketplacemain.products.repositories.CaracteristicaRepository;

import jakarta.transaction.Transactional;

@Service
public class CaracteristicaService {

     @Autowired
    private CaracteristicaRepository repository;

     @Transactional
    public Caracteristicas save(Caracteristicas descuento){
        return repository.save(descuento);
    }

    public List<ValorPropiedadDTO> findValueValor(Long id){
        return repository.findAtributoValor(id);
    }

    public List<ValorPropiedadDTO> findValueValorByIDsubcategoria(Long id, Long idSubproducto){
        return repository.findAtributoValorByIDSubproducto(id, idSubproducto);
    }

    public List<ValorPropiedadDTO> findValuesBySubproduct(Long id, Long idSubproducto){
        return repository.findValuesBySubproduct(id, idSubproducto);
    }

}
