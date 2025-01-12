package com.example.marketplacemain.marketplacemain.autenticacion.services;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.marketplacemain.marketplacemain.autenticacion.DTO.RolDTO;
import com.example.marketplacemain.marketplacemain.autenticacion.entities.User;
import com.example.marketplacemain.marketplacemain.autenticacion.repositories.UserRepositories;
import com.example.marketplacemain.marketplacemain.autenticacion.security.filters.CustomAuthenticationToken;

@Service
public class JpaUserDetailsService implements UserDetailsService{

     @Autowired
    private UserRepositories repository;

   

    

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);

        List<User> userOptional = repository.findByEmail(username);

        if (userOptional.size() == 0) {
            throw new UsernameNotFoundException(String.format("Username %s no existe en el sistema!", username));
        }

        // validaciones del estatus de usuario

        User user = userOptional.get(0);

         // Crear una lista de roles
        List<String> roles = new ArrayList<>();

        if (!user.isVerified() || !user.isStatus()) {
            throw new UsernameNotFoundException(String.format("Username %s no se a verficado en el sistema, intente verificarse con el codigo que le llego a su correo!", username));
            
        }

         


         roles.add("ROLE_USER"); // Es buena práctica usar el prefijo "ROLE_"

        //  System.out.println(user.getVendedor());

         if (user.getVendedor() != null) {
            roles.add("ROLE_VENDEDOR"); // Es buena práctica usar el prefijo "ROLE_"
         }

         
 
         // Convertir los roles a GrantedAuthority
         List<GrantedAuthority> authorities = roles.stream()
                 .map(SimpleGrantedAuthority::new) // Uso de referencia de método
                 .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getEmail(), 
        user.getPassword(), 
        true,
        true,
        true,
        true,
                authorities);
    }
}
