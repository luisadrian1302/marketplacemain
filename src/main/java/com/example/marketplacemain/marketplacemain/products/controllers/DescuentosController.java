package com.example.marketplacemain.marketplacemain.products.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.marketplacemain.marketplacemain.autenticacion.entities.User;
import com.example.marketplacemain.marketplacemain.autenticacion.security.SetAuthUser;
import com.example.marketplacemain.marketplacemain.autenticacion.services.JwtService;
import com.example.marketplacemain.marketplacemain.autenticacion.services.UserService;
import com.example.marketplacemain.marketplacemain.products.DTO.DescuentoDTO;
import com.example.marketplacemain.marketplacemain.products.entitites.Descuento;
import com.example.marketplacemain.marketplacemain.products.services.DescuentoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RequestMapping("api/descuentos")
@RestController
public class DescuentosController {

    @Autowired
    private UserService usuarioservice;

    @Autowired
    private DescuentoService descuentoService;

    @Autowired
    private JwtService jwtService;

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }



    @GetMapping("/getByUser")
    @PreAuthorize("hasRole('ROLE_VENDEDOR')")
    public ResponseEntity<?> getMethodName(HttpServletRequest request) {
        String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
        User user = usuarioservice.getUserByEmail(email);

        List<Descuento> descuentos = descuentoService.getAllByIDUser(user.getVendedor().getId());
        return ResponseEntity.ok().body(descuentos);
    }


    @GetMapping("/getByID/{id}")
    @PreAuthorize("hasRole('ROLE_VENDEDOR')")
    public ResponseEntity<?> getMethodName(HttpServletRequest request,  @PathVariable Long id) {
        String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
        User user = usuarioservice.getUserByEmail(email);

        Descuento descuento = descuentoService.getDescuentoById(id);

        if (descuento == null) {
            Map<String, String> valuesMap = new HashMap<>();
            valuesMap.put("message", "No se encontro ese elemento en la base de datos");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);
        }

        if (descuento.getVendedor().getId() != user.getVendedor().getId()) {
            Map<String, String> valuesMap = new HashMap<>();
            valuesMap.put("message", "No se puede actualuzar este descuento");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);  
        }
        return ResponseEntity.ok().body(descuento);
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_VENDEDOR')")
    public ResponseEntity<?> deleteDescuentoByID(HttpServletRequest request,  @PathVariable Long id) {
        String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
        User user = usuarioservice.getUserByEmail(email);

        Descuento descuentos = descuentoService.getDescuentoById(id);

        if (descuentos ==  null) {
            Map<String, String> valuesMap = new HashMap<>();
            valuesMap.put("message", "No se encontro ese elemento en la base de datos");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);
        }

        if (descuentos.getVendedor().getId() != user.getVendedor().getId()) {
            Map<String, String> valuesMap = new HashMap<>();
            valuesMap.put("message", "No se puede eliminar este descuento");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);  
        }

        descuentos.setStatus(0);
        descuentoService.saveDescuento(descuentos);

        return ResponseEntity.ok().body("ok");
    }
    

    @GetMapping("/getByUserAll")
    @PreAuthorize("hasRole('ROLE_VENDEDOR')")
    public ResponseEntity<?> getByUserAll(HttpServletRequest request) {
        String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
        User user = usuarioservice.getUserByEmail(email);

        List<Descuento> descuentos = descuentoService.getAllByIDUserAll(user.getVendedor().getId());
        return ResponseEntity.ok().body(descuentos);
    }
    

    @PostMapping("/crearDescuento")
    @PreAuthorize("hasRole('ROLE_VENDEDOR')")

    public ResponseEntity<?> createDescuento(   HttpServletRequest request, @Valid  @RequestBody DescuentoDTO entity,  BindingResult result) {


        System.out.println(entity.getNombre()+  " nombre");
        //TODO: process POST request
        if (result.hasFieldErrors()) {
            return validation(result);
        }

        String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
        User user = usuarioservice.getUserByEmail(email);


        Descuento descuento = new Descuento();

        // Obtener la fecha actual con la zona horaria de la Ciudad de México
        LocalDate hoy = LocalDate.now(ZoneId.of("America/Mexico_City"));
        
        // Definir rangos de fechas permitidas
        LocalDate maxInicio = hoy.plusDays(1); // Fecha máxima permitida (hoy + 1 día)
        LocalDate minInicio = hoy.minusDays(7); // Fecha mínima permitida (hoy - 7 días)
        LocalDate maxFinal = hoy.plusYears(1); // Fecha final no puede ser mayor a 1 año desde hoy

        // Obtener fechas del entity
        LocalDate fechaInicio = entity.getFechaInicioDescuento();
        LocalDate fechaFinal = entity.getFechaFinalDescuento();

         // Validar que la fecha de inicio esté en el rango permitido
        if (fechaInicio.isBefore(minInicio) || fechaInicio.isAfter(maxInicio)) {

            Map<String, String> valuesMap = new HashMap<>();
            valuesMap.put("message", "La fecha de inicio debe estar entre " + minInicio + " y " + maxInicio);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);
        }

        // Validar que la fecha final sea mayor que la fecha de inicio
        if (fechaFinal.isBefore(fechaInicio)) {
            Map<String, String> valuesMap = new HashMap<>();
            valuesMap.put("message", "La fecha final debe ser posterior a la fecha de inicio." );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);
        }

        // Validar que la fecha final no exceda el límite de 1 año
        if (fechaFinal.isAfter(maxFinal)) {
            Map<String, String> valuesMap = new HashMap<>();
            valuesMap.put("message", "La fecha final no puede ser mayor a " + maxFinal );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);

        }

        descuento.setFechaInicioDescuento(entity.getFechaInicioDescuento());
        descuento.setFechaFinalDescuento(entity.getFechaFinalDescuento());
        descuento.setPorcentajeDescuento(entity.getPorcentajeDescuento());
        descuento.setNombre(entity.getNombre());
        descuento.setStatus(1);
        descuento.setFechaPublicacion( LocalDate.now());
        descuento.setVendedor(user.getVendedor());

        Descuento nuevoDescuento = descuentoService.saveDescuento(descuento);
        
        return ResponseEntity.ok().body(nuevoDescuento);
    }
    

    @PutMapping("/actualizarescuento/{id}")
    @PreAuthorize("hasRole('ROLE_VENDEDOR')")

    public ResponseEntity<?> updateDescuento(   HttpServletRequest request, @Valid  @RequestBody DescuentoDTO entity,  BindingResult result,  @PathVariable Long id) {


        System.out.println(entity.getNombre()+  " nombre");
        //TODO: process POST request
        if (result.hasFieldErrors()) {
            return validation(result);
        }

        String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
        User user = usuarioservice.getUserByEmail(email);


        Descuento descuento = descuentoService.getDescuentoById(id);

        if (descuento == null) {
            Map<String, String> valuesMap = new HashMap<>();
            valuesMap.put("message", "No se encontro ese elemento en la base de datos");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);
        }

        if (descuento.getVendedor().getId() != user.getVendedor().getId()) {
            Map<String, String> valuesMap = new HashMap<>();
            valuesMap.put("message", "No se puede actualuzar este descuento");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);  
        }

        // Obtener la fecha actual con la zona horaria de la Ciudad de México
        LocalDate hoy = LocalDate.now(ZoneId.of("America/Mexico_City"));
        
        // Definir rangos de fechas permitidas
        LocalDate maxInicio = hoy.plusDays(1); // Fecha máxima permitida (hoy + 1 día)
        LocalDate minInicio = hoy.minusDays(7); // Fecha mínima permitida (hoy - 7 días)
        LocalDate maxFinal = hoy.plusYears(1); // Fecha final no puede ser mayor a 1 año desde hoy

        // Obtener fechas del entity
        LocalDate fechaInicio = entity.getFechaInicioDescuento();
        LocalDate fechaFinal = entity.getFechaFinalDescuento();

         // Validar que la fecha de inicio esté en el rango permitido
        if (fechaInicio.isBefore(minInicio) || fechaInicio.isAfter(maxInicio)) {

            Map<String, String> valuesMap = new HashMap<>();
            valuesMap.put("message", "La fecha de inicio debe estar entre " + minInicio + " y " + maxInicio);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);
        }

        // Validar que la fecha final sea mayor que la fecha de inicio
        if (fechaFinal.isBefore(fechaInicio)) {
            Map<String, String> valuesMap = new HashMap<>();
            valuesMap.put("message", "La fecha final debe ser posterior a la fecha de inicio." );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);
        }

        // Validar que la fecha final no exceda el límite de 1 año
        if (fechaFinal.isAfter(maxFinal)) {
            Map<String, String> valuesMap = new HashMap<>();
            valuesMap.put("message", "La fecha final no puede ser mayor a " + maxFinal );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);

        }

        descuento.setFechaInicioDescuento(entity.getFechaInicioDescuento());
        descuento.setFechaFinalDescuento(entity.getFechaFinalDescuento());
        descuento.setPorcentajeDescuento(entity.getPorcentajeDescuento());
        descuento.setNombre(entity.getNombre());
        descuento.setFechaPublicacion( LocalDate.now());

        Descuento nuevoDescuento = descuentoService.saveDescuento(descuento);
        
        return ResponseEntity.ok().body(nuevoDescuento);
    }
    


}
