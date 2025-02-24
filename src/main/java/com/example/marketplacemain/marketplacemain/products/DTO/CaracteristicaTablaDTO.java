package com.example.marketplacemain.marketplacemain.products.DTO;

public class CaracteristicaTablaDTO {

    private String atributo;
    private String valor;

    // Constructor vacío (necesario para frameworks como Jackson)
    public CaracteristicaTablaDTO() {}

    // Constructor con parámetros
    public CaracteristicaTablaDTO(String atributo, String valor) {
        this.atributo = atributo;
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "CaracteristicaTablaDTO{" +
                "atributo='" + atributo + '\'' +
                ", valor='" + valor + '\'' +
                '}';
    }

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    
}
