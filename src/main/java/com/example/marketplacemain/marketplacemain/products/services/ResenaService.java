package com.example.marketplacemain.marketplacemain.products.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.marketplacemain.marketplacemain.products.DTO.GetResenaInfoDTO;
import com.example.marketplacemain.marketplacemain.products.entitites.Reseña;
import com.example.marketplacemain.marketplacemain.products.repositories.ResenasRepository;

@Service
public class ResenaService {

    @Autowired
    private ResenasRepository resenasRepository;

    public List<Reseña> getResenasByIdProduct(Long id){
        return resenasRepository.getReseñaByIdProduct(id);
    }

    public GetResenaInfoDTO getAvgResenasByIdProduct(Long id){
        List<Object[]> result = resenasRepository.getAvgAndCountResenasByIdProduct(id);
        System.out.println(result.get(0)[0]);
        GetResenaInfoDTO getResenaInfoDTO = new GetResenaInfoDTO((Double) result.get(0)[0], (Long) result.get(0)[1]);
        return getResenaInfoDTO;
    }

}
