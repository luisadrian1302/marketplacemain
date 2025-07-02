package com.example.marketplacemain.marketplacemain.products.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.marketplacemain.marketplacemain.products.entitites.Categoria;
import com.example.marketplacemain.marketplacemain.products.entitites.Producto;
import com.example.marketplacemain.marketplacemain.products.entitites.Subcategoria;
import com.example.marketplacemain.marketplacemain.products.repositories.SubcategoriaInterface;

import jakarta.transaction.Transactional;

@Service
public class SubcategoriasService {

    
    @Autowired 
    private SubcategoriaInterface repository;

    // public List<Categoria> getAllCategorias(){
    //     return (List<Categoria>) repository.findAll();
    // }

    public Subcategoria getsubCategoria(Long id){

        Optional<Subcategoria> subcategorias =  repository.findById(id);

        if (subcategorias.isPresent()) {
            return subcategorias.get();
        }

        return null;
    }

    public List<Subcategoria> GetAllSubcategorias(){

        return (List<Subcategoria>) repository.findAll();

    }

    public List<Subcategoria> getAllCategoriasPublic(){
        return (List<Subcategoria>) repository.findBySubCategoriaAndStatusActive();
    }


    public List<Subcategoria> getAllByCategoria(Long id){

        return (List<Subcategoria>) repository.getSubcategoriasByIdCategoria(id);

    }
    


    public boolean existsByNombre(String nombre){
    
        return repository.existsByNombre(nombre);
    }

    public boolean existsByRuta(String ruta){
    
        return repository.existsByRuta(ruta);
    }

    @Transactional
    public Subcategoria save(Subcategoria producto)   {

        return repository.save(producto);
    }


    
    public List<Subcategoria> getSubCategoriaByNameAndID(Long id, String name){

      
        return repository.existsByNameSubCategory(name, id);
    }


}
