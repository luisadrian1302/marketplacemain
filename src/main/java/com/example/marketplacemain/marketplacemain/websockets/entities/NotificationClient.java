package com.example.marketplacemain.marketplacemain.websockets.entities;

import java.time.LocalDate;

import org.hibernate.annotations.SQLRestriction;

import com.example.marketplacemain.marketplacemain.autenticacion.entities.Client;
import com.example.marketplacemain.marketplacemain.autenticacion.entities.User;
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
@Table
@SQLRestriction("status = 1")
public class NotificationClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    //colocamos el nombre de nuestra base de datos
    @JoinColumn(name = "id_user")
    @JsonIgnoreProperties({ "handler", "hibernateLazyInitializer"})

    private Client user;


    private String tipo;
    private String titulo;
    private String descripcion;
    private Boolean leido;

    private String url_direccion;
    private String urlImagen;
    private Byte status;

    @Column(name = "fecha_publicacion")
    private LocalDate fechaPublicacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getLeido() {
        return leido;
    }

    public void setLeido(Boolean leido) {
        this.leido = leido;
    }

    public String getUrl_direccion() {
        return url_direccion;
    }

    public void setUrl_direccion(String url_direccion) {
        this.url_direccion = url_direccion;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Client getUser() {
        return user;
    }

    public void setUser(Client user) {
        this.user = user;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    


}
