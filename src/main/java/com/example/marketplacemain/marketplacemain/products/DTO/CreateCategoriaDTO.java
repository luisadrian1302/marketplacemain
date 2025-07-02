package com.example.marketplacemain.marketplacemain.products.DTO;

import jakarta.validation.constraints.NotBlank;

public class CreateCategoriaDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    private String ruta;
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getRuta() {
        return ruta;
    }
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
    



}
