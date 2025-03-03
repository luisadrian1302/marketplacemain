package com.example.marketplacemain.marketplacemain.products.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.marketplacemain.marketplacemain.products.DTO.GetSubProductosDTO;
import com.example.marketplacemain.marketplacemain.products.entitites.Atributo;
import com.example.marketplacemain.marketplacemain.products.entitites.SubProducto;
import com.example.marketplacemain.marketplacemain.products.repositories.SubProductoRepository;

import jakarta.transaction.Transactional;

@Service
public class SubproductoService {

    @Autowired
    private SubProductoRepository subProductoRepository;

    @Transactional
    public SubProducto save(SubProducto subProducto){
        return subProductoRepository.save(subProducto);
    }

    public List<SubProducto>  getAllBySubcategoria(Long id){
        return subProductoRepository.findByIdProducto(id);
    }


    public List<SubProducto>  getAllActiveByUser(Long id){
        return subProductoRepository.findByIdUserActive(id);
    }

    public List<SubProducto>  getAllByIDandOUTdiscount(Long id){
        return subProductoRepository.findByUserSinDescuento(id);
    }

    public List<SubProducto>  getAllByIdAndDescount(Long id, Long iddescuento){
        return subProductoRepository.findByUserCOnDescuentoEspecifico(id, iddescuento);
    }

    public List<SubProducto>  getAllByUser(Long id){
        return subProductoRepository.findByIdUserActive(id);
    }

    @Transactional
    public void verificarSubProductos(){
         subProductoRepository.verificarEstadoDescuento();
    }

    @Transactional
    public void verificarSubProductos(Long id){
         subProductoRepository.verificarEstadoDescuento(id);
    }


    


    public SubProducto getById(Long id){
        Optional<SubProducto> subproducto = subProductoRepository.findById(id);
        
        if (subproducto.isPresent()) { 
            return subproducto.get();
        }
        return null;
    }

    @Transactional
    public void updateByID(Long id, Integer status){
        subProductoRepository.updateStatusSubproducto(id,status);
    }



    public List<GetSubProductosDTO> getByUser(Long id){
        List<Object[]> subproducto = subProductoRepository.findByIdUser(id);

        List<GetSubProductosDTO> subProductosDTOs = new ArrayList<>();

        for (int i = 0; i < subproducto.size(); i++) {
        
            GetSubProductosDTO getSubProductoDTO = new GetSubProductosDTO((Long) subproducto.get(i)[0], 
            (String)  subproducto.get(i)[1], (String) subproducto.get(i)[2], (Double) subproducto.get(i)[3], (Integer)  subproducto.get(i)[4], 
            (Integer) subproducto.get(i)[5], (Double) subproducto.get(i)[6],(Double)   subproducto.get(i)[7], (Integer)  subproducto.get(i)[8]);

            subProductosDTOs.add(getSubProductoDTO);
        }
        
       
        return subProductosDTOs;
    }




    

}
