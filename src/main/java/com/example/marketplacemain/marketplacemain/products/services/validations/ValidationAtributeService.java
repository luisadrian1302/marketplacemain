package com.example.marketplacemain.marketplacemain.products.services.validations;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.marketplacemain.marketplacemain.products.entitites.Atributo;
import com.example.marketplacemain.marketplacemain.products.entitites.Subcategoria;

public class ValidationAtributeService {

     public static void existCategoria(Atributo atributo){

        if (atributo == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe el atributo.");
            
        }
    }

}
