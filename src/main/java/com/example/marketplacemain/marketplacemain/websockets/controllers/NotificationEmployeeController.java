package com.example.marketplacemain.marketplacemain.websockets.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.marketplacemain.marketplacemain.autenticacion.entities.User;
import com.example.marketplacemain.marketplacemain.autenticacion.security.SetAuthUser;
import com.example.marketplacemain.marketplacemain.autenticacion.services.JwtService;
import com.example.marketplacemain.marketplacemain.autenticacion.services.UserService;
import com.example.marketplacemain.marketplacemain.websockets.entities.NotificationClient;
import com.example.marketplacemain.marketplacemain.websockets.entities.NotificationEmployee;
import com.example.marketplacemain.marketplacemain.websockets.services.NotificacionesClientService;
import com.example.marketplacemain.marketplacemain.websockets.services.NotificationEmployeeService;
import com.example.marketplacemain.marketplacemain.websockets.valitations.ValidationNotificationEmployee;
import com.example.marketplacemain.marketplacemain.websockets.valitations.ValidationNotificationService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/notificationsEmployee")
public class NotificationEmployeeController {
    @Autowired
    private NotificationEmployeeService notificacionesClientService;

    @Autowired
    private UserService usuarioservice;
 
    @Autowired
    private JwtService jwtService;


    @GetMapping("getcountByID")
    public ResponseEntity<?> getCountByID( HttpServletRequest request) {

        String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
        User user = usuarioservice.getUserByEmail(email);
        return ResponseEntity.ok().body(notificacionesClientService.listNotificationClients(user.getEmployee().getId()));
    }

    @GetMapping("getById")
    public ResponseEntity<?> getById( HttpServletRequest request) {

        String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
        User user = usuarioservice.getUserByEmail(email);
        return ResponseEntity.ok().body(notificacionesClientService.getAllByClient(user.getClient().getId()));
    }



    @GetMapping("updateLeido/{id}")
    public ResponseEntity<?> updateLeido( HttpServletRequest request, @PathVariable Long id) {

        String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
        User user = usuarioservice.getUserByEmail(email);

        NotificationEmployee notificationClient = notificacionesClientService.getById(id);
        ValidationNotificationEmployee.existNofiticationClient(notificationClient);

        notificationClient.setLeido(true);
        notificacionesClientService.save(notificationClient);

        return ResponseEntity.ok().body("ok");
    }

    @GetMapping("eliminarNotificacion/{id}")
    public ResponseEntity<?> eliminarNotificacion( HttpServletRequest request, @PathVariable Long id) {

        String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
        User user = usuarioservice.getUserByEmail(email);

        NotificationEmployee notificationClient = notificacionesClientService.getById(id);
        ValidationNotificationEmployee.existNofiticationClient(notificationClient);

        notificationClient.setStatus((byte) 0);
        notificacionesClientService.save(notificationClient);

        return ResponseEntity.ok().body("ok");
    }

    @GetMapping("maracarTodasLeidas")
    public ResponseEntity<?> maracarTodasLeidas( HttpServletRequest request) {

        String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
        User user = usuarioservice.getUserByEmail(email);
   
 
        notificacionesClientService.UpdateAll(user.getClient().getId());

        return ResponseEntity.ok().body("ok");
    }
}
