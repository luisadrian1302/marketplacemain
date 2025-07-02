package com.example.marketplacemain.marketplacemain.products.controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.marketplacemain.marketplacemain.autenticacion.entities.User;
import com.example.marketplacemain.marketplacemain.autenticacion.security.SetAuthUser;
import com.example.marketplacemain.marketplacemain.autenticacion.services.JwtService;
import com.example.marketplacemain.marketplacemain.autenticacion.services.UserService;
import com.example.marketplacemain.marketplacemain.products.DTO.CreateCategoriaDTO;
import com.example.marketplacemain.marketplacemain.products.DTO.DescuentoDTO;
import com.example.marketplacemain.marketplacemain.products.entitites.Categoria;
import com.example.marketplacemain.marketplacemain.products.entitites.Descuento;
import com.example.marketplacemain.marketplacemain.products.services.CategoriaService;
import com.example.marketplacemain.marketplacemain.products.services.validations.ValidationCategory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/categorias")
public class CategoriaController {

     @Autowired
    private CategoriaService service;

     @Autowired
    private UserService usuarioservice;
    @Autowired
    private JwtService jwtService;

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }


    @GetMapping("/getAllcategorias")
    public ResponseEntity<?> getAllCategorias() {

        List<Categoria> categorias = service.getAllCategoriasPublic();

        return ResponseEntity.status(HttpStatus.CREATED).body(categorias);

        
    }

    @GetMapping("/getAllcategoriasByEmployee")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")

    public ResponseEntity<?> getAllCategoriasByEmployee() {

        List<Categoria> categorias = service.getAllCategorias();

        return ResponseEntity.status(HttpStatus.CREATED).body(categorias);

        
    }

    @GetMapping("/getcategoriaByID/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> getCategoriaByID(  @PathVariable Long id) {
        Categoria categoria = service.getGatCategoriaById(id);
        ValidationCategory.existCategoria(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }

    
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")

    public ResponseEntity<?> createCategory(   HttpServletRequest request, @Valid  @RequestBody CreateCategoriaDTO entity,  BindingResult result) {

        //TODO: process POST request
        if (result.hasFieldErrors()) {
            return validation(result);
        }

        ValidationCategory.validateExistName(entity, service);
        Categoria categoria = new Categoria();

        categoria.setNombre(entity.getNombre());
       
        String route = entity.getNombre();
        String resetRoute = route.replace(" ", "_");
        categoria.setRuta(resetRoute);
        
        categoria.setStatus(true);
        categoria.setFecha_publicacion(new Date());

        Categoria nuevaCategoria = service.save(categoria);
        
        return ResponseEntity.ok().body(nuevaCategoria);
    }
    


    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")

    public ResponseEntity<?> updateDescuento(   HttpServletRequest request, @Valid  @RequestBody CreateCategoriaDTO entity,  BindingResult result,  @PathVariable Long id) {


        
        //TODO: process POST request
        if (result.hasFieldErrors()) {
            return validation(result);
        }

        Categoria categoria = service.getGatCategoriaById(id);
        ValidationCategory.existCategoria(categoria);

        List<Categoria> categorias = service.getCategoriaByNameAndID(id, entity.getNombre());
        ValidationCategory.existCategorias(categorias);

        categoria.setNombre(entity.getNombre());
       
        String route = entity.getNombre();
        String resetRoute = route.replace(" ", "_");
        categoria.setRuta(resetRoute);
    
        categoria.setStatus(true);
        categoria.setFecha_publicacion(new Date());

        Categoria nuevaCategoria = service.save(categoria);
        
        return ResponseEntity.ok().body(nuevaCategoria);
    }
    



    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")

    public ResponseEntity<?> deleteCategoria(   HttpServletRequest request,  @PathVariable Long id) {
        
        Categoria categoria = service.getGatCategoriaById(id);
        ValidationCategory.existCategoria(categoria);
        categoria.setStatus(false);

        service.save(categoria);
        //TODO: process POST request
      
        return ResponseEntity.ok().body("ok");
    }

    
    @GetMapping("/active/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")

    public ResponseEntity<?> activarCategoria(   HttpServletRequest request,  @PathVariable Long id) {
        
        Categoria categoria = service.getGatCategoriaById(id);
        ValidationCategory.existCategoria(categoria);
        categoria.setStatus(true);

        service.save(categoria);
        //TODO: process POST request
      
        return ResponseEntity.ok().body("ok");
    }
    
    


}
