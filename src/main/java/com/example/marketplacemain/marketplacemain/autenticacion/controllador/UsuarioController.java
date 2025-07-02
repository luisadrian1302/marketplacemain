package com.example.marketplacemain.marketplacemain.autenticacion.controllador;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.example.marketplacemain.marketplacemain.autenticacion.entities.DocumentacionCliente;
import com.example.marketplacemain.marketplacemain.autenticacion.entities.Employee;
import com.example.marketplacemain.marketplacemain.autenticacion.entities.User;
import com.example.marketplacemain.marketplacemain.autenticacion.entities.Vendedor;
import com.example.marketplacemain.marketplacemain.autenticacion.security.SetAuthUser;
import com.example.marketplacemain.marketplacemain.autenticacion.services.DocumentacionClientService;
import com.example.marketplacemain.marketplacemain.autenticacion.services.EmployeeService;
import com.example.marketplacemain.marketplacemain.autenticacion.services.JwtService;
import com.example.marketplacemain.marketplacemain.autenticacion.services.UserService;
import com.example.marketplacemain.marketplacemain.autenticacion.validation.ValidationDocumentClient;
import com.example.marketplacemain.marketplacemain.websockets.DTO.NotificacionDocumenttDTO;
import com.example.marketplacemain.marketplacemain.websockets.controllers.NotificationController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/users")
public class UsuarioController {

    @Autowired
    private UserService service;

    @Autowired
    private DocumentacionClientService dcService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JwtService jwtService;

    @Value("${app.upload.dir:${user.home}}")
    private String uploadDir;

    @Autowired
    private NotificationController webSocketController;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody RegisterUserDTO user, BindingResult result) {

        if (result.hasFieldErrors()) {
            return validation(result);
        }
        User userCast = new User();
        userCast.setEmail(user.getEmail());
        userCast.setNombre(user.getName());
        userCast.setPassword(user.getPassword());
        service.save(userCast);

        return ResponseEntity.status(HttpStatus.CREATED).body("ok");
    }

    @PostMapping("/updatedPassword")
    public ResponseEntity<?> updatePassword(HttpServletRequest request, @Valid @RequestBody UserChangePasswordDTO user, BindingResult result) {

        if (result.hasFieldErrors()) {
            return validation(result);
        }


        String username = SetAuthUser.getUsernameDeserialize(request, jwtService);

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

    
    @PostMapping("/informacionGeneral")
    public ResponseEntity<?> informacionGeneral(HttpServletRequest request,
            @Valid @RequestBody InformacionGeneralDTO user, BindingResult result) {

        if (result.hasFieldErrors()) {
            return validation(result);
        }
        
        String username = SetAuthUser.getUsernameDeserialize(request, jwtService);


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

   

    // @GetMapping("/example2")
    // public ResponseEntity<?> example2(HttpServletRequest request) {
    //     String username = SetAuthUser.getUsernameDeserialize(request, jwtService);

    //     User user = service.getUserByEmail(username);
        
    //     Vendedor vendedor= new Vendedor();

    //     vendedor.setBanned(false);
    //     vendedor.setStatus(true);
    //     user.setVendedor(vendedor);

    //     service.save2(user);

    //     return ResponseEntity.status(HttpStatus.CREATED).body("prueba de vendedor creada, esta no es una funcion definitiva");

    // }

    @GetMapping("/example")
    public ResponseEntity<?> example(HttpServletRequest request) {
        String username = SetAuthUser.getUsernameDeserialize(request, jwtService);

        User user = service.getUserByEmail(username);

        return ResponseEntity.status(HttpStatus.CREATED).body("ok");

    }


    @GetMapping("/example3")
    public ResponseEntity<?> example3(HttpServletRequest request) {
        String username = SetAuthUser.getUsernameDeserialize(request, jwtService);

        User user = service.getUserByEmail(username);
        
        Employee employee= new Employee();

        employee.setBanned(false);
        employee.setStatus(true);
        user.setEmployee(employee);

        service.save2(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("prueba de empleado creada, esta no es una funcion definitiva");

    }

    @PreAuthorize("hasRole('ROLE_VENDEDOR')")
    @GetMapping("/isVendedor")
    public ResponseEntity<?> exavendedor() {

        return ResponseEntity.status(HttpStatus.CREATED).body("222");

    }


    
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @GetMapping("/isEmployee")
    public ResponseEntity<?> verificarEmpleado() {

        return ResponseEntity.status(HttpStatus.CREATED).body("ok");

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

    @GetMapping("/getDocumentation")
    public ResponseEntity<?> documentacionUser(HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        String token = "your.jwt.token";

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7); // Quita "Bearer " del encabezado

        }
        String username = jwtService.extractUsername(token);

        User user = service.getUserByEmail(username);
        

        List<DocumentacionCliente> documentacionClientes = dcService.getByuse(user);

        if (documentacionClientes.size() > 0) {
            
            DocumentacionCliente documentacionCliente = documentacionClientes.get(0);
            return ResponseEntity.status(HttpStatus.CREATED).body(documentacionCliente);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(documentacionClientes);




        // Decode without verification (for debugging)


    }

    @PostMapping("/uploadPdf")
    public ResponseEntity<?> uploadPdf(
            HttpServletRequest request, 
            @RequestParam("pdf") MultipartFile file, @RequestParam String tipo) {
        try {

            Map<String, Object> response = new HashMap<>();
            String username = SetAuthUser.getUsernameDeserialize(request, jwtService);

            User user = service.getUserByEmail(username);
        

            List<DocumentacionCliente> documentacionClientes = dcService.getByuse(user);
            DocumentacionCliente documentacionCliente = null;

            if (documentacionClientes.size() > 0) {
                
                documentacionCliente = documentacionClientes.get(0);
                
            }else{
                documentacionCliente = new DocumentacionCliente();
                documentacionCliente.setUsuario(user);
                documentacionCliente.setFechaCreacion(LocalDateTime.now());
                
            }
            System.out.println(tipo);

            if ( !tipo.equals("ine") && !tipo.equals("curp") && !tipo.equals("foto_real") ) {
                response.put("message", "Ocurrio un error en el paso de parametros");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); 
            }   

            // Crear directorio si no existe
            String directory = uploadDir + File.separator + "user-documents" + File.separator + tipo;

           

            Path uploadPath = Paths.get(directory);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

             // Validar que sea un archivo PDF
            if (!tipo.equals("foto_real") && !file.getContentType().equals("application/pdf")) {
                response.put("message", "Solo se permiten archivos PDF");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

           
            // Validar que sea un archivo JPG
            if (tipo.equals("foto_real") && !file.getContentType().equals("image/jpeg")) {
                response.put("message", "Solo se permiten archivos en JPG");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }


            // Generar nombre único para el archivo
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String newFileName = username + "." + fileExtension;

            // Ruta completa del archivo
            Path filePath = uploadPath.resolve(newFileName);

            // Guardar archivo
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String imageUrl = "/user-documents/" + tipo + "/" + newFileName;


            if (tipo.equals("ine")) {
                documentacionCliente.setIneFoto(imageUrl);
                documentacionCliente.setStatusIne(2);
            }
            
            if (tipo.equals("curp")) {
                documentacionCliente.setImageCurp(imageUrl);
                documentacionCliente.setStatusImageCurp(2);
            }

            if (tipo.equals("foto_real")) {
                documentacionCliente.setFaceReal(imageUrl);
                documentacionCliente.setStatusfaceReal(2);
            }

            // actualizar imagen del usuario

            UserImageDTO userImageDTO = new UserImageDTO();

            userImageDTO.setUrlImage(imageUrl);
            userImageDTO.setUsername(username);


            dcService.save(documentacionCliente);



            // mandar notificacion a los empleados encargados de validar roles
            List<Employee> employees = employeeService.getAll();

            for(Employee employee: employees){
                NotificacionDocumenttDTO notificacionDocumenttDTO = new NotificacionDocumenttDTO();
                notificacionDocumenttDTO.setDescripcion("el usuario" + user.getNombre() + " " + user.getApellidos() + " a mandado el documento " + tipo + " para validarlo");
                notificacionDocumenttDTO.setEmpleado(employee);
                LocalDate hoy = LocalDate.now(ZoneId.of("America/Mexico_City"));
                notificacionDocumenttDTO.setFechaInicio(hoy);
                notificacionDocumenttDTO.setStatus((byte) 1);
                notificacionDocumenttDTO.setTipo("Correcto");
                notificacionDocumenttDTO.setTipoStatus(tipo);
                notificacionDocumenttDTO.setTitulo("Aprobación de documentos");
                notificacionDocumenttDTO.setUrl("/userDocuments/"+user.getId());
                notificacionDocumenttDTO.setUrlImage(imageUrl);

                webSocketController.sendMessageEmployeeDocument(username, "viewDocument", notificacionDocumenttDTO);


            }

            

            return ResponseEntity.ok()
                    .body(imageUrl);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No se pudo subir la imagen: " + e.getMessage());
        }
    }


    @PostMapping("/uploadImage")
    public ResponseEntity<?> uploadImage(
            HttpServletRequest request,

            @RequestParam("image") MultipartFile file) {
        try {

            String username = SetAuthUser.getUsernameDeserialize(request, jwtService);


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


    // "/user-images/lm0336172@gmail.com.jpg"
    @GetMapping("/user-images/{path}")
    public ResponseEntity<Resource> getImagePublic(@PathVariable String path, HttpServletRequest request) throws IOException {
        // Construir la ruta donde se guarda la imagen
        String directory = uploadDir + File.separator + "user-images";
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
    @GetMapping("/documetation/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> getDocument(@PathVariable Long id, HttpServletRequest request){

        DocumentacionCliente documentacionCliente = dcService.getById(id);
        System.out.println(documentacionCliente);
        ValidationDocumentClient.validateProductsIsNotPresent(documentacionCliente);

        return ResponseEntity.ok().body(documentacionCliente);
    
    }

   

}
