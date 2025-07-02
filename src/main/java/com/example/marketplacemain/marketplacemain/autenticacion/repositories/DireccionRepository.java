package com.example.marketplacemain.marketplacemain.autenticacion.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.marketplacemain.marketplacemain.autenticacion.entities.Direcciones;
import com.example.marketplacemain.marketplacemain.products.entitites.SubProducto;

public interface DireccionRepository  extends CrudRepository<Direcciones, Long>{

    @Query("select d from Direcciones d left join fetch d.client where d.client.id=?1")
    List<Direcciones> findByIdUser(Long id);

    @Query("select d from Direcciones d left join fetch d.client where d.client.id=?1 and d.id = ?2")
    List<Direcciones> findByIdAndClient(long idUser, long id);


}
