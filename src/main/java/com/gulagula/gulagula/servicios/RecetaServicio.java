package com.gulagula.gulagula.servicios;

import com.gulagula.gulagula.entidades.Imagen;
import com.gulagula.gulagula.entidades.Ingrediente;
import com.gulagula.gulagula.entidades.Receta;
import com.gulagula.gulagula.repositorios.RecetaRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RecetaServicio {

    private RecetaRepositorio recetaRepositorio;
    private IngredienteServicio ingredienteServicio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Autowired
    public RecetaServicio(IngredienteServicio ingredienteServicio) {
        this.ingredienteServicio = ingredienteServicio;
    }

    @Autowired
    public void RecetaRepositorio(RecetaRepositorio recetaRepositorio) {
        this.recetaRepositorio = recetaRepositorio;
    }

    @Transactional
    public void guardarReceta(Receta receta, MultipartFile archivo) throws Exception {
        validacion(receta);
        Imagen imagen = imagenServicio.guardar(archivo);
        receta.setImagen(imagen);

        recetaRepositorio.save(receta);
    }

    @Transactional
    public Receta editarReceta(Receta receta, MultipartFile archivo) throws Exception {
        validacion(receta);
        Imagen imagen = imagenServicio.guardar(archivo);
        receta.setImagen(imagen);
        recetaRepositorio.save(receta);
        return receta;
    }

    @Transactional
    public List<Receta> listarRecetas() {
        return recetaRepositorio.findAll();
    }

    /**
     * Este metodo recibe una lista de ingredientes, busca las recetas que
     * contengan esos ingredientes y devuelve una lista de recetas.-
     *
     *
     * @param ingredientes
     * @return
     */
    @Transactional
    public List<Receta> listarRecetasPorIngredientes(List<Ingrediente> ingredientes) {
        List<Receta> recetaBd = recetaRepositorio.findAll();
        int contadorRecetas = 0;
        int contadorIngredientesEncontrados = 0;
        List<Receta> recetasMas3 = new ArrayList<Receta>();
        do {
            for (int i = 0; i < recetaBd.size(); i++) { // Ingresa con cada una de las recetas de la base de datos
                contadorRecetas++;
                contadorIngredientesEncontrados = 0;
                for (int j = 0; j < ingredientes.size(); j++) { // Ingresa a iterar cada una de los ingredietes ingresados en la pagina

                    for (int k = 0; k < recetaBd.get(i).getIngredientes().size(); k++) { // Ingresa a cada uno de los ingredientes de cada receta

                        if (ingredientes.get(j) == recetaBd.get(i).getIngredientes().get(k)) { //Compara ingrediente ingresado 1 con cada uno de los ingredientes de la receta de arriba si es igual cuenta
                            contadorIngredientesEncontrados++;
                        }
                    } //Cierra for ingredientes de Recetas
                    if (contadorIngredientesEncontrados >= 3) { //si hay 3 o mas ingredientes guarda en la lista a devolver
                        recetasMas3.add(recetaBd.get(i));
                        break;
                    }
                } //Cierra for ingredientes ingresados por parametro
            } //Cierra for receta "Investigar streams"
        } while (contadorRecetas < recetaBd.size());//poner un contador y que de vuelta hasta el tamaño de recetas
        return recetasMas3;
    }

    @Transactional
    public List<Receta> listarRecetasPorIngredientesMenos3(List<Ingrediente> ingredientes) {
        List<Receta> recetaBd = recetaRepositorio.findAll();
        int contadorRecetas = 0;
        int contadorIngredientesEncontrados = 0;
        List<Receta> recetasMenos3 = new ArrayList<Receta>();
        do {
            for (int i = 0; i < recetaBd.size(); i++) { // Ingresa con cada una de las recetas de la base de datos
                contadorRecetas++;
                contadorIngredientesEncontrados = 0;
                for (int j = 0; j < ingredientes.size(); j++) { // Ingresa a iterar cada una de los ingredietes ingresados en la pagina

                    for (int k = 0; k < recetaBd.get(i).getIngredientes().size(); k++) { // Ingresa a cada uno de los ingredientes de cada receta

                        if (ingredientes.get(j) == recetaBd.get(i).getIngredientes().get(k)) { //Compara ingrediente ingresado 1 con cada uno de los ingredientes de la receta de arriba si es igual cuenta
                            contadorIngredientesEncontrados++;
                        }
                    } //Cierra for ingredientes de Recetas
                    if (contadorIngredientesEncontrados > 0 && contadorIngredientesEncontrados < 3) { //si hay menos de e ingredientes guarda en la lista a devolver
                        recetasMenos3.add(recetaBd.get(i));
                        break;
                    }
                } //Cierra for ingredientes ingresados por parametro
            } //Cierra for receta "Investigar streams"
        } while (contadorRecetas < recetaBd.size());//poner un contador y que de vuelta hasta el tamaño de recetas
        return recetasMenos3;
    }

    @Transactional
    public Receta buscarReceta(String id) throws Exception {

        Optional<Receta> resp = recetaRepositorio.findById(id);
        if (resp.isPresent()) {
            return resp.get();
        } else {
            throw new Exception("la receta no se encuentra");
        }
    }

    @Transactional
    public void validacion(Receta receta) throws Exception {

        if (receta.getIngredientes() == null || receta.getIngredientes().isEmpty()) {
            throw new Exception("los ingredientes de la receta no pueden estar vacios");
        }
        if (receta.getTemp() == null) {
            throw new Exception("La temperatura de la receta no puede estar vacia");
        }
        if (receta.getSabor() == null) {
            throw new Exception("El sabor de la receta no puede estar vacio");
        }
        if (receta.getNombre() == null || receta.getNombre().isEmpty()) {
            throw new Exception("El nombre de la receta no puede estar vacio");
        }
        if (receta.getCategoria() == null) {
            throw new Exception("La categoria de la receta no puede estar vacia");
        }
        if (receta.getTipo() == null) {
            throw new Exception("El tipo de la receta no puede estar vacia");
        }
        if (receta.getTiempoDeCoccion() == null || receta.getTiempoDeCoccion().isEmpty()) {
            throw new Exception("El tiempo de cocción de la receta no puede estar vacia");
        }
    }
}
