package com.example.marketplacemain.marketplacemain.products.entitites;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "subproducto")
@SQLRestriction("status = '1'")
// status 0 = "no existe"
// status 1 = "activo"
// status 2 = "pendiente"
// status 3 = "sin stock"
// status 4 = "eliminado"
public class SubProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "id_descuento")
    private Descuento descuento;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="subProducto")
    @JsonIgnoreProperties({"subProducto", "handler", "hibernateLazyInitializer"})
    private Set<Caracteristicas> caracteristicas;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="subProducto")
    @JsonIgnoreProperties({"subProducto", "handler", "hibernateLazyInitializer"})
    private Set<CaractertisticaTabla> caracteristicasTable;
 



    @Column(name = "foto_principal")
    private String fotoPrincipal;

    @Column(name = "multimedia")
    private String multimedia;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "status_validacion")
    private Integer statusValidacion;

    // @PrePersist
    // public void prePersist() {
    //     if (statusValidacion == null) {
    //         statusValidacion = 0;  // Asigna 0 si no tiene valor
    //     }
    //     LocalDateTime now = LocalDateTime.now();
    //     fechaPublicacion = now;  // Establece la fecha de creación si no está configurada
    //     fechaModificacion = now; // 
    // }

    @Column(name = "fecha_publicacion")
    private LocalDateTime fechaPublicacion;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;


    @PreUpdate
    public void preUpdate() {
        fechaModificacion = LocalDateTime.now(); // Actualiza la fecha de modificación en cada actualización
    }

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "condicion_producto")
    private String condicionProducto;

    @Column(name = "peso_producto")
    private Double pesoProducto;

    @Column(name = "tamaño_alto")
    private Double tamañoAlto;

    @Column(name = "tamaño_ancho")
    private Double tamañoAncho;

    @Column(name = "grosor")
    private Double grosor;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    // Constructor vacío (requerido por JPA)
    public SubProducto() {}

    // Constructor con parámetros
    public SubProducto(Producto idProducto, String fotoPrincipal, String multimedia, String descripcion, Double precio,
                       Integer status, LocalDateTime fechaPublicacion, LocalDateTime fechaModificacion, String modelo,
                       String condicionProducto, Double pesoProducto, Double tamañoAlto, Double tamañoAncho,
                       Double grosor, Descuento idDescuento) {
        this.producto = idProducto;
        this.fotoPrincipal = fotoPrincipal;
        this.multimedia = multimedia;
        this.descripcion = descripcion;
        this.precio = precio;
        this.status = status;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaModificacion = fechaModificacion;
        this.modelo = modelo;
        this.condicionProducto = condicionProducto;
        this.pesoProducto = pesoProducto;
        this.tamañoAlto = tamañoAlto;
        this.tamañoAncho = tamañoAncho;
        this.grosor = grosor;
        this.descuento = idDescuento;
    }

    @Override
    public String toString() {
        return "Subproducto{" +
                "id=" + id +
                ", idProducto=" + producto.getTitular() +
                ", fotoPrincipal='" + fotoPrincipal + '\'' +
                ", multimedia='" + multimedia + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", status=" + status +
                ", fechaPublicacion=" + fechaPublicacion +
                ", fechaModificacion=" + fechaModificacion +
                ", modelo='" + modelo + '\'' +
                ", condicionProducto='" + condicionProducto + '\'' +
                ", pesoProducto=" + pesoProducto +
                ", tamañoAlto=" + tamañoAlto +
                ", tamañoAncho=" + tamañoAncho +
                ", grosor=" + grosor +
                ", idDescuento=" + descuento.getNombre() +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getFotoPrincipal() {
        return fotoPrincipal;
    }

    public void setFotoPrincipal(String fotoPrincipal) {
        this.fotoPrincipal = fotoPrincipal;
    }

    public String getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(String multimedia) {
        this.multimedia = multimedia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCondicionProducto() {
        return condicionProducto;
    }

    public void setCondicionProducto(String condicionProducto) {
        this.condicionProducto = condicionProducto;
    }

    public Double getPesoProducto() {
        return pesoProducto;
    }

    public void setPesoProducto(Double pesoProducto) {
        this.pesoProducto = pesoProducto;
    }

    public Double getTamañoAlto() {
        return tamañoAlto;
    }

    public void setTamañoAlto(Double tamañoAlto) {
        this.tamañoAlto = tamañoAlto;
    }

    public Double getTamañoAncho() {
        return tamañoAncho;
    }

    public void setTamañoAncho(Double tamañoAncho) {
        this.tamañoAncho = tamañoAncho;
    }

    public Double getGrosor() {
        return grosor;
    }

    public void setGrosor(Double grosor) {
        this.grosor = grosor;
    }

    public Descuento getDescuento() {
        return descuento;
    }

    public void setDescuento(Descuento descuento) {
        this.descuento = descuento;
    }

    
    public Set<Caracteristicas> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(Set<Caracteristicas> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    

    public SubProducto addCaractertistica(Caracteristicas caracteristica) {
        caracteristicas.add(caracteristica);
        caracteristica.setSubProducto(this);
        return this;
    }
    
    public void removeCaracteristica(Caracteristicas caracteristica) {
        this.getCaracteristicas().remove(caracteristica);
        caracteristica.setSubProducto(null);
    }




    public Set<CaractertisticaTabla> getCaracteristicasTable() {
        return caracteristicasTable;
    }

    public void setCaracteristicasTable(Set<CaractertisticaTabla> caracteristicasTable2) {
        this.caracteristicasTable = caracteristicasTable2;
    }
    public SubProducto addCaractertisticaTabla(CaractertisticaTabla caracteristica) {
        caracteristicasTable.add(caracteristica);
        caracteristica.setSubProducto(this);
        return this;
    }
    
    public void removeCaracteristicaTable(CaractertisticaTabla caracteristica) {
        this.getCaracteristicasTable().remove(caracteristica);
        caracteristica.setSubProducto(null);
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStatusValidacion() {
        return statusValidacion;
    }

    public void setStatusValidacion(Integer statusValidacion) {
        this.statusValidacion = statusValidacion;
    }


    


}
