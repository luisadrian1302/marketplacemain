package com.example.marketplacemain.marketplacemain.products.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.marketplacemain.marketplacemain.products.entitites.Producto;

public interface ProductoRepository  extends CrudRepository<Producto, Long>{

    @Query(value = "select count(id) as  'count' from subproducto where id_producto = ?1 and status <> 0;", nativeQuery = true)
    List<Object[]>  findCountValue(Long id);

}
