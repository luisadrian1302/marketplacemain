package com.example.marketplacemain.marketplacemain.autenticacion.entities;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(mappedBy = "usuario" , orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"usuario", "handler", "hibernateLazyInitializer"})
        

    private Client client;

    @OneToOne(mappedBy = "usuario" , orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"usuario", "handler", "hibernateLazyInitializer"})
   
    private Vendedor vendedor;


    private String nombre;
    private String apellidos;

    @Column(unique = true)
    private String email;

    @JsonProperty(access = Access.WRITE_ONLY)

    private String password;

    @Column(name = "codigo_verificacion")

    private Integer codigoVerificacion;

    private boolean isVerified;

    @Column(name = "numero_telefonico")
    private String numeroTelefonico;

    @Column(name = "code_number")
    private String code_number;

    @Column(name = "urlImage")
    private String urlImage;


    @Column(name = "is_vefiried_number")
    private boolean isVerifiedNumber;

    private boolean status;

    private boolean status_ban;

    @Transient
    private String rol;


    @Column(name = "fecha_creacion")

    private Date fechaCreacion;



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


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public boolean isVerified() {
        return isVerified;
    }


    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }


    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }


    public void setNumeroTelefonico(String numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }


    public boolean isVerifiedNumber() {
        return isVerifiedNumber;
    }


    public void setVerifiedNumber(boolean isVerifiedNumber) {
        this.isVerifiedNumber = isVerifiedNumber;
    }


    public boolean isStatus() {
        return status;
    }


    public void setStatus(boolean status) {
        this.status = status;
    }


    public boolean isStatus_ban() {
        return status_ban;
    }


    public void setStatus_ban(boolean status_ban) {
        this.status_ban = status_ban;
    }


    public Date getFechaCreacion() {
        return fechaCreacion;
    }


    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }


    public Client getClient() {
        return client;
    }


    public void setClient(Client client) {
        this.client = client;

        client.setUsuario(this);
    }

    public void removeClient(Client Client) {
        Client.setUsuario(null);
        this.client = null;
    }

    public Integer getCodigoVerificacion() {
        return codigoVerificacion;
    }


    public void setCodigoVerificacion(Integer codigoVerificacion) {
        this.codigoVerificacion = codigoVerificacion;
    }


    public String getRol() {
        return rol;
    }


    public void setRol(String rol) {
        this.rol = rol;
    }


    public Vendedor getVendedor() {
        return vendedor;
    }



    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
        vendedor.setUsuario(this);
    }


    public String getApellidos() {
        return apellidos;
    }


    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }


    public String getCode_number() {
        return code_number;
    }


    public void setCode_number(String code_number) {
        this.code_number = code_number;
    }


    public String getUrlImage() {
        return urlImage;
    }


    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    




}
