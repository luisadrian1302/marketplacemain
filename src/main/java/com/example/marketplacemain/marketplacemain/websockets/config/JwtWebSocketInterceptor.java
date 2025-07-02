package com.example.marketplacemain.marketplacemain.websockets.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.marketplacemain.marketplacemain.autenticacion.services.JwtService;

@Component
public class JwtWebSocketInterceptor implements ChannelInterceptor {
    
   @Autowired
    private JwtService jwtService;
    
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {


        try {
            StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        
            System.out.println("desde predenser");
            if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                String token = accessor.getFirstNativeHeader("Authorization");
                
                if (token != null && token.startsWith("Bearer ")) {
                    token = token.substring(7);
    
                    String username = jwtService.extractUsername(token);
                    
                  
                    Authentication auth = jwtService.getAuthentication(token, username);
                    accessor.setUser(auth);
                
                }
            }
            
            return message;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());

            // Es importante NO lanzar la excepción aquí, solo registrarla
            return message;
        }
       
    }
}