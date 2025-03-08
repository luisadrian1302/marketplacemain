package com.example.marketplacemain.marketplacemain.products.services.validations;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import com.example.marketplacemain.marketplacemain.products.entitites.Producto;

public class ValidationProductService {

    public static void validateProductsIsNotEmpty(Set<Producto> product) {
        if (product == null ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontro ese producto.");
        }
    }

    public static void validateProductsIsNotPresent(Optional<Producto> product) {
        if (!product.isPresent()) {   
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontro ese producto.");
        }
    }

    public static void countValidation(Long count) {
        if ( count> 0 ) {   
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hay subproductos dentro del producto, elimine todos los subproductos para eliminar el producto.");
        }
    }

  
}
