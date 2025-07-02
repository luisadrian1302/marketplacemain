package com.example.marketplacemain.marketplacemain.autenticacion.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Documentacion_Cliente")
public class DocumentacionCliente {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    @JsonIgnoreProperties({ "handler", "hibernateLazyInitializer", "client", "employee", "vendedor"})

    private User usuario;


    @Column(name = "ine_foto")
    private String ineFoto;

    @Column(name = "status_ine")
    private Integer statusIne;

    @Column(name = "face_real")
    private String faceReal;

    @Column(name = "status_face_real")
    private Integer statusfaceReal;

    @Column(name = "image_curp")
    private String imageCurp;

    @Column(name = "status_image_curp")
    private Integer statusImageCurp;


    @Column(length = 18)
    private String curp;

    @Column(name = "status_documentacion")
    private String statusDocumentacion;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_aprobacion")
    private LocalDateTime fechaAprobacion;

    @ManyToOne
    @JoinColumn(name = "id_aprobador")
     @JsonIgnoreProperties({ "handler", "hibernateLazyInitializer", "usuario"})
    private Employee aprovador;

    
    // Getters y Setters

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

    public String getIneFoto() {
        return ineFoto;
    }

    public void setIneFoto(String ineFoto) {
        this.ineFoto = ineFoto;
    }

    public String getFaceReal() {
        return faceReal;
    }

    public void setFaceReal(String faceReal) {
        this.faceReal = faceReal;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getStatusDocumentacion() {
        return statusDocumentacion;
    }

    public void setStatusDocumentacion(String statusDocumentacion) {
        this.statusDocumentacion = statusDocumentacion;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(LocalDateTime fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

   

    public String getImageCurp() {
        return imageCurp;
    }

    public void setImageCurp(String imageCurp) {
        this.imageCurp = imageCurp;
    }

    public Employee getAprovador() {
        return aprovador;
    }

    public void setAprovador(Employee aprovador) {
        this.aprovador = aprovador;
    }

    public Integer getStatusIne() {
        return statusIne;
    }

    public void setStatusIne(Integer statusIne) {
        this.statusIne = statusIne;
    }

    public Integer getStatusfaceReal() {
        return statusfaceReal;
    }

    public void setStatusfaceReal(Integer statusfaceReal) {
        this.statusfaceReal = statusfaceReal;
    }

    public Integer getStatusImageCurp() {
        return statusImageCurp;
    }

    public void setStatusImageCurp(Integer statusImageCurp) {
        this.statusImageCurp = statusImageCurp;
    }

    
    
}
