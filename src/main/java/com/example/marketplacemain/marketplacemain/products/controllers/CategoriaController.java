package com.example.marketplacemain.marketplacemain.products.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.marketplacemain.marketplacemain.products.entitites.Categoria;
import com.example.marketplacemain.marketplacemain.products.services.CategoriaService;

@RestController
@RequestMapping("api/categorias")
public class CategoriaController {

     @Autowired
    private CategoriaService service;

    @GetMapping("/getAllcategorias")
    public ResponseEntity<?> getAllCategorias() {

        List<Categoria> categorias = service.getAllCategorias();

        return ResponseEntity.status(HttpStatus.CREATED).body(categorias);

    }



}
