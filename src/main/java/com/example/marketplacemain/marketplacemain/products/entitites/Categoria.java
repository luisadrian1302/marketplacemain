package com.example.marketplacemain.marketplacemain.products.entitites;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String ruta;
    private Boolean status;
    private Date fecha_publicacion;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="categoria")
    @JsonIgnoreProperties({"categoria", "handler", "hibernateLazyInitializer"})

    private Set<Subcategoria> subcategorias;


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
    public Set<Subcategoria> getSubcategorias() {
        return subcategorias;
    }
    public void setSubcategorias(Set<Subcategoria> subcategorias) {
        this.subcategorias = subcategorias;
    }
    public Categoria addSubcategoria(Subcategoria subcategoria) {
        subcategorias.add(subcategoria);
        subcategoria.setCategoria(this);
        return this;
    }
    public void removeSubcategoria(Subcategoria subcategoria) {
        this.getSubcategorias().remove(subcategoria);
        subcategoria.setCategoria(null);
    }

    
}
