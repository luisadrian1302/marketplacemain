package com.example.marketplacemain.marketplacemain.autenticacion.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.marketplacemain.marketplacemain.autenticacion.entities.User;
import java.util.List;


public interface UserRepositories extends CrudRepository<User, Long> {

    List<User> findByEmail(String email);

}
