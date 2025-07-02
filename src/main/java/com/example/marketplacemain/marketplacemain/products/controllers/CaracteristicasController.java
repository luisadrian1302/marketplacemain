package com.example.marketplacemain.marketplacemain.products.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.marketplacemain.marketplacemain.products.services.CaracteristicaService;

@RestController
@RequestMapping("api/caracteristicas")
public class CaracteristicasController {
    @Autowired
    private CaracteristicaService caracteristicaService;

    @GetMapping("/public/getProperties/{id}")
    public ResponseEntity<?> getProperties( @PathVariable Long id ) {   
            return ResponseEntity.ok().body(caracteristicaService.findAtributoValorPublic(id));
    }

}
