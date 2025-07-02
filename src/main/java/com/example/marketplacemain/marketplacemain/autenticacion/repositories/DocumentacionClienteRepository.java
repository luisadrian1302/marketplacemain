package com.example.marketplacemain.marketplacemain.autenticacion.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.marketplacemain.marketplacemain.autenticacion.entities.DocumentacionCliente;
import com.example.marketplacemain.marketplacemain.autenticacion.entities.User;

import java.util.List;


public interface DocumentacionClienteRepository  extends CrudRepository<DocumentacionCliente, Long>{


    List<DocumentacionCliente> findByUsuario(User usuario);



    @Query("Select d from DocumentacionCliente d where d.usuario.id  = ?1")
    List<DocumentacionCliente> getDocumentationByIdUser(Long id);





}
