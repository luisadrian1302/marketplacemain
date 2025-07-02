package com.example.marketplacemain.marketplacemain.products.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.marketplacemain.marketplacemain.products.entitites.Atributo;

public interface AtributoRepository extends CrudRepository<Atributo, Long>{

     @Query("select a from Atributo a left join fetch a.subcategoria where a.subcategoria.id=?1 and a.status = 1")
    List<Atributo> findByIdSubcategoria(Long id);

    @Query("select a from Atributo a left join fetch a.subcategoria where a.subcategoria.id=?1 and LOWER(a.nombre) = LOWER(?2) and a.id <> ?3")
    List<Atributo> findByNameAndID(Long id, String name, Long idAtribute);

}
