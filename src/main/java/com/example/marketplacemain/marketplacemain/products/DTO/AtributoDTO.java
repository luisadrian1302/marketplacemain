package com.example.marketplacemain.marketplacemain.products.DTO;

public class AtributoDTO {
    private Long id_atributo;
    private String valor;
    private String tipo;
    private String propiedad;

    // Constructor vacío necesario para Jackson
    public AtributoDTO() {}

    // Constructor con parámetros
    public AtributoDTO(Long id_atributo, String valor, String tipo, String propiedad) {
        this.id_atributo = id_atributo;
        this.valor = valor;
        this.tipo = tipo;
        this.propiedad = propiedad;
    }

    @Override
    public String toString() {
        return "AtributoDTO{" +
                "idAtributo='" + id_atributo + '\'' +
                ", valor='" + valor + '\'' +
                ", tipo='" + tipo + '\'' +
                ", propiedad='" + propiedad + '\'' +
                '}';
    }

    public Long getId_atributo() {
        return id_atributo;
    }

    public void setId_atributo(Long id_atributo) {
        this.id_atributo = id_atributo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(String propiedad) {
        this.propiedad = propiedad;
    }

    
}