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

    @PostMapping("/actualizarProducto")
    @PreAuthorize("hasRole('ROLE_VENDEDOR')")

    public ResponseEntity<?> uploadImage(
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
                Subcategoria subcategoria = subcategoriasService.getCategoria( subcategoriaid);

                if (subcategoria == null) {
                    Map<String, String> valuesMap = new HashMap<>();
                    valuesMap.put("message", "No se pudo crear este producto, no se encontró el id de la subcategoria");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);

                }

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

                // guardar la url
                

               

                return ResponseEntity.ok().body(mensaje);
        }catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No se pudo subir la imagen: " + e.getMessage());
        }
    

    }





    @PostMapping("/actualizarProducto2")
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
            if (!productoOpt.isPresent()) {
                Map<String, String> valuesMap = new HashMap<>();
                valuesMap.put("message", "No se pudo modificar este producto");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);

            }
            Producto producto = productoOpt.get();

               // // verificar si existe esa subcaategoria
            Subcategoria subcategoria = subcategoriasService.getCategoria( subcategoriaid);

           

            if (subcategoria == null) {
                Map<String, String> valuesMap = new HashMap<>();
                valuesMap.put("message", "No se pudo crear este producto, no se encontró el id de la subcategoria");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);

            }

            producto.setTitular(titular);
            producto.setDescripcionGeneral(descripcion);
            producto.setSubcategoria(subcategoria);

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

            // obtener producto por id

            // Producto producto = productService.getById(id);
            if (productos == null) {
                
                Map<String, String> valuesMap = new HashMap<>();
                valuesMap.put("message", "No se encontro ese producto");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(valuesMap);
            }

            Optional<Producto> producto = productos.stream().filter((product) -> product.getId() == id ).findFirst();

            if (!producto.isPresent()) {
                
                Map<String, String> valuesMap = new HashMap<>();
                valuesMap.put("message", "No se encontro ese producto");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(valuesMap);
            }

           

            Producto producto2 =producto.get();
            Long count = productService.getCounValue(producto2.getId());
            System.out.println(count);
            if (count> 0) {
                Map<String, String> valuesMap = new HashMap<>();
                valuesMap.put("message", "Hay subproductos dentro del producto, elimine todos los subproductos para eliminar el producto");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(valuesMap);
            }
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
    
                // obtener producto por id
    
                // Producto producto = productService.getById(id);
                if (productos == null) {
                    
                    Map<String, String> valuesMap = new HashMap<>();
                    valuesMap.put("message", "No se encontro ese producto");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(valuesMap);
                }
    
                Optional<Producto> producto = productos.stream().filter((product) -> product.getId() == id ).findFirst();
    
                if (!producto.isPresent()) {
                    
                    Map<String, String> valuesMap = new HashMap<>();
                    valuesMap.put("message", "No se encontro ese producto");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(valuesMap);
                }
                return ResponseEntity.ok().body(producto.get());
            }
    
    

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
