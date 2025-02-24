package com.example.marketplacemain.marketplacemain.products.DTO;

public record GetSubProductosDTO(
    Long id,
    String titular,
    String descripcionGeneral,
    Double precio,
    Integer stock,
    Integer status,
    Double tamañoAlto,
    Double tamañoAncho,
    Integer statusValidacion

) {}
