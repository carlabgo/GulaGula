package com.gulagula.gulagula.controladores;

import com.gulagula.gulagula.entidades.Receta;
import com.gulagula.gulagula.servicios.RecetaServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imagen")  //Ver ruta de la imagen//
public class ImagenControlador {

    @Autowired
    private RecetaServicio recetaServicio;

//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USUARIO')")
    @GetMapping("/receta/{id}")
    public ResponseEntity<byte[]> imagenReceta(@PathVariable String id) throws Exception {
        Receta receta = recetaServicio.buscarReceta(id);
        System.out.println("entra a imagen receta");
        if (receta == null) {
            throw new Exception("No se puede asignar el archivo a ese ID");
        }

        try {
            if (receta.getImagen() == null) {
                throw new Exception("la Receta no tiene una foto asignada.");
            }
            byte[] imagen = receta.getImagen().getContenido();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentType(MediaType.IMAGE_PNG);

            return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(ImagenControlador.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
