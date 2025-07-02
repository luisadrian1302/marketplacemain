package com.example.marketplacemain.marketplacemain.products.entitites;

import jakarta.persistence.*;
import java.time.LocalDateTime;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Atributo")
public class Atributo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_subcategoria")
    @JsonIgnoreProperties({"atributos", "handler", "hibernateLazyInitializer", "productos", "categorias"})
    private Subcategoria subcategoria;


  

    @Column(nullable = false, length = 255)
    private String nombre;

    @Column(name = "estatus_validacion", nullable = false)
    private Byte estatusValidacion;

    @Column(nullable = false)
    private int privacidad;

    @Column(name = "fecha_creacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaModificacion;



    @Column(name = "tipo_propiedad", length = 255)
    private String tipoPropiedad;

  

    @Column(nullable = false)
    private Byte status;

    



    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public Subcategoria getSubcategoria() {
        return subcategoria;
    }



    public void setSubcategoria(Subcategoria subcategoria) {
        this.subcategoria = subcategoria;
    }



 


    public String getNombre() {
        return nombre;
    }



    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    public Byte getEstatusValidacion() {
        return estatusValidacion;
    }



    public void setEstatusValidacion(Byte estatusValidacion) {
        this.estatusValidacion = estatusValidacion;
    }



    public int getPrivacidad() {
        return privacidad;
    }



    public void setPrivacidad(int privacidad) {
        this.privacidad = privacidad;
    }



    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }



    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }



    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }



    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }






    public String getTipoPropiedad() {
        return tipoPropiedad;
    }



    public void setTipoPropiedad(String tipoPropiedad) {
        this.tipoPropiedad = tipoPropiedad;
    }



    public Byte getStatus() {
        return status;
    }



    public void setStatus(Byte status) {
        this.status = status;
    }

    

}
