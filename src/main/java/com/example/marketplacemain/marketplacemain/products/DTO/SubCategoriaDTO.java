package com.example.marketplacemain.marketplacemain.products.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SubCategoriaDTO {


    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotNull(message = "La categoria es obligatorio")
    @Min(value = 1, message = "La categoria es obligatoria")
    private Long idCategoria;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }


    

}
