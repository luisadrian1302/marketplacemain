package com.example.marketplacemain.marketplacemain.products.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.marketplacemain.marketplacemain.products.entitites.Categoria;
import com.example.marketplacemain.marketplacemain.products.repositories.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired 
    private CategoriaRepository repository;

    public List<Categoria> getAllCategorias(){
        return (List<Categoria>) repository.findAll();
    }



}
