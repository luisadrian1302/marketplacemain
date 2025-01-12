package com.example.marketplacemain.marketplacemain.autenticacion.controllador;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.marketplacemain.marketplacemain.autenticacion.DTO.InformacionGeneralDTO;
import com.example.marketplacemain.marketplacemain.autenticacion.DTO.RegisterUserDTO;
import com.example.marketplacemain.marketplacemain.autenticacion.DTO.UserChangePasswordDTO;
import com.example.marketplacemain.marketplacemain.autenticacion.DTO.UserImageDTO;
import com.example.marketplacemain.marketplacemain.autenticacion.DTO.VerifyDTO;
import com.example.marketplacemain.marketplacemain.autenticacion.entities.User;
import com.example.marketplacemain.marketplacemain.autenticacion.entities.Vendedor;
import com.example.marketplacemain.marketplacemain.autenticacion.services.JwtService;
import com.example.marketplacemain.marketplacemain.autenticacion.services.UserService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/users")
public class UsuarioController {

    @Autowired
    private UserService service;

    @Autowired
    private JwtService jwtService;

    @Value("${app.upload.dir:${user.home}}")
    private String uploadDir;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody RegisterUserDTO user, BindingResult result) {

        if (result.hasFieldErrors()) {
            return validation(result);
        }
        User userCast = new User();
        userCast.setEmail(user.getEmail());
        userCast.setNombre(user.getName());
        userCast.setPassword(user.getPassword());

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(userCast));
    }

    @PostMapping("/updatedPassword")
    public ResponseEntity<?> updatePassword(HttpServletRequest request, @Valid @RequestBody UserChangePasswordDTO user, BindingResult result) {

        if (result.hasFieldErrors()) {
            return validation(result);
        }

        String username = getUsernameDeserialize(request);

        User newUserpassword = service.changePassword(username, user.getOldpassword(), user.getNewpassword());


        if (newUserpassword == null) {
            Map<String, String> valuesMap = new HashMap<>();

            valuesMap.put("message", "No se pudo actualizar este usuario");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);

        }

        return ResponseEntity.status(HttpStatus.OK).body("ok");



    }


    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterUserDTO user, BindingResult result) {

        return create(user, result);
    }

    protected String getUsernameDeserialize(HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");
        String token = "your.jwt.token";
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7); // Quita "Bearer " del encabezado
        }
        String username = jwtService.extractUsername(token);

        return username;
    }

    @PostMapping("/informacionGeneral")
    public ResponseEntity<?> informacionGeneral(HttpServletRequest request,
            @Valid @RequestBody InformacionGeneralDTO user, BindingResult result) {

        if (result.hasFieldErrors()) {
            return validation(result);
        }

        String username = getUsernameDeserialize(request);

        if (!username.equals(user.getEmail())) {

            Map<String, String> valuesMap = new HashMap<>();

            valuesMap.put("message", "error al verificar el usuario");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);

        }

        User userUpdated = service.updateUserGeneral(user);

        if (userUpdated == null) {
            Map<String, String> valuesMap = new HashMap<>();

            valuesMap.put("message", "No se pudo actualizar este usuario");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);

        }

        return ResponseEntity.status(HttpStatus.OK).body(userUpdated);

    }

    @PostMapping("/verificar")
    public ResponseEntity<?> verificar(@Valid @RequestBody VerifyDTO verify, BindingResult result) {

        if (result.hasFieldErrors()) {
            return validation(result);
        }

        User user = service.verificarUser(verify);

        if (user == null) {
            Map<String, String> valuesMap = new HashMap<>();

            valuesMap.put("message", "error al verificar el usuario");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(valuesMap);
        }

        return ResponseEntity.status(HttpStatus.OK).body(user);

    }

    @GetMapping("/example")
    public ResponseEntity<?> example() {

        return ResponseEntity.status(HttpStatus.CREATED).body("222");

    }

    @GetMapping("/example2")
    public ResponseEntity<?> example2(HttpServletRequest request) {
        String username = getUsernameDeserialize(request);

        User user = service.getUserByEmail(username);
        
        Vendedor vendedor= new Vendedor();

        vendedor.setBanned(false);
        vendedor.setStatus(true);
        user.setVendedor(vendedor);

        service.save2(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("prueba de vendedor creada, esta no es una funcion definitiva");

    }

    @PreAuthorize("hasRole('ROLE_VENDEDOR')")
    @GetMapping("/isVendedor")
    public ResponseEntity<?> exavendedor() {

        return ResponseEntity.status(HttpStatus.CREATED).body("222");

    }

    @GetMapping("/getTokenDeserialize")
    public ResponseEntity<?> decode(HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        String token = "your.jwt.token";

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7); // Quita "Bearer " del encabezado

        }
        String username = jwtService.extractUsername(token);

        User user = service.getUserByEmail(username);

        // Decode without verification (for debugging)

        return ResponseEntity.status(HttpStatus.CREATED).body(user);

    }

    @PostMapping("/uploadImage")
    public ResponseEntity<?> uploadImage(
            HttpServletRequest request,

            @RequestParam("image") MultipartFile file) {
        try {

            String username = getUsernameDeserialize(request);

            // Crear directorio si no existe
            String directory = uploadDir + File.separator + "user-images";
            Path uploadPath = Paths.get(directory);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generar nombre único para el archivo
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String newFileName = username + "." + fileExtension;

            // Ruta completa del archivo
            Path filePath = uploadPath.resolve(newFileName);

            // Guardar archivo
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Aquí podrías guardar la ruta en la base de datos
            String imageUrl = "/user-images/" + newFileName;

            // actualizar imagen del usuario

            UserImageDTO userImageDTO = new UserImageDTO();

            userImageDTO.setUrlImage(imageUrl);
            userImageDTO.setUsername(username);

            User userUpdated = service.updateUserImage(userImageDTO);

            if (userUpdated == null) {
                Map<String, String> valuesMap = new HashMap<>();

                // Files.delete(filePath.get());

                valuesMap.put("message", "No se pudo actualizar este usuario");
                return ResponseEntity.badRequest().body(valuesMap);

            }

            return ResponseEntity.ok()
                    .body(imageUrl);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No se pudo subir la imagen: " + e.getMessage());
        }
    }


    @GetMapping("/image/{path}")
    public ResponseEntity<Resource> getImage(@PathVariable String path, HttpServletRequest request) throws IOException {
        // Construir la ruta donde se guarda la imagen
        String directory = uploadDir + File.separator + "user-images";
        Path dirPath = Paths.get(directory);
        
        String username = getUsernameDeserialize(request);

        
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


    // @GetMapping("/image/{path}")
    // public ResponseEntity<byte[]> getImageAsBytes(@PathVariable String path) throws IOException {
    //     String directory = uploadDir + File.separator + "user-images";
    //     Path dirPath = Paths.get(directory);
        
    //     try (Stream<Path> files = Files.list(dirPath)) {
    //         Optional<Path> imageFile = files
    //             .filter(file -> file.getFileName().toString().startsWith(path))
    //             .findFirst();
                
    //         if (imageFile.isPresent()) {
    //             Path file = imageFile.get();
    //             byte[] imageBytes = Files.readAllBytes(file);
    //             String contentType = Files.probeContentType(file);
                
    //             return ResponseEntity.ok()
    //                 .contentType(MediaType.parseMediaType(contentType))
    //                 .body(imageBytes);
    //         } else {
    //             return ResponseEntity.notFound().build();
    //         }
    //     }
    // }

}
