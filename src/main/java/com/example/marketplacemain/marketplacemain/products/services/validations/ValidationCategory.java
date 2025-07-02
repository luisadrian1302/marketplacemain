package com.example.marketplacemain.marketplacemain.products.services.validations;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.marketplacemain.marketplacemain.products.DTO.CreateCategoriaDTO;
import com.example.marketplacemain.marketplacemain.products.entitites.Categoria;
import com.example.marketplacemain.marketplacemain.products.entitites.Descuento;
import com.example.marketplacemain.marketplacemain.products.services.CategoriaService;

public class ValidationCategory {

     public static void validateExistName(CreateCategoriaDTO entity, CategoriaService service){

        if (service.existsByNombre(entity.getNombre())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe una categoria con ese nombre, intente con otro.");
            
        }
    }


 
    public static void validateExistRoute(CreateCategoriaDTO entity, CategoriaService service){

        if (service.existsByRuta(entity.getRuta())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe una categoria con esa ruta, intente con otro.");
            
        }
    }

    public static void existCategoria(Categoria categoria){

        if (categoria == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe la categoria.");
            
        }
    }
    public static void existCategorias(List<Categoria> categorias){

        if (categorias.size() > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existen categorias con ese nombre.");
            
        }
    }
}
