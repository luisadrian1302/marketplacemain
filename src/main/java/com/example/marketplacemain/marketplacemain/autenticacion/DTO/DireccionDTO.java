package com.example.marketplacemain.marketplacemain.autenticacion.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class DireccionDTO {

    @NotBlank(message = "La calle es obligatoria")
    @Size(max = 100, message = "La calle no puede tener más de 100 caracteres")
    private String calle;

    @NotBlank(message = "La ciudad es obligatoria")
    @Size(max = 100, message = "La ciudad no puede tener más de 100 caracteres")
    private String ciudad;

    @NotBlank(message = "El código postal es obligatorio")
    @Pattern(regexp = "\\d{5}", message = "El código postal debe tener 5 dígitos")
    private String codigoPostal;

    @NotBlank(message = "La colonia es obligatoria")
    @Size(max = 100, message = "La colonia no puede tener más de 100 caracteres")
    private String colonia;

    @NotBlank(message = "Entre calle es obligatorio")
    @Size(max = 100, message = "Entre calle no puede tener más de 100 caracteres")
    private String entreCalle;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 50, message = "El estado no puede tener más de 50 caracteres")
    private String estado;

    @NotBlank(message = "El municipio es obligatorio")
    @Size(max = 100, message = "El municipio no puede tener más de 100 caracteres")
    private String municipio;

    @NotBlank(message = "El número externo es obligatorio")
    @Size(max = 10, message = "El número externo no puede tener más de 10 caracteres")
    private String numeroExterno;

    @Size(max = 10, message = "El número interno no puede tener más de 10 caracteres")
    private String numeroInterno; // Opcional

    @NotBlank(message = "El país es obligatorio")
    @Size(max = 50, message = "El país no puede tener más de 50 caracteres")
    private String pais;

    @Size(max = 255, message = "Las referencias no pueden tener más de 255 caracteres")
    private String referencias; // Opcional

    @Size(max = 100, message = "La calle 'yCalle' no puede tener más de 100 caracteres")
    private String yCalle; //opcional

    @Size(max = 60, message = "Longitud no valida")
    private String latitude; // Opcional

    @Size(max = 60, message = "Longitud no valida")
    private String longitud; // Opcional

    


    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getEntreCalle() {
        return entreCalle;
    }

    public void setEntreCalle(String entreCalle) {
        this.entreCalle = entreCalle;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getNumeroExterno() {
        return numeroExterno;
    }

    public void setNumeroExterno(String numeroExterno) {
        this.numeroExterno = numeroExterno;
    }

    public String getNumeroInterno() {
        return numeroInterno;
    }

    public void setNumeroInterno(String numeroInterno) {
        this.numeroInterno = numeroInterno;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getReferencias() {
        return referencias;
    }

    public void setReferencias(String referencias) {
        this.referencias = referencias;
    }

    public String getyCalle() {
        return yCalle;
    }

    public void setyCalle(String yCalle) {
        this.yCalle = yCalle;
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

    
}

