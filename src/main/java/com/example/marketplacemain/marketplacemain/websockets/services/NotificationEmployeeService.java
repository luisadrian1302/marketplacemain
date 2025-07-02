package com.example.marketplacemain.marketplacemain.websockets.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.marketplacemain.marketplacemain.websockets.entities.NotificationClient;
import com.example.marketplacemain.marketplacemain.websockets.entities.NotificationEmployee;
import com.example.marketplacemain.marketplacemain.websockets.repository.NotificationEmployeeRepository;

@Service
public class NotificationEmployeeService {

     @Autowired
    private NotificationEmployeeRepository notificationClientRepository;

    public NotificationEmployee save(NotificationEmployee notification){

        return notificationClientRepository.save(notification);

    }

    public List<NotificationEmployee> getAllByClient(Long id){

        return notificationClientRepository.getByIdClient(id);
        // return null;
    }

    public Long listNotificationClients(Long id){

        return notificationClientRepository.countNotificationNotView_Id(id);
        // return null;
    }


    
    public NotificationEmployee getById(Long id){

        Optional<NotificationEmployee> notification = notificationClientRepository.findById(id);
        if (notification.isPresent()) {
            return notification.get();
        }
        return null;
    }

    public void UpdateAll(Long id){

         notificationClientRepository.updateByUser(id);
    }


    
}
