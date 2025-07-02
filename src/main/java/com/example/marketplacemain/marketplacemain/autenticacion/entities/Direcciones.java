package com.example.marketplacemain.marketplacemain.autenticacion.entities;

import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@SQLRestriction("status = '1'")
public class Direcciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

       // muchas facturas para un cliente 
    @ManyToOne
    //colocamos el nombre de nuestra base de datos
    @JoinColumn(name = "id_vendedor")
    @JsonIgnoreProperties({ "handler", "hibernateLazyInitializer"})

    private Client client;


    private String pais;
    private String estado;
    private String ciudad;
    private String municipio;
    private String colonia;
    private String calle;
    private String codigoPostal;
    private String entre_calle;
    private String y_calle;
    private String numero_externo;
    private String numero_interno;
    private String referencias;
    private String latitude;
    private String longitud;
    private Integer status;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public String getPais() {
        return pais;
    }
    public void setPais(String pais) {
        this.pais = pais;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getCiudad() {
        return ciudad;
    }
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    public String getMunicipio() {
        return municipio;
    }
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }
    public String getColonia() {
        return colonia;
    }
    public void setColonia(String colonia) {
        this.colonia = colonia;
    }
    public String getCalle() {
        return calle;
    }
    public void setCalle(String calle) {
        this.calle = calle;
    }
    public String getEntre_calle() {
        return entre_calle;
    }
    public void setEntre_calle(String entre_calle) {
        this.entre_calle = entre_calle;
    }
    public String getY_calle() {
        return y_calle;
    }
    public void setY_calle(String y_calle) {
        this.y_calle = y_calle;
    }
    public String getNumero_externo() {
        return numero_externo;
    }
    public void setNumero_externo(String numero_externo) {
        this.numero_externo = numero_externo;
    }
    public String getNumero_interno() {
        return numero_interno;
    }
    public void setNumero_interno(String numero_interno) {
        this.numero_interno = numero_interno;
    }
    public String getReferencias() {
        return referencias;
    }
    public void setReferencias(String referencias) {
        this.referencias = referencias;
    }
    public String getCodigoPostal() {
        return codigoPostal;
    }
    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLongitud() {
        return longitud;
    }
    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    


}
