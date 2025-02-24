package com.example.marketplacemain.marketplacemain.products.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class SubproductoDTO {
    @Min(value = 0, message = "El precio del producto debe ser mayor a 0")
    private Double precio;

    @Min(value = 0, message = "La cantidad del producto debe ser mayor a 0")
    private Integer cantidad;

    @Min(value = 0, message = "El peso del producto debe ser mayor a 0")
    private Double peso;

    @Min(value = 0, message = "El grosor del producto debe ser mayor a 0")
    private Double grosor;

    @Min(value = 0, message = "El largo del producto debe ser mayor a 0")
    private Double largo;

    @Min(value = 0, message = "El ancho del producto debe ser mayor a 0")
    private Double ancho;


    @Size(min = 3, max = 50, message = "La descripcion debe tener entre 3 y 255 caracteres")
    private String descripcion;

    // Constructor vacío (necesario para Jackson y otros frameworks)
    public SubproductoDTO() {}

    // Constructor con parámetros
    public SubproductoDTO(Double precio, Integer cantidad, Double peso, Double grosor, Double largo, Double ancho, String descripcion) {
        this.precio = precio;
        this.cantidad = cantidad;
        this.peso = peso;
        this.grosor = grosor;
        this.largo = largo;
        this.ancho = ancho;
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "ProductoDTO{" +
                "precio='" + precio + '\'' +
                ", cantidad='" + cantidad + '\'' +
                ", peso='" + peso + '\'' +
                ", grosor='" + grosor + '\'' +
                ", largo='" + largo + '\'' +
                ", ancho='" + ancho + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

   

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getGrosor() {
        return grosor;
    }

    public void setGrosor(Double grosor) {
        this.grosor = grosor;
    }

    public Double getLargo() {
        return largo;
    }

    public void setLargo(Double largo) {
        this.largo = largo;
    }

    public Double getAncho() {
        return ancho;
    }

    public void setAncho(Double ancho) {
        this.ancho = ancho;
    }


    
}
