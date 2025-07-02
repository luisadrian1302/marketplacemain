package com.example.marketplacemain.marketplacemain.autenticacion.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.marketplacemain.marketplacemain.autenticacion.entities.DocumentacionCliente;
import com.example.marketplacemain.marketplacemain.autenticacion.entities.User;
import com.example.marketplacemain.marketplacemain.autenticacion.repositories.DocumentacionClienteRepository;


@Service
public class DocumentacionClientService {
     @Autowired
    private  DocumentacionClienteRepository repository;

    public List<DocumentacionCliente> getByuse(User user ){
        return repository.findByUsuario(user);
    }

    public DocumentacionCliente getById(Long id ){

        List<DocumentacionCliente> dcOptional = repository.getDocumentationByIdUser(id);

        if (dcOptional.size() > 0) {
            return dcOptional.get(0);
        }
        return null;
    }


    


    public DocumentacionCliente save(DocumentacionCliente dc ){
        return repository.save(dc);
    }

}
