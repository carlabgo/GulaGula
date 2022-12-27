package com.gulagula.gulagula.servicios;

import com.gulagula.gulagula.entidades.Imagen;
import com.gulagula.gulagula.repositorios.ImagenRepositorio;
import java.io.IOException;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagenServicio {

    @Autowired
    private ImagenRepositorio imagenRepositorio;

    @Transactional
    public Imagen guardar(MultipartFile archivo) throws Exception {
        if (archivo != null && !archivo.isEmpty()) {
            try {

                Imagen imagen = new Imagen();
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());

                return imagenRepositorio.save(imagen);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return null;

    }

    @Transactional
    public Imagen actualizar(String idImagen, MultipartFile archivo) throws Exception {

        if (archivo != null) {
            try {
                Imagen imagen = new Imagen();
                if (idImagen != null) {
                    Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);
                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());

                return imagenRepositorio.save(imagen);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

}
