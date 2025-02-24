package com.example.marketplacemain.marketplacemain.products.images;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;


public class ImageHelper {

     static public String uploadImage(String uploadDir, String email, MultipartFile file, String pathName) throws IOException {

         // Crear directorio si no existe
                String directory = uploadDir + File.separator + pathName;
                Path uploadPath = Paths.get(directory);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Generar nombre único para el archivo
                String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
                String newFileName = email + "." + fileExtension;

                // Ruta completa del archivo
                Path filePath = uploadPath.resolve(newFileName);

                // Guardar archivo
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // Aquí podrías guardar la ruta en la base de datos
                String imageUrl = "/"+ pathName+ "/" + newFileName;
                return newFileName;
     }

}
