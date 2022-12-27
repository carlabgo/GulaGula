package com.gulagula.gulagula.servicios;

import com.gulagula.gulagula.entidades.Ingrediente;
import com.gulagula.gulagula.repositorios.IngredienteRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IngredienteServicio {

    private IngredienteRepositorio ingredienteRepositorio;

    @Autowired
    public void IngredienteRepositorio(IngredienteRepositorio ingredienteRepositorio) {
        this.ingredienteRepositorio = ingredienteRepositorio;
    }

    @Transactional
    public Ingrediente guardarIngrediente(Ingrediente ingrediente) throws Exception {
        validacion(ingrediente);
        ingredienteRepositorio.save(ingrediente);
        return ingrediente;
    }

    @Transactional
    public void editarIngrediente(Ingrediente ingrediente) throws Exception {
        validacion(ingrediente);
        buscarIngrediente(ingrediente.getId());
        ingredienteRepositorio.save(ingrediente);
    }

    @Transactional
    public List<Ingrediente> listarIngredientes() {
        return ingredienteRepositorio.findAll();
    }

    @Transactional
    public Ingrediente buscarIngrediente(String id) throws Exception {

        Optional<Ingrediente> resp = ingredienteRepositorio.findById(id);
        if (resp.isPresent()) {
            return resp.get();
        } else {
            throw new Exception("El Ingrediente no se encuentra");
        }
    }

    @Transactional
    public List buscarPorNombre(String nombre) throws Exception {

        List<Ingrediente> ing = ingredienteRepositorio.findAll();
        if (ing.contains(Ingrediente.class.cast(nombre))) {
            ing = new ArrayList();
            return ing;
        } else {
            throw new Exception("El Ingrediente no se encuentra");
        }
    }

    @Transactional
    public void validacion(Ingrediente ingrediente) throws Exception {

        if (ingrediente == null) {
            throw new Exception("los ingredientes no pueden estar vacios");
        }
//   
        if (ingrediente.getNombre() == null || ingrediente.getNombre().isEmpty()) {
            throw new Exception("El nombre del ingrediente no puede estar vacio");
        }

    }

    @Transactional
    public Ingrediente getOne(String id) {
        return ingredienteRepositorio.getOne(id);
    }

    @Transactional
    public List<Ingrediente> ingredientesOrdenados() {
        return ingredienteRepositorio.ingredientesOrdenados();
    }
}
