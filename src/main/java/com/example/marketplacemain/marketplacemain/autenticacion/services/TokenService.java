package com.example.marketplacemain.marketplacemain.autenticacion.services;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class TokenService {
    // Clave de encriptación - DEBE ser de 16, 24 o 32 bytes para AES-128, AES-192 o AES-256
    private static final String SECRET_KEY = "MySecretKey12345"; // Cambia esto por una clave segura
    private static final String ALGORITHM = "AES";

    public static String generateToken(String email, String name) {
        try {
            // Crear el string con la información a encriptar
            String data = email + "::" + name + "::" + System.currentTimeMillis();
            
            // Crear la clave secreta
            SecretKeySpec secretKey = new SecretKeySpec(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8), 
                ALGORITHM
            );
            
            // Configurar el cipher para encriptación
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            
            // Encriptar los datos
            byte[] encryptedData = cipher.doFinal(data.getBytes());
            
            // Convertir a Base64 para usar en URL
            return Base64.getUrlEncoder().encodeToString(encryptedData);
            
        } catch (Exception e) {
            throw new RuntimeException("Error generating token", e);
        }
    }
    
    public static TokenData decryptToken(String token) {
        try {
            // Decodificar el token de Base64
            byte[] encryptedData = Base64.getUrlDecoder().decode(token);
            
            // Crear la clave secreta
            SecretKeySpec secretKey = new SecretKeySpec(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8), 
                ALGORITHM
            );
            
            // Configurar el cipher para desencriptación
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            
            // Desencriptar los datos
            byte[] decryptedData = cipher.doFinal(encryptedData);
            String decryptedString = new String(decryptedData);
            
            // Separar los datos
            String[] parts = decryptedString.split("::");
            
            // Verificar que el token no haya expirado (ejemplo: 24 horas)
            long timestamp = Long.parseLong(parts[2]);
            if (System.currentTimeMillis() - timestamp > 24 * 60 * 60 * 1000) {
                throw new RuntimeException("Token expired");
            }
            
            return new TokenData(parts[0], parts[1]);
            
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting token", e);
        }
    }
    
    // Clase para almacenar los datos del token
    public static class TokenData {
        private final String email;
        private final String name;
        
        public TokenData(String email, String name) {
            this.email = email;
            this.name = name;
        }
        
        public String getEmail() { return email; }
        public String getName() { return name; }
    }
}