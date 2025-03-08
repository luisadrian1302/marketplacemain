package com.example.marketplacemain.marketplacemain.products.controllers.subproductos;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.marketplacemain.marketplacemain.autenticacion.entities.User;
import com.example.marketplacemain.marketplacemain.autenticacion.security.SetAuthUser;
import com.example.marketplacemain.marketplacemain.autenticacion.services.JwtService;
import com.example.marketplacemain.marketplacemain.autenticacion.services.UserService;
import com.example.marketplacemain.marketplacemain.products.DTO.AtributoDTO;
import com.example.marketplacemain.marketplacemain.products.DTO.CaracteristicaTablaDTO;
import com.example.marketplacemain.marketplacemain.products.DTO.GetSubProductosDTO;
import com.example.marketplacemain.marketplacemain.products.DTO.SubproductoDTO;
import com.example.marketplacemain.marketplacemain.products.DTO.ValorPropiedadDTO;
import com.example.marketplacemain.marketplacemain.products.entitites.Atributo;
import com.example.marketplacemain.marketplacemain.products.entitites.Caracteristicas;
import com.example.marketplacemain.marketplacemain.products.entitites.CaractertisticaTabla;
import com.example.marketplacemain.marketplacemain.products.entitites.Descuento;
import com.example.marketplacemain.marketplacemain.products.entitites.Producto;
import com.example.marketplacemain.marketplacemain.products.entitites.SubProducto;
import com.example.marketplacemain.marketplacemain.products.entitites.Valor;
import com.example.marketplacemain.marketplacemain.products.images.ImageHelper;
import com.example.marketplacemain.marketplacemain.products.services.AtributoService;
import com.example.marketplacemain.marketplacemain.products.services.CaracteristicaService;
import com.example.marketplacemain.marketplacemain.products.services.CaracteristicasTableService;
import com.example.marketplacemain.marketplacemain.products.services.DescuentoService;
import com.example.marketplacemain.marketplacemain.products.services.ProductService;
import com.example.marketplacemain.marketplacemain.products.services.SubproductoService;
import com.example.marketplacemain.marketplacemain.products.services.ValorService;
import com.example.marketplacemain.marketplacemain.products.services.validations.ValidationDiscountService;
import com.example.marketplacemain.marketplacemain.products.services.validations.ValidationProductService;
import com.example.marketplacemain.marketplacemain.products.services.validations.ValidationSubproductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@RequestMapping("api/SubProduct/seller")
@RestController
public class SubProductSellerController {
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

    @Value("${app.upload.dir:${user.home}}")
    private String uploadDir;

    @PostMapping("/subir")
    @PreAuthorize("hasRole('ROLE_VENDEDOR')")
    public ResponseEntity<?> crearSubproducto(@RequestParam("imagenes") List<MultipartFile> imagenes,
    @RequestParam("caractertistica") String caractertistica, @RequestParam(value = "descuento",  required = false) Long descuento
    , @RequestParam("inforacionGeneral") String inforacionGeneral, @RequestParam("caracteristicaAll") String caracteristicaAll,
    @RequestParam("id_producto") Long id_producto, HttpServletRequest request ) {
        

        try {
        
        
            // comprobar que exista el tipo de valor color en un producto, si existe hay que comprobar si el usuario mando el color designado
            // comprobar que los
            String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
            User user = usuarioservice.getUserByEmail(email);

            Producto producto = productService.getById(id_producto);
            List<SubProducto> subproductos = subproductoService.getAllBySubcategoria(id_producto);

            List<Caracteristicas> caracteristicasList = new ArrayList<Caracteristicas>();
            List<CaractertisticaTabla> caracteristicasTable = new ArrayList<CaractertisticaTabla>();
            // validar cantidad de imagenes
            ValidationSubproductService.validateCantidadImages(imagenes);
            

            ObjectMapper objectMapper = new ObjectMapper();
            List<AtributoDTO> atributos = objectMapper.readValue(caractertistica, new TypeReference<List<AtributoDTO>>() {}); //aqui guardamos atributo y valor y tendra una caracteristica

            List<CaracteristicaTablaDTO> caracteristicasTabla = objectMapper.readValue(caracteristicaAll, new TypeReference<List<CaracteristicaTablaDTO>>() {}); //estos son caracterticas de la tabla
            SubproductoDTO subproductoDTO = objectMapper.readValue(inforacionGeneral, new TypeReference<SubproductoDTO>() {}); //aqui vamos a guardar el subproducto

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<SubproductoDTO>> violations = validator.validate(subproductoDTO);

            if (!violations.isEmpty()) {
                Map<String, List<String>> valuesMap = new HashMap<>();

                List<String> arreglo = new ArrayList<>();
                for (ConstraintViolation<SubproductoDTO> violation : violations) {
                    System.out.println(violation.getMessage());
                    arreglo.add(violation.getMessage());
                }
                valuesMap.put("message", arreglo);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);
            }



            Boolean existProperyColor = false;
            Integer countCaracteristicasSimilares = 0;

            ValidationSubproductService.VerificarCaracteristicas(atributos);

            List<ValorPropiedadDTO> valorPropiedadDTOs = caracteristicaService.findValueValor(id_producto);

            Set<String> elementosEncontrados = new HashSet<>();

            for(ValorPropiedadDTO valorPropiedadDTO: valorPropiedadDTOs){

                for(AtributoDTO atributoDTO: atributos){
                    // verificar si ya existe un producto similar
                    if (atributoDTO.getPropiedad().toLowerCase().equals(valorPropiedadDTO.getNombre().toLowerCase() ) 
                    && atributoDTO.getValor().toLowerCase().equals(valorPropiedadDTO.getValor().toLowerCase() ) ) {
                        countCaracteristicasSimilares ++;
                    }

                    // verificar si existe relacion de valores en productos (al menos dos)
                    if ( atributoDTO.getPropiedad().toLowerCase().equals(valorPropiedadDTO.getNombre().toLowerCase()) ) {

                        elementosEncontrados.add(valorPropiedadDTO.getNombre().toLowerCase());
                        
                    }
                    if (valorPropiedadDTO.getTipoPropiedad().toLowerCase().equals("color") ) {
                        existProperyColor = true;
                    }

                }
            }

            // verificar si el atributo obtenido realmente este color
            valorPropiedadDTOs.forEach(e->System.out.println(e.getTipoPropiedad()+ ": " + e.getValor()));
            elementosEncontrados.forEach(e->System.out.println(e));

            ValidationSubproductService.isSubProductDuplicateCreateOrUpdate(atributos, countCaracteristicasSimilares);
            ValidationSubproductService.checkTwoSimilarProperties(elementosEncontrados, valorPropiedadDTOs);

            // comparar que color exista en nuestra lista de elementos
            if (existProperyColor) {
                ValidationSubproductService.getColorExist(atributos);                
            }
 
            // crear el subproducto
            SubProducto subProducto =new SubProducto();
            Set<Caracteristicas> setCaracteristicas = new HashSet<>(caracteristicasList);
            Set<CaractertisticaTabla> setCaracteristicasTabla = new HashSet<>(caracteristicasTable);

          
            subProducto.setDescripcion(subproductoDTO.getDescripcion());
            if (descuento != null) {

                Descuento descuento2 = descuentoService.getDescuentoById(descuento);
                subProducto.setDescuento(descuento2);
            }
            subProducto.setGrosor(subproductoDTO.getGrosor());
            subProducto.setFotoPrincipal("");
            subProducto.setPesoProducto(subproductoDTO.getPeso());
            subProducto.setTamañoAlto(subproductoDTO.getLargo());
            subProducto.setTamañoAncho(subproductoDTO.getAncho());
            
            subProducto.setProducto(producto);

            subProducto.setPrecio(subproductoDTO.getPrecio());
            subProducto.setStatus(1);
            subProducto.setStock(subproductoDTO.getCantidad()); 
            subProducto.setDescripcion(subproductoDTO.getDescripcion());
            subProducto.setStatusValidacion(0);
            subProducto.setFechaPublicacion(LocalDateTime.now());

            SubProducto subProductoSave = subproductoService.save(subProducto);

            // guardar la multmedia
            List<String> nombresGuardados = new ArrayList<>();
            Integer counter = 0;
            for (MultipartFile imagen : imagenes) {

                String nombre_img = ImageHelper.uploadImage(uploadDir, subProductoSave.getId().toString()+"_"+counter.toString() , imagen, "subproducto");
                if (!nombre_img.equals("null")) {          
                    nombresGuardados.add(nombre_img);
                    counter++;                
                }
            }
            ValidationSubproductService.getNamesFilesAndDelete(nombresGuardados, subproductoService, subProductoSave);


            String nombresGuardado = objectMapper.writeValueAsString(nombresGuardados); //aqui vamos a guardar el subproducto
            subProductoSave.setMultimedia(nombresGuardado);
            // aqui creamos el atributo
            for(AtributoDTO atributo : atributos){
                Valor valor = new Valor();

                valor.setValor(atributo.getValor());
                valor.setStatus((byte) 1);
                valor.setFechaCreacion(LocalDateTime.now());

                Valor valorsave = valorService.saveValor(valor);
                // obtener los atributos
                Atributo atributo2 = atributoService.getAtributoById(atributo.getId_atributo());
                
                // crear la caractertistica
                Caracteristicas caracteristicas = new Caracteristicas();
                caracteristicas.setValor(valorsave);
                caracteristicas.setAtributo(atributo2);
                caracteristicas.setColocarCaracteristicaPrincipal((byte) 1);
                caracteristicas.setStatus((byte) 1);
                caracteristicas.setSubProducto(subProductoSave);
                caracteristicas.setFechaPublicacion(LocalDateTime.now());

                Caracteristicas caracteristicas2 = caracteristicaService.save(caracteristicas);
                caracteristicasList.add(caracteristicas2);
            }

            for(CaracteristicaTablaDTO caracteristicaTablaDTO : caracteristicasTabla){
                CaractertisticaTabla caractertisticaTabla = new CaractertisticaTabla();
                caractertisticaTabla.setValor(caracteristicaTablaDTO.getValor());
                caractertisticaTabla.setAtributo(caracteristicaTablaDTO.getAtributo());
                caractertisticaTabla.setSubProducto(subProductoSave);
                CaractertisticaTabla caractertisticaTabla2 = caracteTableService.saveDescuento(caractertisticaTabla);
                caracteristicasTable.add(caractertisticaTabla2);

            }
            subproductoService.save(subProductoSave);
                    
            return ResponseEntity.ok("ok");

        } catch (JsonMappingException e) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Hubo un error en el servidor");
            return ResponseEntity.badRequest().body(body);
        }catch (JsonProcessingException e) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Hubo un error en el servidor");
            return ResponseEntity.badRequest().body(body);
        }
        catch (IOException e) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "No se pudo guardar el elemento");
            return ResponseEntity.badRequest().body(body);
        }
        
    }


    @PutMapping("/edit")
    @PreAuthorize("hasRole('ROLE_VENDEDOR')")
    public ResponseEntity<?> editarSubproducto(@RequestParam("imagenes") List<MultipartFile> imagenes,
    @RequestParam("caractertistica") String caractertistica, @RequestParam(value = "descuento",  required = false) Long descuento
    , @RequestParam("inforacionGeneral") String inforacionGeneral, @RequestParam("caracteristicaAll") String caracteristicaAll,
    @RequestParam("id_producto") Long id_producto, @RequestParam("id_subproducto") Long id_subproducto  ) {
        
        try {
            // comprobar que exista el tipo de valor color en un producto, si existe hay que comprobar si el usuario mando el color designado
            // comprobar que los
            Producto producto = productService.getById(id_producto);
            List<SubProducto> subproductos = subproductoService.getAllBySubcategoria(id_producto);

            List<Caracteristicas> caracteristicasList = new ArrayList<Caracteristicas>();
            List<CaractertisticaTabla> caracteristicasTable = new ArrayList<CaractertisticaTabla>();
            // comprobamos que no se 
            
          // validar cantidad de imagenes
            ValidationSubproductService.validateCantidadImages(imagenes);

            ObjectMapper objectMapper = new ObjectMapper();
            List<AtributoDTO> atributos = objectMapper.readValue(caractertistica, new TypeReference<List<AtributoDTO>>() {}); //aqui guardamos atributo y valor y tendra una caracteristica

            List<CaracteristicaTablaDTO> caracteristicasTabla = objectMapper.readValue(caracteristicaAll, new TypeReference<List<CaracteristicaTablaDTO>>() {}); //estos son caracterticas de la tabla
            SubproductoDTO subproductoDTO = objectMapper.readValue(inforacionGeneral, new TypeReference<SubproductoDTO>() {}); //aqui vamos a guardar el subproducto

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<SubproductoDTO>> violations = validator.validate(subproductoDTO);

            if (!violations.isEmpty()) {
                Map<String, List<String>> valuesMap = new HashMap<>();

                List<String> arreglo = new ArrayList<>();
                for (ConstraintViolation<SubproductoDTO> violation : violations) {
                    System.out.println(violation.getMessage());
                    arreglo.add(violation.getMessage());
                }
                valuesMap.put("message", arreglo);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);
            }

            Boolean existProperyColor = false;
            Integer countCaracteristicasSimilares = 0;
            ValidationSubproductService.VerificarCaracteristicas(atributos);

            List<ValorPropiedadDTO> valorPropiedadDTOs = caracteristicaService.findValueValorByIDsubcategoria(id_producto, id_subproducto);

            Set<String> elementosEncontrados = new HashSet<>();

            for(ValorPropiedadDTO valorPropiedadDTO: valorPropiedadDTOs){

                for(AtributoDTO atributoDTO: atributos){
                    // verificar si ya existe un producto similar
                    if (atributoDTO.getPropiedad().toLowerCase().equals(valorPropiedadDTO.getNombre().toLowerCase() ) 
                    && atributoDTO.getValor().toLowerCase().equals(valorPropiedadDTO.getValor().toLowerCase() ) ) {
                        countCaracteristicasSimilares ++;
                    }

                    // verificar si existe relacion de valores en productos (al menos dos)
                    if ( atributoDTO.getPropiedad().toLowerCase().equals(valorPropiedadDTO.getNombre().toLowerCase()) ) {

                        elementosEncontrados.add(valorPropiedadDTO.getNombre().toLowerCase());
                        
                    }
                    if (valorPropiedadDTO.getTipoPropiedad().toLowerCase().equals("color") ) {
                        existProperyColor = true;
                    }

                }
            }

            valorPropiedadDTOs.forEach(e->System.out.println(e.getTipoPropiedad()+ ": " + e.getValor()));
            elementosEncontrados.forEach(e->System.out.println(e));

            ValidationSubproductService.isSubProductDuplicateCreateOrUpdate(atributos, countCaracteristicasSimilares);        
            ValidationSubproductService.checkTwoSimilarProperties(elementosEncontrados, valorPropiedadDTOs);


            // comparar que color exista en nuestra lista de elementos
            if (existProperyColor) {
                ValidationSubproductService.getColorExist(atributos);
            }
           
            // crear el subproducto
            SubProducto subProducto =subproductoService.getById(id_subproducto);
            ValidationSubproductService.validateSubProductISNotEmpty(subProducto);
          
            subProducto.setDescripcion(subproductoDTO.getDescripcion());
            if (descuento != null) {

                Descuento descuento2 = descuentoService.getDescuentoById(descuento);
                subProducto.setDescuento(descuento2);
            }else{
                subProducto.setDescuento(null);
            }
            subProducto.setGrosor(subproductoDTO.getGrosor());
            subProducto.setFotoPrincipal("");
            subProducto.setPesoProducto(subproductoDTO.getPeso());
            subProducto.setTamañoAlto(subproductoDTO.getLargo());
            subProducto.setTamañoAncho(subproductoDTO.getAncho());
            subProducto.setProducto(producto);
            subProducto.setPrecio(subproductoDTO.getPrecio());
            subProducto.setStatus(1);
            subProducto.setStock(subproductoDTO.getCantidad()); 
            subProducto.setDescripcion(subproductoDTO.getDescripcion());

            subProducto.getCaracteristicas().clear();
            subProducto.getCaracteristicasTable().clear();
            subProducto.setFechaModificacion(LocalDateTime.now());

            SubProducto subProductoSave = subproductoService.save(subProducto);;

            // guardar la multmedia
            List<String> nombresGuardados = new ArrayList<>();
            Integer counter = 0;
            for (MultipartFile imagen : imagenes) {

                
                String nombre_img = ImageHelper.uploadImage(uploadDir, subProductoSave.getId().toString()+"_"+counter.toString() , imagen, "subproducto");
                if (!nombre_img.equals("null")) {          
                    nombresGuardados.add(nombre_img);
                    counter++;                
                }
            }

            String nombresGuardado = objectMapper.writeValueAsString(nombresGuardados); //aqui vamos a guardar el subproducto

            if (counter > 0) {
                
                subProductoSave.setMultimedia(nombresGuardado);
            }

            // aqui creamos el atributo
            for(AtributoDTO atributo : atributos){
                Valor valor = new Valor();

                valor.setValor(atributo.getValor());
                valor.setStatus((byte) 1);

                Valor valorsave = valorService.saveValor(valor);

                // obtener los atributos
                Atributo atributo2 = atributoService.getAtributoById(atributo.getId_atributo());
                
                // crear la caractertistica
                Caracteristicas caracteristicas = new Caracteristicas();
                caracteristicas.setValor(valorsave);
                caracteristicas.setAtributo(atributo2);
                caracteristicas.setColocarCaracteristicaPrincipal((byte) 1);
                caracteristicas.setStatus((byte) 1);
                caracteristicas.setSubProducto(subProductoSave);
                Caracteristicas caracteristicas2 = caracteristicaService.save(caracteristicas);
                caracteristicasList.add(caracteristicas2);
            }

            for(CaracteristicaTablaDTO caracteristicaTablaDTO : caracteristicasTabla){
                CaractertisticaTabla caractertisticaTabla = new CaractertisticaTabla();
                caractertisticaTabla.setValor(caracteristicaTablaDTO.getValor());
                caractertisticaTabla.setAtributo(caracteristicaTablaDTO.getAtributo());
                caractertisticaTabla.setSubProducto(subProductoSave);
                CaractertisticaTabla caractertisticaTabla2 = caracteTableService.saveDescuento(caractertisticaTabla);
                caracteristicasTable.add(caractertisticaTabla2);

            }


            subproductoService.save(subProductoSave);      
            return ResponseEntity.ok("ok");
        }  catch (JsonMappingException e) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Hubo un error en el servidor");
            return ResponseEntity.badRequest().body(body);
        }catch (JsonProcessingException e) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Hubo un error en el servidor");
            return ResponseEntity.badRequest().body(body);
        }
        catch (IOException e) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "No se pudo guardar el elemento");
            return ResponseEntity.badRequest().body(body);
        }
    }



    @GetMapping("getByProduct/{id}")
    @PreAuthorize("hasRole('ROLE_VENDEDOR')")
    public ResponseEntity<?> getMethodName( HttpServletRequest request, @PathVariable Long id) {

        // quitar el decuento si este ya expiro
        subproductoService.verificarSubProductos();

        String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
        User user = usuarioservice.getUserByEmail(email);
        Set<Producto> productos = user.getVendedor().getProductos();
       
        ValidationProductService.validateProductsIsNotEmpty(productos);

        Optional<Producto> producto = productos.stream().filter((product) -> product.getId() == id ).findFirst();
        ValidationProductService.validateProductsIsNotPresent(producto);

        List<SubProducto> subProductos = subproductoService.getAllBySubcategoria(id);
        return ResponseEntity.ok().body(subProductos);
    }
    


    @GetMapping("getById/{id}")
    @PreAuthorize("hasRole('ROLE_VENDEDOR')")
    public ResponseEntity<?> getById( HttpServletRequest request, @PathVariable Long id) {

        String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
        User user = usuarioservice.getUserByEmail(email);

        subproductoService.verificarSubProductos(id);

        // obtener subproducto
        SubProducto subProducto = subproductoService.getById(id);
        ValidationSubproductService.validateSubProductISNotEmpty(subProducto);
        // verificar si ese subproducto entra en los productos que el usuario tiene
        Set<Producto> productos = user.getVendedor().getProductos();

        // obtener producto por id
        ValidationProductService.validateProductsIsNotEmpty(productos);
        Optional<Producto> producto = productos.stream().filter((product) -> product.getId() == subProducto.getProducto().getId()).findFirst();
        ValidationProductService.validateProductsIsNotPresent(producto);
     
        return ResponseEntity.ok().body(subProducto);
    }


    @DeleteMapping("getById/{id}")
    @PreAuthorize("hasRole('ROLE_VENDEDOR')")
    public ResponseEntity<?> deleteByID( HttpServletRequest request, @PathVariable Long id) {
        String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
        User user = usuarioservice.getUserByEmail(email);
        // obtener subproducto
        SubProducto subProducto = subproductoService.getById(id);
        ValidationSubproductService.validateSubProductISNotEmpty(subProducto);

        // verificar si ese subproducto entra en los productos que el usuario tiene
        Set<Producto> productos = user.getVendedor().getProductos();
        ValidationProductService.validateProductsIsNotEmpty(productos);
        Optional<Producto> producto = productos.stream().filter((product) -> product.getId() == subProducto.getProducto().getId()).findFirst();
        ValidationProductService.validateProductsIsNotPresent(producto);
        // actualizar el producto
        subProducto.setStatus(4);
        SubProducto saveSubProducto = subproductoService.save(subProducto);
        
        return ResponseEntity.ok().body(saveSubProducto);
    }

    @DeleteMapping("getID/{id}")
    @PreAuthorize("hasRole('ROLE_VENDEDOR')")
    public ResponseEntity<?> eliminarSubproducto( HttpServletRequest request, @PathVariable Long id) {

        String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
        User user = usuarioservice.getUserByEmail(email);

        // verificar si ese subproducto entra en los productos que el usuario tiene
        Set<Producto> productos = user.getVendedor().getProductos();
        // obtener producto por id
        ValidationProductService.validateProductsIsNotEmpty(productos);
         // actualizar valor
        subproductoService.updateByID(id, 0);
        return ResponseEntity.ok().body("ok");
    }


    @GetMapping("getHability/{id}")
    @PreAuthorize("hasRole('ROLE_VENDEDOR')")
    public ResponseEntity<?> habilitarProducto( HttpServletRequest request, @PathVariable Long id) {


        String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
        User user = usuarioservice.getUserByEmail(email);

        subproductoService.verificarSubProductos(id);   

        // verificar si ese subproducto entra en los productos que el usuario tiene
        Set<Producto> productos = user.getVendedor().getProductos();

        // obtener producto por id
        ValidationProductService.validateProductsIsNotEmpty(productos);
        // actualizar valor
        subproductoService.updateByID(id, 1);
        // obtener subproducto
        SubProducto subProducto = subproductoService.getById(id);
        ValidationSubproductService.validateSubProductISNotEmpty(subProducto);
        // verificar si existe un producto con las mismas caracteristicas
        List<ValorPropiedadDTO> valorPropiedadDTOsTotal = caracteristicaService.findValueValorByIDsubcategoria(subProducto.getProducto().getId(), subProducto.getId());
        List<ValorPropiedadDTO> valorPropiedadDTOsSubproduct = caracteristicaService.findValuesBySubproduct(subProducto.getProducto().getId(), subProducto.getId());

        Integer countCaracteristicasSimilares = 0;
        Set<String> elementosEncontrados = new HashSet<>();
        for(ValorPropiedadDTO valorPropiedadDTO: valorPropiedadDTOsTotal){

            for(ValorPropiedadDTO atributoDTO: valorPropiedadDTOsSubproduct){
                // verificar si ya existe un producto similar
                if (atributoDTO.getNombre().toLowerCase().equals(valorPropiedadDTO.getNombre().toLowerCase() ) 
                && atributoDTO.getValor().toLowerCase().equals(valorPropiedadDTO.getValor().toLowerCase() ) ) {
                    countCaracteristicasSimilares ++;
                }

                // verificar si existe relacion de valores en productos (al menos dos)
                if ( atributoDTO.getNombre().toLowerCase().equals(valorPropiedadDTO.getNombre().toLowerCase()) ) {
                    elementosEncontrados.add(valorPropiedadDTO.getNombre().toLowerCase());
                }
            }
        }
        
        ValidationSubproductService.isSubProductDuplicate(valorPropiedadDTOsSubproduct, countCaracteristicasSimilares, subproductoService, id);
        ValidationSubproductService.checkTwoSimilarProperties(elementosEncontrados, valorPropiedadDTOsTotal, subproductoService, id);  

        Optional<Producto> producto = productos.stream().filter((product) -> product.getId() == subProducto.getProducto().getId()).findFirst();
        ValidationSubproductService.validateProductsIsNotPresent(producto, subproductoService, id);
        
        return ResponseEntity.ok().body(subProducto);
    }



    @PostMapping("publicarProducto")
    @PreAuthorize("hasRole('ROLE_VENDEDOR')")
    public ResponseEntity<?> publicarProducto( HttpServletRequest request,
    @RequestBody String ids) {

        String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
        User user = usuarioservice.getUserByEmail(email);
        // verificar si ese subproducto entra en los productos que el usuario tiene
        Set<Producto> productos = user.getVendedor().getProductos();
        ValidationProductService.validateProductsIsNotEmpty(productos);

        // Parseamos el JSON
        JSONObject jsonObject = new JSONObject(ids);
        // Extraemos el valor de la clave "ids" que es una cadena que representa un array
        String idsString = jsonObject.getString("ids");
        // Convertimos la cadena a un JSONArray
        JSONArray idsArray = new JSONArray(idsString);

        // Imprimimos el array convertido
        for (int i = 0; i < idsArray.length(); i++) {
            SubProducto subProducto = subproductoService.getById(idsArray.getLong(i));

            subProducto.setStatusValidacion(2);
            subproductoService.save(subProducto);
        }
        
        return ResponseEntity.ok().body("ok");
    }


    @PostMapping("addDescuento/{id}")
    @PreAuthorize("hasRole('ROLE_VENDEDOR')")
    public ResponseEntity<?> colocarDescuentos( HttpServletRequest request,
    @RequestBody String ids,  @PathVariable Long id) {

        String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
        User user = usuarioservice.getUserByEmail(email);

        // subproductoService.verificarSubProductos();

        // verificar si ese subproducto entra en los productos que el usuario tiene
        Set<Producto> productos = user.getVendedor().getProductos();
        Descuento descuento = descuentoService.getDescuentoById(id);
        ValidationDiscountService.validateDiscountIsNotEmpty(descuento);

        ValidationProductService.validateProductsIsNotEmpty(productos);

        // verificar si existe un producto con las mismas caracteristicas
        JSONObject jsonObject = new JSONObject(ids);
        String idsString = jsonObject.getString("ids");
        JSONArray idsArray = new JSONArray(idsString);

        for (int i = 0; i < idsArray.length(); i++) {
            SubProducto subProducto = subproductoService.getById(idsArray.getLong(i));
            subProducto.setDescuento(descuento);
            subproductoService.save(subProducto);
        }
        
        return ResponseEntity.ok().body("ok");
    }


    @PostMapping("removeDescount/{id}")
    @PreAuthorize("hasRole('ROLE_VENDEDOR')")
    public ResponseEntity<?> quitarDescuentos( HttpServletRequest request,
    @RequestBody String ids,  @PathVariable Long id) {

        String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
        User user = usuarioservice.getUserByEmail(email);
        // verificar si ese subproducto entra en los productos que el usuario tiene
        Set<Producto> productos = user.getVendedor().getProductos();
        ValidationProductService.validateProductsIsNotEmpty(productos);

        // verificar si existe un producto con las mismas caracteristicas
        JSONObject jsonObject = new JSONObject(ids);
        String idsString = jsonObject.getString("ids");
        JSONArray idsArray = new JSONArray(idsString);

        for (int i = 0; i < idsArray.length(); i++) {
            SubProducto subProducto = subproductoService.getById(idsArray.getLong(i));
            subProducto.setDescuento(null);
            subproductoService.save(subProducto);
        }     
        return ResponseEntity.ok().body("ok");
    }

    @GetMapping("getByUser")
    @PreAuthorize("hasRole('ROLE_VENDEDOR')")
    public ResponseEntity<?> getByUser( HttpServletRequest request) {

        String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
        User user = usuarioservice.getUserByEmail(email);

        Set<Producto> productos = user.getVendedor().getProductos();
        // Producto producto = productService.getById(id);
        ValidationProductService.validateProductsIsNotEmpty(productos);
        // actualizar los productos
        subproductoService.verificarSubProductos();
        // obtener todos los subproductos 
        List<GetSubProductosDTO> subProductos = subproductoService.getByUser(user.getVendedor().getId());
        return ResponseEntity.ok().body(subProductos);
    }



    @GetMapping("getByUserActive")
    @PreAuthorize("hasRole('ROLE_VENDEDOR')")
    public ResponseEntity<?> getByUserActive( HttpServletRequest request) {

        String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
        User user = usuarioservice.getUserByEmail(email);
        // verificar si ese subproducto entra en los productos que el usuario tiene
        Set<Producto> productos = user.getVendedor().getProductos();

        // Producto producto = productService.getById(id);
        ValidationProductService.validateProductsIsNotEmpty(productos);
        subproductoService.verificarSubProductos();
        return ResponseEntity.ok().body(subproductoService.getAllActiveByUser(user.getVendedor().getId()));
    }


    @GetMapping("getByUserOutDescuento")
    @PreAuthorize("hasRole('ROLE_VENDEDOR')")
    public ResponseEntity<?> getByUserOutDescuento( HttpServletRequest request) {
        String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
        User user = usuarioservice.getUserByEmail(email);
        // verificar si ese subproducto entra en los productos que el usuario tiene
        Set<Producto> productos = user.getVendedor().getProductos();

        ValidationProductService.validateProductsIsNotEmpty(productos);
        // actualizar los productos
        subproductoService.verificarSubProductos();
        // obtener todos los subproductos 
        return ResponseEntity.ok().body(subproductoService.getAllByIDandOUTdiscount(user.getVendedor().getId()));
    }

    @GetMapping("getByUserandDescuento/{id}")
    @PreAuthorize("hasRole('ROLE_VENDEDOR')")
    public ResponseEntity<?> getByUserandDescuento( HttpServletRequest request, @PathVariable Long id) {

        String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
        User user = usuarioservice.getUserByEmail(email);
        Set<Producto> productos = user.getVendedor().getProductos();

        // Producto producto = productService.getById(id);
        ValidationProductService.validateProductsIsNotEmpty(productos);
        subproductoService.verificarSubProductos();
        // obtener todos los subproductos 
        return ResponseEntity.ok().body(subproductoService.getAllByIdAndDescount(user.getVendedor().getId(), id));
    }

    
    @GetMapping("/getAtributos/{id}")
    @PreAuthorize("hasRole('ROLE_VENDEDOR')")
    public ResponseEntity<?> getAtributos( HttpServletRequest request, @PathVariable Long id) {

        String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
        User user = usuarioservice.getUserByEmail(email);
        Set<Producto> productos = user.getVendedor().getProductos();
         // Producto producto = productService.getById(id);
        ValidationProductService.validateProductsIsNotEmpty(productos);

        Optional<Producto> producto = productos.stream().filter((product) -> product.getId() == id ).findFirst();
        ValidationProductService.validateProductsIsNotPresent(producto);
        List<ValorPropiedadDTO> valorPropiedadDTOs = caracteristicaService.findValueValor(id);

        return ResponseEntity.ok().body(valorPropiedadDTOs);
    }
    



}
