package com.example.marketplacemain.marketplacemain.products.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.marketplacemain.marketplacemain.products.DTO.ValorPropiedadDTO;
import com.example.marketplacemain.marketplacemain.products.DTO.ValorPropiedadPublicDTO;
import com.example.marketplacemain.marketplacemain.products.entitites.Caracteristicas;
import com.example.marketplacemain.marketplacemain.products.repositories.CaracteristicaRepository;

import jakarta.transaction.Transactional;

@Service
public class CaracteristicaService {

    @Autowired
    private CaracteristicaRepository repository;

     @Transactional
    public Caracteristicas save(Caracteristicas descuento){
        return repository.save(descuento);
    }

    public List<ValorPropiedadDTO> findValueValor(Long id){
        return repository.findAtributoValor(id);
    }

    public List<ValorPropiedadPublicDTO> findAtributoValorPublic(Long id){
        return repository.findAtributoValorPublic(id);
    }

    

    public Long getCountByAtributo(Long id){
        return repository.countByAtributo_Id(id);
    }

    public List<ValorPropiedadDTO> findValueValorByIDsubcategoria(Long id, Long idSubproducto){
        return repository.findAtributoValorByIDSubproducto(id, idSubproducto);
    }

    public List<ValorPropiedadDTO> findValuesBySubproduct(Long id, Long idSubproducto){
        return repository.findValuesBySubproduct(id, idSubproducto);
    }


    public Long getIDSubproductTwoChanel(Long idProducto, String nombre1, String valor1, String tipo1, String nombre2, String valor2,  String tipo2){

        List<Object[]> idSubproduct = repository.getIDSubproductTwoChanel( idProducto,  nombre1,  valor1,  tipo1,  nombre2,  valor2, tipo2);
        if (idSubproduct.size() > 0) {
            Long count =  (Long) idSubproduct.get(0)[0];
            return count;
        }
        return (long) 0;
    }

    public Long getIDSubproductThreeChanel(Long idProducto, String nombre1, String valor1, String tipo1,
     String nombre2, String valor2,  String tipo2, String nombre3, String valor3,  String tipo3){

        List<Object[]> idSubproduct = repository.getIDSubproductThreeChanel( idProducto,  nombre1,  valor1,  tipo1,  nombre2,  valor2, tipo2,
        nombre3,  valor3, tipo3);
        if (idSubproduct.size() > 0) {
            Long count =  (Long) idSubproduct.get(0)[0];
            return count;
        }
        return (long) 0;
    }

    public Long getIDSubproductOneChanel(Long idProducto, String nombre1, String valor1, String tipo1){

        List<Object[]> idSubproduct = repository.getIDSubproductoneChanel( idProducto,  nombre1,  valor1,  tipo1);
        if (idSubproduct.size() > 0) {
            Long count =  (Long) idSubproduct.get(0)[0];
            return count;
        }
        return (long) 0;
    }


}
