package com.example.marketplacemain.marketplacemain.products.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.marketplacemain.marketplacemain.products.services.ResenaService;

@RestController
@RequestMapping("api/resenas")
public class ResenasController {

    @Autowired
    private ResenaService resenaService;

    @GetMapping("/public/getById/{id}")
    public ResponseEntity<?> getByIdPublic( @PathVariable Long id ) {   
            return ResponseEntity.ok().body(resenaService.getResenasByIdProduct(id));
    }

    @GetMapping("/public/getAvgById/{id}")
    public ResponseEntity<?> getAvgById( @PathVariable Long id ) {   
            return ResponseEntity.ok().body(resenaService.getAvgResenasByIdProduct(id));
    }


}
