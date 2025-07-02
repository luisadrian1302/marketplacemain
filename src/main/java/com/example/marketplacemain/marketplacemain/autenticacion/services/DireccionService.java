package com.example.marketplacemain.marketplacemain.autenticacion.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.marketplacemain.marketplacemain.autenticacion.entities.Direcciones;
import com.example.marketplacemain.marketplacemain.autenticacion.repositories.DireccionRepository;
import com.example.marketplacemain.marketplacemain.products.entitites.SubProducto;

import jakarta.transaction.Transactional;

@Service

public class DireccionService {
     @Autowired
    private DireccionRepository repository;

    @Transactional
    public Direcciones save(Direcciones direcciones)   {

        return repository.save(direcciones);
    }

  

    
    
    public List<Direcciones> getAll()   {
        return (List<Direcciones>) repository.findAll();
    }

    public List<Direcciones>  getAllByUser(Long id){
        return repository.findByIdUser(id);
    }

    public Direcciones  getAllByID(Long id){
        Optional<Direcciones> direcciones = repository.findById(id);

        if (direcciones.isPresent()) {
            return direcciones.get();
        }

        return null;
    }

    public List<Direcciones>  getAllByIDAndDireccion(Long idUser, Long id){
        List<Direcciones> direcciones = repository.findByIdAndClient(idUser, id);

        return direcciones;
    }

}
