package com.example.marketplacemain.marketplacemain.products.entitites;

import com.example.marketplacemain.marketplacemain.autenticacion.entities.Vendedor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;


      // muchas facturas para un cliente 
    @ManyToOne
    //colocamos el nombre de nuestra base de datos
    @JoinColumn(name = "id_vendedor")
    private Vendedor vendedor;

    private int idSubcategoria;
    private String titular;
    private String descripcionGeneral;
    private String fechaPublicacion;
    private String estadoAprobacion;
    private String status;
    private String seo;
    private String fechaModificacion;
    private String marca;
    private Integer idMarca; // Campo opcional, por eso se usa Integer
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
   
    public int getIdSubcategoria() {
        return idSubcategoria;
    }
    public void setIdSubcategoria(int idSubcategoria) {
        this.idSubcategoria = idSubcategoria;
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
    public String getFechaPublicacion() {
        return fechaPublicacion;
    }
    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
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
    public String getFechaModificacion() {
        return fechaModificacion;
    }
    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
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


    

}
