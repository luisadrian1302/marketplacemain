package com.example.marketplacemain.marketplacemain.products.services.validations;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.marketplacemain.marketplacemain.products.entitites.Producto;
import com.example.marketplacemain.marketplacemain.products.entitites.Subcategoria;

public class ValidationSubcategoryService {

     public static void validateProductsIsNotEmpty(Subcategoria subcategoria ) {
        if (subcategoria == null ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se pudo crear este producto, no se encontró el id de la subcategoria.");
        }
    }


}
