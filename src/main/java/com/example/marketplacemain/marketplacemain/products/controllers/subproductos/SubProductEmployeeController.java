package com.example.marketplacemain.marketplacemain.products.controllers.subproductos;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.marketplacemain.marketplacemain.autenticacion.entities.Client;
import com.example.marketplacemain.marketplacemain.autenticacion.entities.User;
import com.example.marketplacemain.marketplacemain.autenticacion.security.SetAuthUser;
import com.example.marketplacemain.marketplacemain.autenticacion.services.JwtService;
import com.example.marketplacemain.marketplacemain.autenticacion.services.UserService;
import com.example.marketplacemain.marketplacemain.products.entitites.Producto;
import com.example.marketplacemain.marketplacemain.products.entitites.SubProducto;
import com.example.marketplacemain.marketplacemain.products.services.AtributoService;
import com.example.marketplacemain.marketplacemain.products.services.CaracteristicaService;
import com.example.marketplacemain.marketplacemain.products.services.CaracteristicasTableService;
import com.example.marketplacemain.marketplacemain.products.services.DescuentoService;
import com.example.marketplacemain.marketplacemain.products.services.ProductService;
import com.example.marketplacemain.marketplacemain.products.services.SubproductoService;
import com.example.marketplacemain.marketplacemain.products.services.ValorService;
import com.example.marketplacemain.marketplacemain.products.services.validations.ValidationProductService;
import com.example.marketplacemain.marketplacemain.products.services.validations.ValidationSubproductService;
import com.example.marketplacemain.marketplacemain.websockets.controllers.NotificationController;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("api/SubProduct/employee")
@RestController
public class SubProductEmployeeController {
     @Autowired
    private AtributoService atributoService;

    @Autowired
    private DescuentoService descuentoService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SubproductoService subproductoService;

    @Autowired
    private ValorService valorService;

    @Autowired
    private CaracteristicaService caracteristicaService;

    @Autowired
    private CaracteristicasTableService caracteTableService;
       @Autowired
    private UserService usuarioservice;

    @Autowired
    private NotificationController webSocketController;

    @Autowired
    private JwtService jwtService;

    // employee
    @GetMapping("getBySubProductDisapprove")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> getBySubProductDisapprove( HttpServletRequest request) {

        return ResponseEntity.ok().body(subproductoService.getAllBysubproductDisapprove());
    }

    @GetMapping("getByIdEmployee/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> getByIdEmployee( HttpServletRequest request, @PathVariable Long id) {
        subproductoService.verificarSubProductos(id);
        // obtener subproducto
        SubProducto subProducto = subproductoService.getById(id);
        ValidationSubproductService.validateSubProductISNotEmpty(subProducto);
        return ResponseEntity.ok().body(subProducto);
    }


    @DeleteMapping("rechazarProducto/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> rechazarProducto( HttpServletRequest request,
    @PathVariable Long id) {

        String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
        User user = usuarioservice.getUserByEmail(email);
        // verificar si ese subproducto entra en los productos que el usuario tiene
        SubProducto subProducto = subproductoService.getById(id);
        ValidationSubproductService.validateSubProductISNotEmpty(subProducto);
        subProducto.setStatusValidacion(0);
        subproductoService.save(subProducto);

        String username = subProducto.getProducto().getVendedor().getUsuario().getEmail();


        Map<String, String> message =new HashMap<>();
        message.put("message", "updateDelete");
        webSocketController.sendMessageToUserDelete(username, message, subProducto);
 
        return ResponseEntity.ok().body(username);
    }


    @GetMapping("aprobarPorID/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> aprobarPorID( HttpServletRequest request,
    @PathVariable Long id) {

        String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
        User user = usuarioservice.getUserByEmail(email);
        // verificar si ese subproducto entra en los productos que el usuario tiene
        SubProducto subProducto = subproductoService.getById(id);
        ValidationSubproductService.validateSubProductISNotEmpty(subProducto);
        subProducto.setStatusValidacion(1);
        subproductoService.save(subProducto);

        String username = subProducto.getProducto().getVendedor().getUsuario().getEmail();


        Map<String, String> message =new HashMap<>();
        message.put("message", "updateDelete");
        webSocketController.sendMessageToUserAprovee(username, message, subProducto);
 
        return ResponseEntity.ok().body(username);
    }



}
