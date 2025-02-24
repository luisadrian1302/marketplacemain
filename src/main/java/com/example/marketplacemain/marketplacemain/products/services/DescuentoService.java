package com.example.marketplacemain.marketplacemain.products.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.marketplacemain.marketplacemain.products.entitites.Atributo;
import com.example.marketplacemain.marketplacemain.products.entitites.Descuento;
import com.example.marketplacemain.marketplacemain.products.repositories.DescuentoRepository;

import jakarta.transaction.Transactional;


@Service
public class DescuentoService {

    @Autowired
    private DescuentoRepository descuentoRepository;



    public List<Descuento> getAllDescuentos(){
        return (List<Descuento>) descuentoRepository.findAll();
    }

    public List<Descuento>  getAllByIDUser(Long id){
        return descuentoRepository.findByIdVendedor(id);
    }

    public List<Descuento>  getAllByIDUserAll(Long id){
        return descuentoRepository.findByIdVendedorAll(id);
    }
     public Descuento  getDescuentoById(Long id){
        Optional<Descuento> descuento =  descuentoRepository.findById(id);

        if (descuento.isPresent()) {
            return descuento.get();
        }
        return null;
    }
    
    @Transactional
    public Descuento saveDescuento(Descuento descuento){
        return descuentoRepository.save(descuento);
    }
}
