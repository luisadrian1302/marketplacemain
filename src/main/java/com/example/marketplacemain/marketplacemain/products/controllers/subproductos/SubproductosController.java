package com.example.marketplacemain.marketplacemain.products.controllers.subproductos;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.marketplacemain.marketplacemain.products.DTO.AtributoDTO;
import com.example.marketplacemain.marketplacemain.products.entitites.Producto;
import com.example.marketplacemain.marketplacemain.products.entitites.SubProducto;
import com.example.marketplacemain.marketplacemain.products.services.CaracteristicaService;
import com.example.marketplacemain.marketplacemain.products.services.ProductService;
import com.example.marketplacemain.marketplacemain.products.services.SubproductoService;
import com.example.marketplacemain.marketplacemain.products.services.validations.ValidationProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RequestMapping("api/SubProduct")
@RestController
public class SubproductosController {
   


    @Value("${app.upload.dir:${user.home}}")
    private String uploadDir;

    @Autowired
    private SubproductoService subproductoService;

    
    @Autowired
    private ProductService productoService;

    @Autowired
    private CaracteristicaService caracteristicaService;



    // public
    @GetMapping("/image/{path}")
    public ResponseEntity<Resource> getImage(@PathVariable String path, HttpServletRequest request) throws IOException {

        System.out.println(uploadDir);
        // Construir la ruta donde se guarda la imagen
        String directory = uploadDir + File.separator + "subproducto";
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

    @GetMapping("/public/findByUltimasOfertas")
        public ResponseEntity<?> findByUltimasOfertas( @RequestParam Long limit ) {   
                return ResponseEntity.ok().body(subproductoService.getByLastOfter(limit));
    }

    @GetMapping("/public/findBymejoresOfertas")
        public ResponseEntity<?> mejoresOfertas( @RequestParam Long limit ) {   
                return ResponseEntity.ok().body(subproductoService.mejoresOfertas(limit));
    }

    @GetMapping("/public/findByultimosProductos")
        public ResponseEntity<?> ultimosProductos( @RequestParam Long limit ) {   
                return ResponseEntity.ok().body(subproductoService.ultimosProductos(limit));
    }

    @GetMapping("/public/getById/{id}")
    public ResponseEntity<?> getByIdPublic( @PathVariable Long id ) {   
            return ResponseEntity.ok().body(subproductoService.getById(id));
    }

    @PostMapping("/public/changeSubproduct/{id}")
    public ResponseEntity<?> getByIdPublic( @PathVariable Long id , @RequestParam("caractertistica") String caractertistica, @RequestParam("posId") int posId) {
        
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                List<AtributoDTO> atributos = objectMapper.readValue(caractertistica, new TypeReference<List<AtributoDTO>>() {}); //aqui guardamos atributo y valor y tendra una caracteristica
  
                Long idSubproduct = (long) 0;
                // System.out.println("posValue: " + posId);
                // System.out.println("propiedad: " + atributos.get(posId).getPropiedad());
                // System.out.println("valor: " +atributos.get(posId).getValor());
                // System.out.println("tipo: " + atributos.get(posId).getTipo());

                atributos.forEach(attr -> System.out.println(attr.getTipo()));
                // System.out.println(atributos.size());
                if (atributos.size() == 2) {
                    // logica de dos canales
                    Long nuevoValor = caracteristicaService.getIDSubproductTwoChanel(id, 
                    atributos.get(0).getPropiedad(), atributos.get(0).getValor(),atributos.get(0).getTipo(),
                    atributos.get(1).getPropiedad(), atributos.get(1).getValor(),atributos.get(1).getTipo());

                    
                    if (nuevoValor > 0) {
                        return ResponseEntity.ok().body(nuevoValor);
                    }

                    Long nuevoValor2 = caracteristicaService.getIDSubproductOneChanel(
                        id, 
                        atributos.get(posId).getPropiedad(), atributos.get(posId).getValor(),atributos.get(posId).getTipo()
                    );
                    if (nuevoValor2 > 0) {
                        return ResponseEntity.ok().body(nuevoValor2);
                    }

                    return ResponseEntity.notFound().build();

                    // en caso de que el valor este vacio vamos a obtener el primer subproducto con esa caractertistica    
                }
                
                // triple canal
                if (atributos.size() == 3) {
                    // logica de dos canales
                    Long nuevoValor = caracteristicaService.getIDSubproductThreeChanel(id, 
                    atributos.get(0).getPropiedad(), atributos.get(0).getValor(),atributos.get(0).getTipo(),
                    atributos.get(1).getPropiedad(), atributos.get(1).getValor(),atributos.get(1).getTipo(),
                    atributos.get(2).getPropiedad(), atributos.get(2).getValor(),atributos.get(2).getTipo()
                    );

                    
                    if (nuevoValor > 0) {
                        return ResponseEntity.ok().body(nuevoValor);
                    }

                    Long nuevoValor2 = caracteristicaService.getIDSubproductOneChanel(
                        id, 
                        atributos.get(posId).getPropiedad(), atributos.get(posId).getValor(),atributos.get(posId).getTipo()
                    );
                    if (nuevoValor2 > 0) {
                        return ResponseEntity.ok().body(nuevoValor2);
                    }

                    return ResponseEntity.notFound().build();

                    // en caso de que el valor este vacio vamos a obtener el primer subproducto con esa caractertistica    
                }
                

                return ResponseEntity.ok().body(idSubproduct);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                // TODO: handle exception
                return ResponseEntity.badRequest().body("");

            }
            
    }

    
    

}
