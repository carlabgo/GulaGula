package com.gulagula.gulagula.controladores;

import com.gulagula.gulagula.entidades.Ingrediente;
import com.gulagula.gulagula.servicios.IngredienteServicio;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ingrediente")

public class IngredienteControlador {

    private final IngredienteServicio ingredienteServicio;

    public IngredienteControlador(IngredienteServicio ingredienteServicio) {
        this.ingredienteServicio = ingredienteServicio;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping
    public String listarIngrediente(ModelMap model) {
        List<Ingrediente> ingrediente = ingredienteServicio.ingredientesOrdenados();
        model.addAttribute("ingrediente", ingrediente);
        return "ingrediente/lista-ingrediente";
    }

    @GetMapping("/form")
    public String mostrarFormulario(ModelMap model) {
        model.addAttribute("ingrediente", new Ingrediente());
        return "ingrediente/ingrediente-form";
    }

    @PostMapping("/form")
    public String procesarFormulario(@ModelAttribute Ingrediente ingrediente, ModelMap model) {
        try {
            ingredienteServicio.guardarIngrediente(ingrediente);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            System.out.println(e.getMessage());
            return "ingrediente/ingrediente-form";
        }
        return "redirect:/ingrediente/form";
    }

    @GetMapping("/editar")
    public String modificar(@RequestParam String id, ModelMap model) {
        try {
            model.put("ingrediente", ingredienteServicio.buscarIngrediente(id));
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "ingrediente/editar-ingrediente";
        }
        return "ingrediente/editar-ingrediente";
    }

    @PostMapping("/editar")
    public String modificarFormulario(@ModelAttribute Ingrediente ingrediente, ModelMap model) {
        try {
            ingredienteServicio.editarIngrediente(ingrediente);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            System.out.println(e.getMessage());
            return "ingrediente/editar-ingrediente";
        }
        return "redirect:/ingrediente";
    }
}
