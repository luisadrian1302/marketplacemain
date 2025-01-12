package com.example.marketplacemain.marketplacemain.products.entitites;

import java.util.Date;

import com.example.marketplacemain.marketplacemain.autenticacion.entities.Vendedor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Subcategoria {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

       // muchas facturas para un cliente 
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    private String nombre;
    private String ruta;
    private Boolean status;
    private Date fecha_publicacion;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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
    public Boolean getStatus() {
        return status;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }
    public Date getFecha_publicacion() {
        return fecha_publicacion;
    }
    public void setFecha_publicacion(Date fecha_publicacion) {
        this.fecha_publicacion = fecha_publicacion;
    }
    public Categoria getCategoria() {
        return categoria;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    
}
