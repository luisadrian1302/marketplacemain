package com.example.marketplacemain.marketplacemain.products.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.marketplacemain.marketplacemain.products.DTO.CreateCategoriaDTO;
import com.example.marketplacemain.marketplacemain.products.DTO.SubCategoriaDTO;
import com.example.marketplacemain.marketplacemain.products.entitites.Categoria;
import com.example.marketplacemain.marketplacemain.products.entitites.Subcategoria;
import com.example.marketplacemain.marketplacemain.products.services.CategoriaService;
import com.example.marketplacemain.marketplacemain.products.services.SubcategoriasService;
import com.example.marketplacemain.marketplacemain.products.services.validations.ValidationCategory;
import com.example.marketplacemain.marketplacemain.products.services.validations.ValidationSubcategoryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/subcategoria")
public class SubcategoriaController {

    @Autowired
    private SubcategoriasService subcategoriasService;

    
    @Autowired
    private CategoriaService categoriaService;


     private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }


    @GetMapping("getAll")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(  subcategoriasService.GetAllSubcategorias());
    }
    

 


    @GetMapping("getByIdCategoria/{id}")
    @PreAuthorize("hasRole('ROLE_VENDEDOR')")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        return ResponseEntity.ok(  subcategoriasService.getAllByCategoria(id));
    }


    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")

    public ResponseEntity<?> createSubCategory(   HttpServletRequest request, @Valid  @RequestBody SubCategoriaDTO entity,  BindingResult result) {

        //TODO: process POST request
        if (result.hasFieldErrors()) {
            return validation(result);
        }

        ValidationSubcategoryService.validateExistName(entity, subcategoriasService);
        Subcategoria subcategoria = new Subcategoria();

        subcategoria.setNombre(entity.getNombre());
       
        String route = entity.getNombre();
        String resetRoute = route.replace(" ", "_");
        subcategoria.setRuta(resetRoute);
        
        subcategoria.setStatus(true);
        subcategoria.setFecha_publicacion(new Date());

        Categoria categoria = categoriaService.getGatCategoriaById(entity.getIdCategoria());
        ValidationCategory.existCategoria(categoria);

        subcategoria.setCategoria(categoria);
        Subcategoria nuevasubcategoria = subcategoriasService.save(subcategoria);
        
        return ResponseEntity.ok().body(nuevasubcategoria);
    }

     @GetMapping("/getsubcategoriaByID/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> getCategoriaByID(  @PathVariable Long id) {
        Subcategoria subcategoria = subcategoriasService.getsubCategoria(id);
        ValidationSubcategoryService.existCategoria(subcategoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(subcategoria);
    }

    
    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")

    public ResponseEntity<?> updateSubCategory(   HttpServletRequest request, @Valid  @RequestBody SubCategoriaDTO entity,  BindingResult result, @PathVariable Long id) {

        //TODO: process POST request
        if (result.hasFieldErrors()) {
            return validation(result);
        }

        // ValidationSubcategoryService.validateExistName(entity, subcategoriasService);
        Subcategoria subcategoria = subcategoriasService.getsubCategoria(id);
        ValidationSubcategoryService.existCategoria(subcategoria);


        List<Subcategoria> subcaategorias = subcategoriasService.getSubCategoriaByNameAndID(id, entity.getNombre());
        ValidationSubcategoryService.existsubCategorias(subcaategorias);

        subcategoria.setNombre(entity.getNombre());
       
        String route = entity.getNombre();
        String resetRoute = route.replace(" ", "_");
        subcategoria.setRuta(resetRoute);
        
        subcategoria.setStatus(true);
        subcategoria.setFecha_publicacion(new Date());

        Categoria categoria = categoriaService.getGatCategoriaById(entity.getIdCategoria());
        ValidationCategory.existCategoria(categoria);

        subcategoria.setCategoria(categoria);
        Subcategoria nuevasubcategoria = subcategoriasService.save(subcategoria);
        
        return ResponseEntity.ok().body(nuevasubcategoria);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")

    public ResponseEntity<?> deleteCategoria(   HttpServletRequest request,  @PathVariable Long id) {
        
        Subcategoria subcategoria = subcategoriasService.getsubCategoria(id);
        ValidationSubcategoryService.existCategoria(subcategoria);
        subcategoria.setStatus(false);

        subcategoriasService.save(subcategoria);
      
        return ResponseEntity.ok().body("ok");
    }

    @GetMapping("/active/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")

    public ResponseEntity<?> activarCategoria(   HttpServletRequest request,  @PathVariable Long id) {
        
        Subcategoria subcategoria = subcategoriasService.getsubCategoria(id);
        ValidationSubcategoryService.existCategoria(subcategoria);
        subcategoria.setStatus(true);

        subcategoriasService.save(subcategoria);
      
        return ResponseEntity.ok().body("ok");
    }
    
    

    

}
