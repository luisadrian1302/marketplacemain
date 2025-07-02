package com.example.marketplacemain.marketplacemain.products.services.validations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.marketplacemain.marketplacemain.products.DTO.AtributoDTO;
import com.example.marketplacemain.marketplacemain.products.DTO.ValorPropiedadDTO;
import com.example.marketplacemain.marketplacemain.products.entitites.Producto;
import com.example.marketplacemain.marketplacemain.products.entitites.SubProducto;
import com.example.marketplacemain.marketplacemain.products.services.SubproductoService;

public class ValidationSubproductService {


    public static void validateSubProductISNotEmpty(SubProducto subProducto){
        if (subProducto == null ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontró el subproducto.");
        }

    }

    public static void isSubProductDuplicate(List<ValorPropiedadDTO> valorPropiedadDTOsSubproduct, Integer countCaracteristicasSimilares,
    SubproductoService subproductoService, Long idSubproduct ){
     
        if (valorPropiedadDTOsSubproduct.size() <= countCaracteristicasSimilares  || countCaracteristicasSimilares == 2 ) {
            subproductoService.updateByID(idSubproduct, 4);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Un subproducto ya tiene los mismos valores y atributos");
        }

    }

    public static void getNamesFilesAndDelete(List<String> nombresGuardados,
    SubproductoService subproductoService, SubProducto idSubproduct ){
     
        if (nombresGuardados.size() == 0 ) {
            subproductoService.deleteSubProductByObject(idSubproduct);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este producto no dispone una imagen en JPG o PNG por favor, coloque al menos una imagen en su subproducto");
        }

    }

    public static void validateCantidadImages(List<MultipartFile> imagenes){
     
        if (imagenes.size() >= 15 ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Solo puede subir hasta 15 imagenes");
        }

    }

    public static void isSubProductDuplicateCreateOrUpdate(List<AtributoDTO> atributos, Integer countCaracteristicasSimilares){
        // 3 < 2 = true
        // 3 < 2 = true
     
        if (atributos.size() <= countCaracteristicasSimilares ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Un subproducto ya tiene los mismos valores y atributos");
        }

    }

    public static void VerificarCaracteristicas(List<AtributoDTO> atributos ){
     
        if (atributos.size() < 2 ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Coloque al menos dos caracteristicas principales al producto");
        }

    }

    public static void checkTwoSimilarProperties(Set<String> elementosEncontrados , List<ValorPropiedadDTO> valorPropiedadDTOsTotal ,
    SubproductoService subproductoService, Long idSubproduct ){
     
        if (elementosEncontrados.size() < 2 && valorPropiedadDTOsTotal.size() > 0 ) {
            subproductoService.updateByID(idSubproduct, 4);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El subproducto requiere al menos dos caracteristicas similares de sus variantes");
        }

    }

    public static void checkTwoSimilarProperties(Set<String> elementosEncontrados , List<ValorPropiedadDTO> valorPropiedadDTOsTotal){
     
        if (elementosEncontrados.size() < 2 && valorPropiedadDTOsTotal.size() > 0 ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El subproducto requiere al menos dos caracteristicas similares de sus variantes");
        }

    }


    public static void validateProductsIsNotPresent(Optional<Producto> product, SubproductoService subproductoService, Long idSubproduct) {
        if (!product.isPresent()) {   
            subproductoService.updateByID(idSubproduct, 4);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontro ese producto.");
        }
    }

    public static void getColorExist( List<AtributoDTO> atributos) {

        Optional<AtributoDTO> getColor = atributos.stream().filter(element -> element.getTipo().toLowerCase().equals("color") ).findFirst();
        if (getColor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Se detecto que un producto tiene un color, por favor asigne un color al producto.");
        } 
    }




    // public static void validateProductIsNotEmpty(Producto product) {
    //     if (product == null || product.getTitular() == null ) {
    //         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El subproducto esta vacio.");
    //     }
    // }
    

}
