package com.example.marketplacemain.marketplacemain.websockets.valitations;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.marketplacemain.marketplacemain.websockets.entities.NotificationEmployee;

public class ValidationNotificationEmployee {
 public static void existNofiticationClient(NotificationEmployee categoria){

        if (categoria == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe la notificacion.");
            
        }
    }
}
