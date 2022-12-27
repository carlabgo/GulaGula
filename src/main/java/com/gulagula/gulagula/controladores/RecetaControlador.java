package com.gulagula.gulagula.controladores;

import com.gulagula.gulagula.entidades.Ingrediente;
import com.gulagula.gulagula.entidades.Receta;
import com.gulagula.gulagula.servicios.IngredienteServicio;
import com.gulagula.gulagula.servicios.RecetaServicio;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/receta")
public class RecetaControlador {

    private final RecetaServicio recetaServicio;
    private final IngredienteServicio ingredienteServicio;

    public RecetaControlador(RecetaServicio recetaServicio, IngredienteServicio ingredienteServicio) {
        this.recetaServicio = recetaServicio;
        this.ingredienteServicio = ingredienteServicio;
    }

    @GetMapping
    public String listarReceta(ModelMap model) {
        List<Receta> receta = recetaServicio.listarRecetas();
        model.addAttribute("receta", receta);
        return "receta/lista-receta";
    }

    @GetMapping("/form")
    public String mostrarFormulario(ModelMap model) {
        List<Ingrediente> ingredientes = ingredienteServicio.ingredientesOrdenados();
        model.addAttribute("ingredientes", ingredientes);
        model.addAttribute("receta", new Receta());
        return "receta/receta-form";
    }

    @PostMapping("/form")
    public String procesarFormulario(@ModelAttribute Receta receta, ModelMap model, @RequestParam(required = false) MultipartFile archivo, @RequestParam(required = false) List<Ingrediente> ingredientesId) {
        try {
            recetaServicio.guardarReceta(receta, archivo);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            System.out.println(e.getMessage());
            return "receta/receta-form";
        }
        return "redirect:/receta";
    }

    @GetMapping("/editar")
    public String modificar(@RequestParam(value = "id") String id, ModelMap model) {
        try {
            Receta receta = recetaServicio.buscarReceta(id);
            List<Ingrediente> ingredientes = ingredienteServicio.ingredientesOrdenados();
            model.addAttribute("ingredientes", ingredientes);
            model.put("receta", receta);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "receta/editar-receta";
        }
        return "receta/editar-receta";
    }

    @PostMapping("/editar")
    public String modificarFormulario(@ModelAttribute Receta receta, @RequestParam(required = false) MultipartFile archivo, ModelMap model) {
        try {
            recetaServicio.editarReceta(receta, archivo);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            System.out.println(e.getMessage());
            return "receta/editar-receta";
        }
        return "redirect:/receta";
    }

}
