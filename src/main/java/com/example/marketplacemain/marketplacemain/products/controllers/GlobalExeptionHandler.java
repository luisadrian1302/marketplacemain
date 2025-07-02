package com.example.marketplacemain.marketplacemain.products.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExeptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getReason());
        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }


    

}


// JsonMappingException
// JsonProcessingException

// catch (JsonMappingException e) {
//     // TODO Auto-generated catch block
//     e.printStackTrace();
// } catch (JsonProcessingException e) {
//     // TODO Auto-generated catch block
//     e.printStackTrace();
// } 