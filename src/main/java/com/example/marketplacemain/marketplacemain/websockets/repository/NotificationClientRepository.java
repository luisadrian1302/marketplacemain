package com.example.marketplacemain.marketplacemain.websockets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.marketplacemain.marketplacemain.products.entitites.SubProducto;
import com.example.marketplacemain.marketplacemain.websockets.entities.NotificationClient;

import jakarta.transaction.Transactional;

public interface NotificationClientRepository  extends  CrudRepository<NotificationClient, Long> {

    // @Query("Select n from NotificationClient n")
    // NotificationClient obtener();
    @Query("select n from NotificationClient n where n.user.id = ?1")
    List<NotificationClient> getByIdClient(Long id);


    @Query("SELECT COUNT(n.id) FROM NotificationClient n where n.user.id = ?1 and n.leido = false" )
    long countNotificationNotView_Id(Long id);

    @Modifying
     @Transactional
    @Query("Update  NotificationClient n  SET n.leido = true  where n.user.id = ?1" )
    void updateByUser(Long id);



}
