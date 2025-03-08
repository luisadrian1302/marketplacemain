package com.example.marketplacemain.marketplacemain.products.services.validations;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import com.example.marketplacemain.marketplacemain.products.entitites.Descuento;

public class ValidationDiscountService {


    public static void validateDiscountIsNotEmpty(Descuento descuento){
         if (descuento ==  null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontro el descuento.");

        }

    }
}
