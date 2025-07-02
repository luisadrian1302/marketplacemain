package com.example.marketplacemain.marketplacemain.products.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.marketplacemain.marketplacemain.products.entitites.Categoria;
import com.example.marketplacemain.marketplacemain.products.entitites.Subcategoria;

public interface SubcategoriaInterface extends CrudRepository<Subcategoria, Long> {


    @Query("select s from Subcategoria s where s.categoria.id = ?1 and s.status <> false")
    List<Subcategoria> getSubcategoriasByIdCategoria(Long id);


    boolean existsByNombre(String nombre);
    boolean existsByRuta(String ruta);

    @Query("Select s from  Subcategoria s where s.nombre = ?1 and s.id <> ?2")
    List<Subcategoria> existsByNameSubCategory(String nombre, Long id);
    
    
    @Query("Select s from  Subcategoria s where s.status <> false")
    List<Subcategoria> findBySubCategoriaAndStatusActive(); 
}
