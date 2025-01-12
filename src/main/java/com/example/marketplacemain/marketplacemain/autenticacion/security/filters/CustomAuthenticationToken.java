package com.example.marketplacemain.marketplacemain.autenticacion.security.filters;

import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.example.marketplacemain.marketplacemain.autenticacion.DTO.RolDTO;

public class CustomAuthenticationToken extends JwtAuthenticationFilter  {

    private RolDTO rolDTO;


    public CustomAuthenticationToken(AuthenticationManager authenticationManager, RolDTO rolDTO) {

        super(authenticationManager);
        this.rolDTO = rolDTO;
    }

    
}
