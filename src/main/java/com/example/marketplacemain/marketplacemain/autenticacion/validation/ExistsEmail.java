package com.example.marketplacemain.marketplacemain.autenticacion.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = ExistsEmailValidation.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)

public @interface ExistsEmail {

    String message() default "ya existe en la base de datos!, escoja otro correo electronico!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
