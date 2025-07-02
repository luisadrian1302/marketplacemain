package com.example.marketplacemain.marketplacemain.products.services.validations;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.marketplacemain.marketplacemain.products.DTO.CreateCategoriaDTO;
import com.example.marketplacemain.marketplacemain.products.DTO.SubCategoriaDTO;
import com.example.marketplacemain.marketplacemain.products.entitites.Categoria;
import com.example.marketplacemain.marketplacemain.products.entitites.Producto;
import com.example.marketplacemain.marketplacemain.products.entitites.Subcategoria;
import com.example.marketplacemain.marketplacemain.products.services.CategoriaService;
import com.example.marketplacemain.marketplacemain.products.services.SubcategoriasService;

public class ValidationSubcategoryService {

     public static void validateProductsIsNotEmpty(Subcategoria subcategoria ) {
        if (subcategoria == null ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se pudo crear este producto, no se encontró el id de la subcategoria.");
        }
    }


    public static void validateExistName(SubCategoriaDTO entity, SubcategoriasService service){

        if (service.existsByNombre(entity.getNombre())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe una subcategoria con ese nombre, intente con otro.");
            
        }
    }


   
    public static void existCategoria(Subcategoria subcategoria){

        if (subcategoria == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe la subcategoria.");
            
        }
    }
    public static void existsubCategorias(List<Subcategoria> subcategoria){

        if (subcategoria.size() > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existen subcategoria con ese nombre.");
            
        }
    }


}
