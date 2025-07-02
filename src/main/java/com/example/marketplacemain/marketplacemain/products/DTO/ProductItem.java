package com.example.marketplacemain.marketplacemain.products.DTO;

public record ProductItem(

     String titular,
     Double precio,
     Double porcentaje_descuento,
     Double puntuacion,
     Long count,
     String marca,
     String multimedia,
     Long total_subproductos,
     Long id


) {}
