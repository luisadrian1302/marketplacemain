package com.example.marketplacemain.marketplacemain.autenticacion.entities;

import java.util.Set;

import com.example.marketplacemain.marketplacemain.products.entitites.Atributo;
import com.example.marketplacemain.marketplacemain.products.entitites.Descuento;
import com.example.marketplacemain.marketplacemain.products.entitites.Producto;
import com.example.marketplacemain.marketplacemain.products.entitites.Subcategoria;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Vendedor {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private User usuario;

    private Boolean status;

    private Boolean banned;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="vendedor")
    @JsonIgnoreProperties({"vendedor", "handler", "hibernateLazyInitializer"})
    private Set<Producto> productos;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="vendedor")
    @JsonIgnoreProperties({"vendedor", "handler", "hibernateLazyInitializer"})
    private Set<Descuento> descuentos;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="vendedor")
    @JsonIgnoreProperties({"vendedor", "handler", "hibernateLazyInitializer"})
    private Set<Atributo> atributos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

 
    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    
    public Vendedor addProduct(Producto producto) {
        productos.add(producto);
        producto.setVendedor(this);
        return this;
    }

    public void removeProduct(Producto producto) {
        this.getProductos().remove(producto);
        producto.setVendedor(null);
    }


    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }


    public Set<Descuento> getDescuentos() {
        return descuentos;
    }

    public Vendedor addDescuento(Descuento descuento) {
        descuentos.add(descuento);
        descuento.setVendedor(this);
        return this;
    }

    public void removeDescuento(Descuento descuento) {
        this.getDescuentos().remove(descuento);
        descuento.setVendedor(null);
    }


    public void setDescuento(Set<Descuento> descuento) {
        this.descuentos = descuento;
    }

     public Vendedor addAtributo(Atributo producto) {
        atributos.add(producto);
        producto.setVendedor(this);
        return this;
    }

    public void removeAtributo(Atributo producto) {
        this.getAtributos().remove(producto);
        producto.setVendedor(null);
    }

    public Set<Atributo> getAtributos() {
        return atributos;
    }

    public void setAtributos(Set<Atributo> atributos) {
        this.atributos = atributos;
    }
    

    
}
