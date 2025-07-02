package com.example.marketplacemain.marketplacemain.products.controllers;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.marketplacemain.marketplacemain.autenticacion.entities.User;
import com.example.marketplacemain.marketplacemain.autenticacion.security.SetAuthUser;
import com.example.marketplacemain.marketplacemain.autenticacion.services.JwtService;
import com.example.marketplacemain.marketplacemain.autenticacion.services.UserService;
import com.example.marketplacemain.marketplacemain.products.entitites.Producto;
import com.example.marketplacemain.marketplacemain.products.entitites.Subcategoria;
import com.example.marketplacemain.marketplacemain.products.images.ImageHelper;
import com.example.marketplacemain.marketplacemain.products.services.ProductService;
import com.example.marketplacemain.marketplacemain.products.services.SubcategoriasService;
import com.example.marketplacemain.marketplacemain.products.services.validations.ValidationProductService;
import com.example.marketplacemain.marketplacemain.products.services.validations.ValidationSubcategoryService;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("api/product")
@RestController
public class ProductController {
    @Value("${app.upload.dir:${user.home}}")
    private String uploadDir;

    @Autowired
    private SubcategoriasService subcategoriasService;
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService usuarioservice;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/crearProducto")
    @PreAuthorize("hasRole('ROLE_VENDEDOR')")

    public ResponseEntity<?> crearProducto(
            HttpServletRequest request,
            @RequestParam("titular") String titular,
            @RequestParam("descripcion") String descripcion,
            @RequestParam(value = "marca", required = false) String marca,
            @RequestParam("categoriaid") Long categoriaid,
            @RequestParam("subcategoriaid") Long subcategoriaid,
            @RequestParam("image") MultipartFile file
     
            ) {
                try{

                String mensaje = String.format("Nombre: %s, Apellido: %s, Archivo: %s", 
                titular, descripcion,categoriaid, subcategoriaid, file.getOriginalFilename());
                
                // guardar los datos
                Producto producto = new Producto();
                producto.setTitular(titular);
                producto.setDescripcionGeneral(descripcion);
                producto.setStatus("disapproved");
                // verificar si existe esa subcaategoria
                Subcategoria subcategoria = subcategoriasService.getsubCategoria( subcategoriaid);

                ValidationSubcategoryService.validateProductsIsNotEmpty(subcategoria);

                if (marca != null) {
                    producto.setMarca(marca);
                }
                producto.setSubcategoria(subcategoria);
                String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
                User user = usuarioservice.getUserByEmail(email);

                producto.setVendedor(user.getVendedor());
                Number counter = user.getVendedor().getProductos().size();
                String urlImage = ImageHelper.uploadImage(uploadDir, "product"+email+counter, file, "product-images");
                // guardar el producto

                producto.setImagePortada(urlImage);
                producto.setFechaPublicacion( LocalDateTime.now());
                
                productService.save(producto);
                return ResponseEntity.ok().body(mensaje);
        }catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No se pudo subir la imagen: " + e.getMessage());
        }
    

    }





    @PostMapping("/actualizarProducto")
    @PreAuthorize("hasRole('ROLE_VENDEDOR')")
    public ResponseEntity<?> updateProducto(
        HttpServletRequest request,
        @RequestParam("id") Long id,
        @RequestParam("titular") String titular,
        @RequestParam("descripcion") String descripcion,
        @RequestParam(value = "marca", required = false) String marca,
        @RequestParam("categoriaid") Long categoriaid,
        @RequestParam("subcategoriaid") Long subcategoriaid,
        @RequestParam(value = "image", required = false) MultipartFile file
 
        ) {
            try{
            
  

      
           
            String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
            User user = usuarioservice.getUserByEmail(email);
            
            Optional<Producto> productoOpt = user.getVendedor().getProductos().stream().filter(product -> product.getId().equals(id)).findFirst();
            ValidationProductService.validateProductsIsNotPresent(productoOpt);
            Producto producto = productoOpt.get();

               // // verificar si existe esa subcaategoria
            Subcategoria subcategoria = subcategoriasService.getsubCategoria( subcategoriaid);
            ValidationSubcategoryService.validateProductsIsNotEmpty(subcategoria);

            producto.setTitular(titular);
            producto.setDescripcionGeneral(descripcion);
            // validar si existen elementos
            if (producto.getSubproducto().size() == 0) {
                
                producto.setSubcategoria(subcategoria);
            }

            if (marca != null) {
                producto.setMarca(marca);
            }
            Number counter = user.getVendedor().getProductos().size() - 1;
            if (file != null) {
                String urlImage = ImageHelper.uploadImage(uploadDir, "product"+email+counter, file, "product-images");
                producto.setImagePortada(urlImage);
            }
            // guardar el producto
            producto.setFechaModificacion( LocalDateTime.now());

            productService.save(producto);           

            return ResponseEntity.ok().body("ok");
    }catch (IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("No se pudo subir la imagen: " + e.getMessage());
    }


}



    @GetMapping("/verProductosPorUsuario")
    @PreAuthorize("hasRole('ROLE_VENDEDOR')")
    public ResponseEntity<?> getAllProductByUser(
        HttpServletRequest request
        ) {
            
            String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
            User user = usuarioservice.getUserByEmail(email);
            return ResponseEntity.ok().body(user.getVendedor().getProductos());
        }
    @DeleteMapping("/getProduct/{id}")
    @PreAuthorize("hasRole('ROLE_VENDEDOR')")

    public ResponseEntity<?> deleteProduct(
        HttpServletRequest request, @PathVariable Long id
      
 
        ) {
            
            String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
            User user = usuarioservice.getUserByEmail(email);
            Set<Producto> productos = user.getVendedor().getProductos();
            ValidationProductService.validateProductsIsNotEmpty(productos);
            Optional<Producto> producto = productos.stream().filter((product) -> product.getId() == id ).findFirst();
            ValidationProductService.validateProductsIsNotPresent(producto);

            Producto producto2 =producto.get();
            Long count = productService.getCounValue(producto2.getId());
            
            ValidationProductService.countValidation(count);
            producto2.setStatus("deleted");

            productService.save(producto2);
            return ResponseEntity.ok().body("ok");
        }
 

        @GetMapping("/getVendedor/{id}")
        @PreAuthorize("hasRole('ROLE_VENDEDOR')")
        public ResponseEntity<?> getProduct(
            HttpServletRequest request, @PathVariable Long id
            ) {       
                String email = SetAuthUser.getUsernameDeserialize(request, jwtService);
                User user = usuarioservice.getUserByEmail(email);
                Set<Producto> productos = user.getVendedor().getProductos();
                // Producto producto = productService.getById(id);
                ValidationProductService.validateProductsIsNotEmpty(productos);
    
                Optional<Producto> producto = productos.stream().filter((product) -> product.getId() == id ).findFirst();
                ValidationProductService.validateProductsIsNotPresent(producto);
                return ResponseEntity.ok().body(producto.get());
            }

        // registrar actividad del usuario y con esos datos sacar los productos relacionados 
        // tarea mañana
        // un endpoint ultimas ofertas
        // un endpoint mejores ofertas
        // un endpoint nuevos productos


        // un endpoint mas vistos productos (relevantes)
        // un endpoint de productos vistos similares vistos
        // un endpoint de productos que posiblemente le interese (tendencias del dia)

        // Machine learning?

        // nota: la consulta sera para hacerlo lo mas efiente posible, se colocara el nombre, el precio, el descuento (si es que tiene) y sus atributos de forma simplificada
        // solo traera el producto sin subproductos, cuando el usuario seleccione una opcion traera los datos relacionadas al icono que selecciono, nota: si solo selecciona uno
        // debera motrar los posibles productos disponibles en esa opcion en la botonera de opciones y no debera ocultar ningun atrinuto simplemente los colocara de un color mas 
        // opaco sin la necesidad de deshabilitarlo
        // 

        
    
 
    @GetMapping("/image/{path}")
    public ResponseEntity<Resource> getImage(@PathVariable String path, HttpServletRequest request) throws IOException {
        // Construir la ruta donde se guarda la imagen
        String directory = uploadDir + File.separator + "product-images";
        Path dirPath = Paths.get(directory);
        // Buscar archivo que comience con el userId
        try (Stream<Path> files = Files.list(dirPath)) {
            Optional<Path> imageFile = files
                .filter(file -> file.getFileName().toString().startsWith(path))
                .findFirst();
                
            if (imageFile.isPresent()) {
                Path file = imageFile.get();
                Resource resource = new UrlResource(file.toUri());
                
                // Detectar el tipo de contenido (MIME type)
                String contentType = Files.probeContentType(file);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }
                
                return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFileName().toString() + "\"")
                    .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    }
}
