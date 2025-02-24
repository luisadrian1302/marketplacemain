package com.example.marketplacemain.marketplacemain.products.entitites;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Subcategoria {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

       // muchas facturas para un cliente 
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    @JsonIgnoreProperties({"subcategorias", "handler", "hibernateLazyInitializer"})
    private Categoria categoria;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="subcategoria")
    @JsonIgnoreProperties({"subcategoria", "handler", "hibernateLazyInitializer"})
    private Set<Producto> productos;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="subcategoria")
    @JsonIgnoreProperties({"subcategoria", "handler", "hibernateLazyInitializer"})
    private Set<Atributo> atributos;

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

    public Subcategoria addProduct(Producto producto) {
        productos.add(producto);
        producto.setSubcategoria(this);
        return this;
    }

    public void removeProduct(Producto producto) {
        this.getProductos().remove(producto);
        producto.setSubcategoria(null);
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }

    public Subcategoria addAtributo(Atributo producto) {
        atributos.add(producto);
        producto.setSubcategoria(this);
        return this;
    }

    public void removeAtributo(Atributo producto) {
        this.getAtributos().remove(producto);
        producto.setSubcategoria(null);
    }

    public Set<Atributo> getAtributos() {
        return atributos;
    }
    public void setAtributos(Set<Atributo> atributos) {
        this.atributos = atributos;
    }



    
    
}
