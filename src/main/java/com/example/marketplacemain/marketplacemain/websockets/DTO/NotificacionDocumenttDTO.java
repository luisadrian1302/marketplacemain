package com.example.marketplacemain.marketplacemain.websockets.DTO;

import java.time.LocalDate;

import com.example.marketplacemain.marketplacemain.autenticacion.entities.Employee;

public class NotificacionDocumenttDTO {

    private String tipo;
    private String tipoStatus;
    private String titulo;
    private String descripcion;
    private String url;
    private String urlImage;
    private Byte status;
    private LocalDate fechaInicio;
    private Employee empleado;

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getTipoStatus() {
        return tipoStatus;
    }
    public void setTipoStatus(String tipoStatus) {
        this.tipoStatus = tipoStatus;
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
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public Byte getStatus() {
        return status;
    }
    public void setStatus(Byte status) {
        this.status = status;
    }
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public Employee getEmpleado() {
        return empleado;
    }
    public void setEmpleado(Employee empleado) {
        this.empleado = empleado;
    }
    public String getUrlImage() {
        return urlImage;
    }
    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
    
    


}
