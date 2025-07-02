package com.example.marketplacemain.marketplacemain.websockets.config;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    
    @Autowired
    private JwtWebSocketInterceptor jwtWebSocketInterceptor;
   
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {


        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        System.out.println("registandoooooo--------------------------");
        // Configura con y sin SockJS
        registry.addEndpoint("/ws")
               .setAllowedOrigins("*"); // Para pruebas, usar "*" (en producción usa orígenes específicos)
        
        // También añade con SockJS como alternativa
        registry.addEndpoint("/ws")
               .setAllowedOrigins("*")
               .withSockJS();
    }
   

    
}
