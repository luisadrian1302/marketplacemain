package com.example.marketplacemain.marketplacemain.autenticacion.validation;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.marketplacemain.marketplacemain.autenticacion.entities.DocumentacionCliente;

public class ValidationDocumentClient {

     public static void validateProductsIsNotPresent(DocumentacionCliente docclient) {
        if (docclient == null) {   
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontro la documentación de ese usuario.");
        }
    }

}
