package com.example.marketplacemain.marketplacemain.websockets.valitations;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.marketplacemain.marketplacemain.products.entitites.Categoria;
import com.example.marketplacemain.marketplacemain.websockets.entities.NotificationClient;

public class ValidationNotificationService {

    public static void existNofiticationClient(NotificationClient categoria){

        if (categoria == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe la notificacion.");
            
        }
    }

}
