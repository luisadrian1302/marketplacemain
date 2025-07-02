package com.example.marketplacemain.marketplacemain.autenticacion.controllador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.marketplacemain.marketplacemain.autenticacion.DTO.DireccionDTO;
import com.example.marketplacemain.marketplacemain.autenticacion.entities.Direcciones;
import com.example.marketplacemain.marketplacemain.autenticacion.entities.User;
import com.example.marketplacemain.marketplacemain.autenticacion.security.SetAuthUser;
import com.example.marketplacemain.marketplacemain.autenticacion.services.DireccionService;
import com.example.marketplacemain.marketplacemain.autenticacion.services.JwtService;
import com.example.marketplacemain.marketplacemain.autenticacion.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/direccion")

public class DireccionesController {
     @Autowired
    private UserService userService;

    @Autowired
    private DireccionService direccionService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/guardar")
    public ResponseEntity<?> recibirDireccion(HttpServletRequest request, @Valid @RequestBody DireccionDTO direccion) {
        String username = SetAuthUser.getUsernameDeserialize(request, jwtService);

        Direcciones direcciones = new Direcciones();

        User user = userService.getUserByEmail(username);
        if (user == null) {
            Map<String, String> valuesMap = new HashMap<>();

            valuesMap.put("message", "No se pudo crear esta direccion");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);

        }

        direcciones.setCalle(direccion.getCalle());
        direcciones.setCiudad(direccion.getCiudad());
        direcciones.setClient(user.getClient());
        direcciones.setColonia(direccion.getColonia());
        direcciones.setEntre_calle(direccion.getEntreCalle());
        direcciones.setEstado(direccion.getEstado());
        direcciones.setMunicipio(direccion.getMunicipio());
        direcciones.setNumero_externo(direccion.getNumeroExterno());
        direcciones.setNumero_interno(direccion.getNumeroInterno());
        direcciones.setPais(direccion.getPais());
        direcciones.setReferencias(direccion.getReferencias());
        direcciones.setY_calle(direccion.getyCalle());
        direcciones.setCodigoPostal(direccion.getCodigoPostal());
        direcciones.setLatitude(direccion.getLatitude());
        direcciones.setLongitud(direccion.getLongitud());
        direcciones.setStatus(1);


        direccionService.save(direcciones);


        return ResponseEntity.ok("Ok");
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll( HttpServletRequest request) {
        String username = SetAuthUser.getUsernameDeserialize(request, jwtService);


        User user = userService.getUserByEmail(username);
        if (user == null) {
            Map<String, String> valuesMap = new HashMap<>();

            valuesMap.put("message", "No se pudo crear esta direccion");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);

        }
        return ResponseEntity.ok().body(direccionService.getAllByUser(user.getClient().getId()));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarDireccion(HttpServletRequest request, @Valid @RequestBody DireccionDTO direccion,  @PathVariable Long id) {
        String username = SetAuthUser.getUsernameDeserialize(request, jwtService);


        User user = userService.getUserByEmail(username);
        if (user == null) {
            Map<String, String> valuesMap = new HashMap<>();

            valuesMap.put("message", "No se pudo crear esta direccion");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);

        }
        List<Direcciones> direccionesList = direccionService.getAllByIDAndDireccion(user.getClient().getId(), id);
        Direcciones direcciones = direccionesList.get(0);

        direcciones.setCalle(direccion.getCalle());
        direcciones.setCiudad(direccion.getCiudad());
        direcciones.setColonia(direccion.getColonia());
        direcciones.setEntre_calle(direccion.getEntreCalle());
        direcciones.setEstado(direccion.getEstado());
        direcciones.setMunicipio(direccion.getMunicipio());
        direcciones.setNumero_externo(direccion.getNumeroExterno());
        direcciones.setNumero_interno(direccion.getNumeroInterno());
        direcciones.setPais(direccion.getPais());
        direcciones.setReferencias(direccion.getReferencias());
        direcciones.setY_calle(direccion.getyCalle());
        direcciones.setCodigoPostal(direccion.getCodigoPostal());
        direcciones.setLatitude(direccion.getLatitude());
        direcciones.setLongitud(direccion.getLongitud());

        direccionService.save(direcciones);


        return ResponseEntity.ok("Ok");
    }


    
    @GetMapping("/getAll/{id}")
    public ResponseEntity<?> getAllById( HttpServletRequest request, @PathVariable Long id) {
        String username = SetAuthUser.getUsernameDeserialize(request, jwtService);


        User user = userService.getUserByEmail(username);
        if (user == null) {
            Map<String, String> valuesMap = new HashMap<>();

            valuesMap.put("message", "No se pudo crear esta direccion");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);
        }
        List<Direcciones> direcciones = direccionService.getAllByIDAndDireccion(user.getClient().getId(), id);

        return ResponseEntity.ok().body(direcciones);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> detele( HttpServletRequest request, @PathVariable Long id) {
        String username = SetAuthUser.getUsernameDeserialize(request, jwtService);

        User user = userService.getUserByEmail(username);
        if (user == null) {
            Map<String, String> valuesMap = new HashMap<>();

            valuesMap.put("message", "No se pudo eliminar esta direccion");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);
        }
        List<Direcciones> direcciones = direccionService.getAllByIDAndDireccion(user.getClient().getId(), id);
        Direcciones direccion = direcciones.get(0);

        if (direccion == null) {
            Map<String, String> valuesMap = new HashMap<>();

            valuesMap.put("message", "No se pudo eliminar esta direccion");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);
        }
        direccion.setStatus(0);

        direccionService.save(direccion);


        return ResponseEntity.ok().body("ok");
    }
}
