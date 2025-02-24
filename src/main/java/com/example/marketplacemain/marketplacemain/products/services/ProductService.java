package com.example.marketplacemain.marketplacemain.products.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.marketplacemain.marketplacemain.products.entitites.Producto;
import com.example.marketplacemain.marketplacemain.products.repositories.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductoRepository repository;


    @Transactional
    public Producto save(Producto producto)   {

        return repository.save(producto);
    }


    @Transactional
    public Producto getById(Long id)   {
        Optional<Producto> producto = repository.findById(id);

        if (producto.isPresent()) {
            return producto.get();
        }
        return null;

    }

    
    @Transactional
    public Long getCounValue(Long id)   {
       List<Object[]> producto = repository.findCountValue(id);

       Long count =  (Long) producto.get(0)[0];
        return count;

    }


}
