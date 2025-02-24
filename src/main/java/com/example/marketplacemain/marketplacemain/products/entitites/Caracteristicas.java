package com.example.marketplacemain.marketplacemain.products.entitites;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Caracteristicas")
public class Caracteristicas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_atributo")
    private Atributo atributo;

    @ManyToOne
    @JoinColumn(name = "id_valor")
    private Valor valor;

    @ManyToOne
    @JoinColumn(name = "id_subproducto")
   
    private SubProducto subProducto;


    @Column(nullable = false)
    private Byte status;

    @Column(name = "fecha_publicacion", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private LocalDateTime fechaPublicacion;

    @Column(name = "colocar_caracteristica_principal", nullable = false)
    private Byte colocarCaracteristicaPrincipal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Atributo getAtributo() {
        return atributo;
    }

    public void setAtributo(Atributo atributo) {
        this.atributo = atributo;
    }

    public Valor getValor() {
        return valor;
    }

    public void setValor(Valor valor) {
        this.valor = valor;
    }

 
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Byte getColocarCaracteristicaPrincipal() {
        return colocarCaracteristicaPrincipal;
    }

    public void setColocarCaracteristicaPrincipal(Byte colocarCaracteristicaPrincipal) {
        this.colocarCaracteristicaPrincipal = colocarCaracteristicaPrincipal;
    }

    public SubProducto getSubProducto() {
        return subProducto;
    }

    public void setSubProducto(SubProducto subProducto) {
        this.subProducto = subProducto;
    }



    
}
