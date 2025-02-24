package com.example.marketplacemain.marketplacemain.products.DTO;

public class ValorPropiedadDTO {

    private String tipoPropiedad;
    private String valor;
    private String nombre;
    
     
   
    public ValorPropiedadDTO(String tipoPropiedad, String valor, String nombre) {
        this.tipoPropiedad = tipoPropiedad;
        this.valor = valor;
        this.nombre = nombre;
    }
    public ValorPropiedadDTO() {
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

    

}
