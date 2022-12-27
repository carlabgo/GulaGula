package com.gulagula.gulagula.controladores;

import com.gulagula.gulagula.entidades.Ingrediente;
import com.gulagula.gulagula.entidades.Receta;
import com.gulagula.gulagula.servicios.IngredienteServicio;
import com.gulagula.gulagula.servicios.RecetaServicio;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/buscar")
public class BusquedaControlador {

    private final RecetaServicio recetaServicio;
    private final IngredienteServicio ingredienteServicio;

    public BusquedaControlador(RecetaServicio recetaServicio, IngredienteServicio ingredienteServicio) {
        this.recetaServicio = recetaServicio;
        this.ingredienteServicio = ingredienteServicio;
    }

    @GetMapping
    public String mostrarIngredientes(ModelMap model) {
        List<Ingrediente> ingredientes = ingredienteServicio.ingredientesOrdenados();
        model.addAttribute("ingredientes", ingredientes);
        return "/receta/buscar-receta";
    }

    @PostMapping
    public String procesarIngredientes(ModelMap model, @RequestParam(required = false) List<Ingrediente> ingredientesId) {
        try {
            List<Receta> recetas = recetaServicio.listarRecetasPorIngredientes(ingredientesId);
            model.addAttribute("recetas", recetas);
            List<Receta> recetasMenos3 = recetaServicio.listarRecetasPorIngredientesMenos3(ingredientesId);
            model.addAttribute("recetasMenos3", recetasMenos3);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            System.out.println(e.getMessage());
            return "/receta/buscar-receta";
        }
        return "/receta/receta-buscada";
    }

    @GetMapping("/receta-seleccionada")
    public String recetaSeleccionada(@RequestParam(value = "id") String id, ModelMap model) {
        try {
            Receta receta = recetaServicio.buscarReceta(id);
            model.put("receta", receta);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "receta/receta-buscada";
        }
        return "receta/receta-seleccionada";
    }

}
