package com.example.marketplacemain.marketplacemain.autenticacion.security;

import com.example.marketplacemain.marketplacemain.autenticacion.services.JwtService;

import jakarta.servlet.http.HttpServletRequest;

public class SetAuthUser {

    static public String getUsernameDeserialize(HttpServletRequest request, JwtService jwtService) {

        String authorizationHeader = request.getHeader("Authorization");
        String token = "your.jwt.token";
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7); // Quita "Bearer " del encabezado
        }
        String username = jwtService.extractUsername(token);

        return username;
    }

}
