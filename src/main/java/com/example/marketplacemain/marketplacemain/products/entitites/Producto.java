package com.example.marketplacemain.marketplacemain.products.entitites;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.SQLRestriction;

import com.example.marketplacemain.marketplacemain.autenticacion.entities.Vendedor;
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
@SQLRestriction("status <> 'deleted'")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;


      // muchas facturas para un cliente 
    @ManyToOne
    //colocamos el nombre de nuestra base de datos
    @JoinColumn(name = "id_vendedor")
    @JsonIgnoreProperties({"productos", "handler", "hibernateLazyInitializer", "descuentos", "atributos"})

    private Vendedor vendedor;


    @ManyToOne
    @JoinColumn(name = "id_subcategoria")
    @JsonIgnoreProperties({"productos", "handler", "hibernateLazyInitializer", "atributos"})

    private Subcategoria subcategoria;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="producto")
    @JsonIgnoreProperties({"producto", "handler", "hibernateLazyInitializer", "descuento", "caracteristicasTable"})
    private Set<SubProducto> subproducto;

    // private int idSubcategoria;
    private String titular;
    private String descripcionGeneral;
    private LocalDateTime fechaPublicacion;
    private String estadoAprobacion;
    private String status;
    private String imagePortada;
    private String seo;
    private LocalDateTime fechaModificacion;
    private String marca;
    private Integer idMarca; 

    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
   
   
    public String getTitular() {
        return titular;
    }
    public void setTitular(String titular) {
        this.titular = titular;
    }
    public String getDescripcionGeneral() {
        return descripcionGeneral;
    }
    public void setDescripcionGeneral(String descripcionGeneral) {
        this.descripcionGeneral = descripcionGeneral;
    }
  
    public String getEstadoAprobacion() {
        return estadoAprobacion;
    }
    public void setEstadoAprobacion(String estadoAprobacion) {
        this.estadoAprobacion = estadoAprobacion;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getSeo() {
        return seo;
    }
    public void setSeo(String seo) {
        this.seo = seo;
    }
   
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public Integer getIdMarca() {
        return idMarca;
    }
    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }
    
    public Vendedor getVendedor() {
        return vendedor;
    }
    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }
    public Subcategoria getSubcategoria() {
        return subcategoria;
    }
    public void setSubcategoria(Subcategoria subcategoria) {
        this.subcategoria = subcategoria;
    }
    public String getImagePortada() {
        return imagePortada;
    }
    public void setImagePortada(String imagePortada) {
        this.imagePortada = imagePortada;
    }
    public Set<SubProducto> getSubproducto() {
        return subproducto;
    }
    public void setSubproducto(Set<SubProducto> subproducto) {
        this.subproducto = subproducto;
    }
    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }
    public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }
    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    // public Producto addSubproducto(SubProducto producto) {
    //     subproducto.add(producto);
    //     producto.setProducto(this);
    //     return this;
    // }

    // public void removeSubproducto(SubProducto producto) {
    //     this.getAtributos().remove(producto);
    //     producto.setVendedor(null);
    // }

    
    

}
