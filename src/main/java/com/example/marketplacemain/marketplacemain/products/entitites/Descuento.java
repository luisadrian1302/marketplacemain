package com.example.marketplacemain.marketplacemain.products.entitites;

import java.time.LocalDate;

import org.hibernate.annotations.SQLRestriction;

import com.example.marketplacemain.marketplacemain.autenticacion.entities.Vendedor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "descuento")
@SQLRestriction("status = 1")
public class Descuento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
      // muchas facturas para un cliente 
    @ManyToOne
    //colocamos el nombre de nuestra base de datos
    @JoinColumn(name = "id_vendedor")
    @JsonIgnoreProperties({"vendedor"})
    private Vendedor vendedor;


    @Column(name = "porcentaje_descuento", nullable = false)
    private Double porcentajeDescuento;

    @Column(name = "fecha_inicio_descuento", nullable = false)
    private LocalDate fechaInicioDescuento;

    @Column(name = "fecha_final_descuento", nullable = false)
    private LocalDate fechaFinalDescuento;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "fecha_publicacion")
    private LocalDate fechaPublicacion;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    
    

}
