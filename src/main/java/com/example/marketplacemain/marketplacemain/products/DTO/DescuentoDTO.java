package com.example.marketplacemain.marketplacemain.products.DTO;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DescuentoDTO {

    @NotNull
    private Double porcentajeDescuento;

    @NotNull
    private LocalDate fechaInicioDescuento;

    @NotNull
    private LocalDate fechaFinalDescuento;

    @NotBlank
    private String nombre;

    public Double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }
    public void setPorcentajeDescuento(Double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }
    public LocalDate getFechaInicioDescuento() {
        return fechaInicioDescuento;
    }
    public void setFechaInicioDescuento(LocalDate fechaInicioDescuento) {
        this.fechaInicioDescuento = fechaInicioDescuento;
    }
    public LocalDate getFechaFinalDescuento() {
        return fechaFinalDescuento;
    }
    public void setFechaFinalDescuento(LocalDate fechaFinalDescuento) {
        this.fechaFinalDescuento = fechaFinalDescuento;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    

}
