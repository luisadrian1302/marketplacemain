package com.example.marketplacemain.marketplacemain.websockets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.marketplacemain.marketplacemain.websockets.entities.NotificationClient;
import com.example.marketplacemain.marketplacemain.websockets.entities.NotificationEmployee;

import jakarta.transaction.Transactional;

public interface NotificationEmployeeRepository extends CrudRepository<NotificationEmployee, Long>{

    @Query("SELECT COUNT(n.id) FROM NotificationEmployee n where n.user.id = ?1 and n.leido = false" )
    long countNotificationNotView_Id(Long id);

     @Query("select n from NotificationEmployee n where n.user.id = ?1")
    List<NotificationEmployee> getByIdClient(Long id);


    @Modifying
    @Transactional
    @Query("Update  NotificationEmployee n  SET n.leido = true  where n.user.id = ?1" )
    void updateByUser(Long id);
}
