package com.example.marketplacemain.marketplacemain.autenticacion.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.marketplacemain.marketplacemain.autenticacion.entities.User;
import com.example.marketplacemain.marketplacemain.autenticacion.services.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExistsEmailValidation implements ConstraintValidator<ExistsEmail, String> {

    @Autowired
    private UserService service;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        // TODO Auto-generated method stub
        if (service == null) {
            return true;
        } 


        System.out.println(service.existsEmail(email) + " " + email);
        return !service.existsEmail(email);
    }
 
}
