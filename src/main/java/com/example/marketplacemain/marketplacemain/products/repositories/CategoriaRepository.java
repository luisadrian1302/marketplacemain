package com.example.marketplacemain.marketplacemain.products.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.marketplacemain.marketplacemain.products.entitites.Categoria;

public interface CategoriaRepository extends CrudRepository<Categoria, Long> {
    boolean existsByNombre(String nombre);
    boolean existsByRuta(String ruta);

    @Query("Select c from  Categoria c where c.nombre = ?1 and c.id <> ?2")
    List<Categoria> existsByNameCategory(String nombre, Long id);
    
    
    @Query("Select c from  Categoria c where c.status <> false")
    List<Categoria> findByCategoriaAndStatusActive(); 


}
