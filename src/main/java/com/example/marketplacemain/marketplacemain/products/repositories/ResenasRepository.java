package com.example.marketplacemain.marketplacemain.products.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.marketplacemain.marketplacemain.products.entitites.Reseña;

public interface ResenasRepository extends CrudRepository<Reseña, Long>{

    @Query("Select r from Reseña r where r.producto.id  = ?1")
    List<Reseña> getReseñaByIdProduct(Long id);

    @Query(value = " select COALESCE(avg(r.puntuacion)) as promedio, COALESCE(count(r.id)) as total from reseña r where r.id_producto = ?1;", nativeQuery = true)
    List<Object[]> getAvgAndCountResenasByIdProduct(Long id);




}
