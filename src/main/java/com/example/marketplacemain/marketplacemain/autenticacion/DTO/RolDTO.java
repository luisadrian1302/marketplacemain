package com.example.marketplacemain.marketplacemain.autenticacion.DTO;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class RolDTO {

    private String rol = "ROLE_CLIENT";

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
}
