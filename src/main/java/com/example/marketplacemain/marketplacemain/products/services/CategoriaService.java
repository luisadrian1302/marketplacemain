package com.example.marketplacemain.marketplacemain.products.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.marketplacemain.marketplacemain.products.entitites.Categoria;
import com.example.marketplacemain.marketplacemain.products.repositories.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired 
    private CategoriaRepository repository;

    public List<Categoria> getAllCategorias(){
        return (List<Categoria>) repository.findAll();
    }

    public List<Categoria> getAllCategoriasPublic(){
        return (List<Categoria>) repository.findByCategoriaAndStatusActive();
    }

    public Categoria getGatCategoriaById(Long id){

        Optional<Categoria> categoria = repository.findById(id);
        if (categoria.isPresent()) {
            return categoria.get();
        }
        return null;
    }


    public List<Categoria> getCategoriaByNameAndID(Long id, String name){

      
        return repository.existsByNameCategory(name, id);
    }

    public Categoria save(Categoria categoria){
    
        return repository.save(categoria);
    }

    public boolean existsByNombre(String nombre){
    
        return repository.existsByNombre(nombre);
    }

    public boolean existsByRuta(String ruta){
    
        return repository.existsByRuta(ruta);
    }





}
