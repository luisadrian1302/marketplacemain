package com.example.marketplacemain.marketplacemain.products.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.marketplacemain.marketplacemain.products.entitites.CaractertisticaTabla;
import com.example.marketplacemain.marketplacemain.products.repositories.CaracteristicaTableRepository;

import jakarta.transaction.Transactional;

@Service
public class CaracteristicasTableService {

    @Autowired
    private CaracteristicaTableRepository repository;

     @Transactional
    public CaractertisticaTabla saveDescuento(CaractertisticaTabla descuento){
        return repository.save(descuento);
    }
}
