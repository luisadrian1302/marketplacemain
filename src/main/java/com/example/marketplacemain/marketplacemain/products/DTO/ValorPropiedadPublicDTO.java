package com.example.marketplacemain.marketplacemain.products.DTO;

public class ValorPropiedadPublicDTO {
    private String tipoPropiedad;
    private String valor;
    private String nombre;
    private Long id;
    public ValorPropiedadPublicDTO(String tipoPropiedad, String valor, String nombre, Long id) {
        this.tipoPropiedad = tipoPropiedad;
        this.valor = valor;
        this.nombre = nombre;
        this.id = id;
    }
    public ValorPropiedadPublicDTO() {
    }
    public String getTipoPropiedad() {
        return tipoPropiedad;
    }
    public void setTipoPropiedad(String tipoPropiedad) {
        this.tipoPropiedad = tipoPropiedad;
    }
    public String getValor() {
        return valor;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Long getid() {
        return id;
    }
    public void setid(Long id) {
        this.id = id;
    }

    
}
