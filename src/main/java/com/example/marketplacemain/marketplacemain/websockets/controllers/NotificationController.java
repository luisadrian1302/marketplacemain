package com.example.marketplacemain.marketplacemain.websockets.controllers;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.example.marketplacemain.marketplacemain.autenticacion.entities.Client;
import com.example.marketplacemain.marketplacemain.autenticacion.entities.Employee;
import com.example.marketplacemain.marketplacemain.products.entitites.SubProducto;
import com.example.marketplacemain.marketplacemain.websockets.DTO.NotificacionDocumenttDTO;
import com.example.marketplacemain.marketplacemain.websockets.entities.NotificationClient;
import com.example.marketplacemain.marketplacemain.websockets.entities.NotificationEmployee;
import com.example.marketplacemain.marketplacemain.websockets.services.NotificacionesClientService;
import com.example.marketplacemain.marketplacemain.websockets.services.NotificationEmployeeService;

@Controller
public class NotificationController {
  // tipos de notificaciones
        // {notif.tipo === 'Aviso' && 'Info'}
        // {notif.tipo === 'Correcto' && 'Éxito'}
        // {notif.tipo === 'Error' && 'Error'}
        // {notif.tipo === 'Importante' && 'Advertencia'}

     // Almacena las sesiones por ID de usuario
    private Map<String, Set<String>> userSessions = new ConcurrentHashMap<>();

    @Autowired 
    private NotificacionesClientService clientService;

    @Autowired 
    private NotificationEmployeeService employeeService;
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/notify") // Ruta donde el frontend enviará mensajes
    @SendTo("/topic/updates") // Los mensajes se envían a esta ruta
    public String sendAprovee(String message) {
        System.out.println("Mensaje recibido: " + message);
        return message; // Envía el mensaje a los clientes suscritos
    }


    @MessageMapping("/rechazado") // Ruta donde el frontend enviará mensajes
    @SendTo("/topic/rechazado") // Los mensajes se envían a esta ruta
    public String sendDelete(String message) {
        System.out.println("Mensaje recibido: " + message);
        return message; // Envía el mensaje a los clientes suscritos
    }


     // Cliente envía este mensaje al conectarse para identificarse
    @MessageMapping("/register")
    public void registerUser(StompHeaderAccessor headerAccessor, String userId) {
        String sessionId = headerAccessor.getSessionId();
        
        // Guarda la asociación de userId -> sessionId
        userSessions.computeIfAbsent(userId, k -> new HashSet<>()).add(sessionId);
        
        System.out.println("Usuario registrado: " + userId + " con sesión: " + sessionId);
    }
    
    // Para enviar un mensaje a un usuario específico
    public void sendMessageToUserDelete(String userId, Object message, SubProducto subProducto) {

      
        Client cliente = subProducto.getProducto().getVendedor().getUsuario().getClient();


         // Obtener la fecha actual con la zona horaria de la Ciudad de México
        LocalDate hoy = LocalDate.now(ZoneId.of("America/Mexico_City"));
        NotificationClient notificationClient = new NotificationClient();
        notificationClient.setDescripcion("Su producto " + subProducto.getProducto().getTitular() +  " fue rechazado debido a que no cumple con las reglas de la comunidad" +
        ", asegurese de que sus datos sean coherentes en su producto");
        notificationClient.setLeido(false);
        notificationClient.setUser(cliente);
        notificationClient.setTitulo("producto rechazado");
        notificationClient.setTipo("Error");
        notificationClient.setUrl_direccion("/subproducts/getAll");
        notificationClient.setFechaPublicacion(hoy);
        notificationClient.setStatus((byte) 1);
        notificationClient.setUrlImagen(subProducto.getProducto().getImagePortada());
        clientService.save(notificationClient);


        messagingTemplate.convertAndSend("/queue/specific-user/" + userId, message);
    }


    public void sendMessageToUserAprovee(String userId, Object message, SubProducto subProducto) {

      
        Client cliente = subProducto.getProducto().getVendedor().getUsuario().getClient();


         // Obtener la fecha actual con la zona horaria de la Ciudad de México
        LocalDate hoy = LocalDate.now(ZoneId.of("America/Mexico_City"));
        NotificationClient notificationClient = new NotificationClient();
        notificationClient.setDescripcion("Su producto " + subProducto.getProducto().getTitular() +  " fue aprobado en la plataforma" );
        notificationClient.setLeido(false);
        notificationClient.setUser(cliente);
        notificationClient.setTitulo("producto Aprobado");
        notificationClient.setTipo("Correcto");
        notificationClient.setUrl_direccion("/subproducts/getAll");
        notificationClient.setFechaPublicacion(hoy);
        notificationClient.setStatus((byte) 1);
        notificationClient.setUrlImagen(subProducto.getProducto().getImagePortada());
        clientService.save(notificationClient);


        messagingTemplate.convertAndSend("/queue/specific-user/" + userId, message);
    }
    

    // Para empleados
    public void sendMessageEmployeeDocument(String userId, Object message, NotificacionDocumenttDTO notifier) {

        // Obtener la fecha actual con la zona horaria de la Ciudad de México
        // LocalDate hoy = LocalDate.now(ZoneId.of("America/Mexico_City"));

        NotificationEmployee notificationClient = new NotificationEmployee();
        notificationClient.setDescripcion(notifier.getDescripcion());
        notificationClient.setLeido(false);
        notificationClient.setUser(notifier.getEmpleado());
        notificationClient.setTitulo(notifier.getTitulo());
        notificationClient.setTipo(notifier.getTipoStatus());
        notificationClient.setUrl_direccion(notifier.getUrl());
        notificationClient.setFechaPublicacion(notifier.getFechaInicio());
        notificationClient.setStatus(notifier.getStatus());
        notificationClient.setUrlImagen(notifier.getUrlImage());
        employeeService.save(notificationClient);

        messagingTemplate.convertAndSend("/queue/specific-user-employee/" + userId, message);
    }
    // Para enviar un mensaje a todos los usuarios
    public void sendMessageToAll(Object message) {
        messagingTemplate.convertAndSend("/topic/all", message);
    }
}
