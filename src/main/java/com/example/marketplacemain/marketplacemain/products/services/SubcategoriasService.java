package com.example.marketplacemain.marketplacemain.products.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.marketplacemain.marketplacemain.products.entitites.Subcategoria;
import com.example.marketplacemain.marketplacemain.products.repositories.SubcategoriaInterface;

@Service
public class SubcategoriasService {

    
    @Autowired 
    private SubcategoriaInterface repository;

    // public List<Categoria> getAllCategorias(){
    //     return (List<Categoria>) repository.findAll();
    // }

    public Subcategoria getCategoria(Long id){

        Optional<Subcategoria> subcategorias =  repository.findById(id);

        if (subcategorias.isPresent()) {
            return subcategorias.get();
        }

        return null;
    }

    


}
