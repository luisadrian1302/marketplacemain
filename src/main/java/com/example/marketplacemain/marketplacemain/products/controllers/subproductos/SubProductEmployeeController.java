package com.example.marketplacemain.marketplacemain.products.controllers.subproductos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.marketplacemain.marketplacemain.autenticacion.services.JwtService;
import com.example.marketplacemain.marketplacemain.autenticacion.services.UserService;
import com.example.marketplacemain.marketplacemain.products.entitites.SubProducto;
import com.example.marketplacemain.marketplacemain.products.services.AtributoService;
import com.example.marketplacemain.marketplacemain.products.services.CaracteristicaService;
import com.example.marketplacemain.marketplacemain.products.services.CaracteristicasTableService;
import com.example.marketplacemain.marketplacemain.products.services.DescuentoService;
import com.example.marketplacemain.marketplacemain.products.services.ProductService;
import com.example.marketplacemain.marketplacemain.products.services.SubproductoService;
import com.example.marketplacemain.marketplacemain.products.services.ValorService;
import com.example.marketplacemain.marketplacemain.products.services.validations.ValidationSubproductService;

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


}
