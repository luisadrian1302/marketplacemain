package com.example.marketplacemain.marketplacemain.autenticacion.services;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.marketplacemain.marketplacemain.autenticacion.DTO.InformacionGeneralDTO;
import com.example.marketplacemain.marketplacemain.autenticacion.DTO.UserImageDTO;
import com.example.marketplacemain.marketplacemain.autenticacion.DTO.VerifyDTO;
import com.example.marketplacemain.marketplacemain.autenticacion.entities.Client;
import com.example.marketplacemain.marketplacemain.autenticacion.entities.User;
import com.example.marketplacemain.marketplacemain.autenticacion.entities.Vendedor;
import com.example.marketplacemain.marketplacemain.autenticacion.repositories.UserRepositories;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;


@Service
public class UserService {


    @Autowired
    private  UserRepositories repository;

    @Autowired
    private EmailService emailService;
     

 

    public Boolean existsEmail(String email){

        List<User> users = repository.findByEmail(email);

        if (users.size() > 0 ) {
            return true;
        }
        return false;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

      
    @Transactional
    public User save(User user)   {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // generamos un codigo para verificar la cuenta
        SecureRandom secureRandom = new SecureRandom();
        Integer codigo = 100000 + secureRandom.nextInt(900000);


        Client cliente = new Client();

        cliente.setBanned(false);
        cliente.setStatus(false);

      

        System.out.println(user.getEmail() + " " + codigo.toString());
        
        try {
            emailService.enviarCorreo(user.getEmail(), "confirmacion de correo", codigo.toString() , "http://localhost:3000/confirmar");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        user.setCodigoVerificacion(codigo); 
        User usrNew = repository.save(user);
        usrNew.setClient(cliente);
        return repository.save(usrNew);
    }


    @Transactional
    public User save2(User user)   {

        return repository.save(user);
    }

    @Transactional

    public User getUserByEmail(String email){

        return repository.findByEmail(email).get(0);

    }

    @Transactional 
    public User verificarUser(VerifyDTO verifyDTO){

        List<User> users = repository.findByEmail(verifyDTO.getCorreo());

        if (users.size() == 0) {
            return null;
        }

        User user = users.get(0);

        if (user.getCodigoVerificacion().equals(verifyDTO.getCode())) {
            user.setVerified(true);
            user.setStatus(true);

            User userUpdate = repository.save(user);
            return userUpdate;
        }


        return null;
    }

    @Transactional 
    public User updateUserGeneral(InformacionGeneralDTO verifyDTO){

        List<User> users = repository.findByEmail(verifyDTO.getEmail());

        if (users.size() == 0) {
            return null;
        }

        User user = users.get(0);

        if (user.isStatus()) {
            user.setApellidos(verifyDTO.getFirstName());
            user.setNombre(verifyDTO.getLastName());
            user.setNumeroTelefonico(verifyDTO.getPhone());
            user.setCode_number(verifyDTO.getCountryCode());

            User userUpdate = repository.save(user);
            return userUpdate;
        }


        return null;
    }



    @Transactional 
    public User updateUserImage(UserImageDTO verifyDTO){

        List<User> users = repository.findByEmail(verifyDTO.getUsername());

        if (users.size() == 0) {
            return null;
        }

        User user = users.get(0);

        if (user.isStatus()) {
            user.setUrlImage(verifyDTO.getUrlImage());
            User userUpdate = repository.save(user);
            return userUpdate;
        }


        return null;
    }


     public User changePassword(String username, String oldPassword, String newPassword) {
        // Buscar al usuario por su nombre de usuario
        List<User> users = repository.findByEmail(username);
        if (users == null) {
            return null;   
        }
        User user = users.get(0);
        // Verificar la contraseña antigua
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return null;
        }

        // Actualizar con la nueva contraseña encriptada
        user.setPassword(passwordEncoder.encode(newPassword));
        repository.save(user);

        return user;
    }

}
   