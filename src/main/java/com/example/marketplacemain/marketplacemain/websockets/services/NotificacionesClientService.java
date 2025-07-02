package com.example.marketplacemain.marketplacemain.websockets.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.marketplacemain.marketplacemain.websockets.entities.NotificationClient;
import com.example.marketplacemain.marketplacemain.websockets.repository.NotificationClientRepository;

@Service
public class NotificacionesClientService {

    @Autowired
    private NotificationClientRepository notificationClientRepository;

    public NotificationClient save(NotificationClient notification){

        return notificationClientRepository.save(notification);

    }

    public List<NotificationClient> getAllByClient(Long id){

        return notificationClientRepository.getByIdClient(id);
        // return null;
    }

    public NotificationClient getById(Long id){

        Optional<NotificationClient> notification = notificationClientRepository.findById(id);
        if (notification.isPresent()) {
            return notification.get();
        }
        return null;
    }

    public void UpdateAll(Long id){

         notificationClientRepository.updateByUser(id);
    }


    public Long listNotificationClients(Long id){

        return notificationClientRepository.countNotificationNotView_Id(id);
        // return null;
    }

}
