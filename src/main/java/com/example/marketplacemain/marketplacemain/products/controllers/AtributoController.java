package com.example.marketplacemain.marketplacemain.products.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.example.marketplacemain.marketplacemain.autenticacion.DTO.AtributoDTO;
import com.example.marketplacemain.marketplacemain.autenticacion.entities.User;
import com.example.marketplacemain.marketplacemain.autenticacion.security.SetAuthUser;
import com.example.marketplacemain.marketplacemain.autenticacion.services.JwtService;
import com.example.marketplacemain.marketplacemain.autenticacion.services.UserService;
import com.example.marketplacemain.marketplacemain.products.entitites.Atributo;
import com.example.marketplacemain.marketplacemain.products.entitites.Subcategoria;
import com.example.marketplacemain.marketplacemain.products.services.AtributoService;
import com.example.marketplacemain.marketplacemain.products.services.CaracteristicaService;
import com.example.marketplacemain.marketplacemain.products.services.SubcategoriasService;
import com.example.marketplacemain.marketplacemain.products.services.validations.ValidationAtributeService;
import com.example.marketplacemain.marketplacemain.products.services.validations.ValidationSubcategoryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/atributo")
public class AtributoController {
     @Autowired
    private UserService usuarioservice;

    @Autowired
    private SubcategoriasService subcategoriasService;

    @Autowired
    private AtributoService atributoService;


    @Autowired
    private CaracteristicaService caracteristicaService;

    @Autowired
    private JwtService jwtService;

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }



    @GetMapping("/getBySubcategoria/{id}")
    @PreAuthorize("hasRole('ROLE_VENDEDOR')")
    public ResponseEntity<?> getByiDsUBCATEGORIA(@PathVariable Long id, HttpServletRequest request) {
        List<Atributo> atributos = atributoService.getAllBySubcategoria(id);
        return ResponseEntity.ok().body(atributos);
    }
    

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAnyRole('ROLE_VENDEDOR', 'ROLE_EMPLOYEE')")
    public ResponseEntity<?> getById(@PathVariable Long id, HttpServletRequest request) {
        Atributo atributos = atributoService.getAtributoById(id);
        return ResponseEntity.ok().body(atributos);
    }
    

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_VENDEDOR', 'ROLE_EMPLOYEE')")
    public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest request) {
        Atributo atributo = atributoService.getAtributoById(id);
        atributo.setStatus((byte) 0);
        atributoService.saveAtributo(atributo);
        return ResponseEntity.ok().body("ok");
    }

    @GetMapping("/active/{id}")
    @PreAuthorize("hasAnyRole('ROLE_VENDEDOR', 'ROLE_EMPLOYEE')")
    public ResponseEntity<?> active(@PathVariable Long id, HttpServletRequest request) {
        Atributo atributo = atributoService.getAtributoById(id);
        atributo.setStatus((byte) 1);
        atributoService.saveAtributo(atributo);

        return ResponseEntity.ok().body("ok");
    }


    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> getAll( HttpServletRequest request) {
        return ResponseEntity.ok().body(atributoService.getAllDescuentos());
    }


    @PostMapping("/crearAtributo")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")

    public ResponseEntity<?> createDescuento(   HttpServletRequest request, @Valid  @RequestBody AtributoDTO entity,  BindingResult result) {


        System.out.println(entity.getNombre()+  " nombre");
        //TODO: process POST request
        if (result.hasFieldErrors()) {
            return validation(result);
        }

        String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
        User user = usuarioservice.getUserByEmail(email);


        Atributo atributoOld = new Atributo();

        atributoOld.setNombre(entity.getNombre());
        atributoOld.setTipoPropiedad(entity.getTipoPropiedad());

        Subcategoria subcategoria = subcategoriasService.getsubCategoria(entity.getIdSubcategoria());
        if (subcategoria == null) {
            Map<String, String> valuesMap = new HashMap<>();

            valuesMap.put("message", "No se pudo encontrar el id de la categoria");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);
        }

        // verificar que no exista un atributo con ese mismo nombre
        Optional<Atributo> atributoVerify = subcategoria.getAtributos().stream().filter(e -> e.getNombre().toLowerCase().equals( entity.getNombre().toLowerCase()) ).findFirst();

        subcategoria.getAtributos().stream().forEach(e-> System.out.println(e.getNombre()));

        System.out.println(atributoVerify.isPresent());
        System.out.println(entity.getNombre());
        if (atributoVerify.isPresent()) {
            Map<String, String> valuesMap = new HashMap<>();

            valuesMap.put("message", "Ya existe un atributo con ese nombre, intentelo con otro");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);
        }
        atributoOld.setSubcategoria(subcategoria);
        atributoOld.setPrivacidad(1);
        atributoOld.setEstatusValidacion((byte) 0);
        atributoOld.setPrivacidad(1);
        atributoOld.setStatus((byte) 1);
        atributoOld.setFechaCreacion( LocalDateTime.now() );

        // atributoOld.setFechaCreacion();
      

        Atributo nuevoAtributo = atributoService.saveAtributo(atributoOld);
        
        return ResponseEntity.ok().body(nuevoAtributo);
    }
    


    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")

    public ResponseEntity<?> updateDescuento(   HttpServletRequest request, @Valid  @RequestBody AtributoDTO entity, 
     BindingResult result, @PathVariable Long id) {

        if (result.hasFieldErrors()) {
            return validation(result);
        }

        Atributo atributoOld = atributoService.getAtributoById(id);
        ValidationAtributeService.existCategoria(atributoOld);

        Long countIds = caracteristicaService.getCountByAtributo(id);

       

        atributoOld.setNombre(entity.getNombre());
        atributoOld.setTipoPropiedad(entity.getTipoPropiedad());

        Subcategoria subcategoria = subcategoriasService.getsubCategoria(entity.getIdSubcategoria());
        ValidationSubcategoryService.existCategoria(subcategoria);

        // verificar que no exista un atributo con ese mismo nombre
        List<Atributo> atributoVerify = atributoService.getByIdSubAndName(subcategoria.getId(), entity.getNombre(), id);

        if (atributoVerify.size() > 0) {
            Map<String, String> valuesMap = new HashMap<>();
            valuesMap.put("message", "Ya existe un atributo con ese nombre, intentelo con otro");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);
        }
        if (countIds == 0) {
            atributoOld.setSubcategoria(subcategoria);
        }

        atributoOld.setPrivacidad(1);
        atributoOld.setEstatusValidacion((byte) 0);
        atributoOld.setPrivacidad(1);
        atributoOld.setStatus((byte) 1);
        atributoOld.setFechaCreacion( LocalDateTime.now() ); 


        Atributo nuevoAtributo = atributoService.saveAtributo(atributoOld);
        return ResponseEntity.ok().body(nuevoAtributo);
    }
    

    @GetMapping("/getCount/{id}")
    @PreAuthorize("hasAnyRole( 'ROLE_EMPLOYEE' )")
    public ResponseEntity<?> getCount(@PathVariable Long id, HttpServletRequest request) {
        Long getId = caracteristicaService.getCountByAtributo(id);
        return ResponseEntity.ok().body(getId);
    }
    

}
