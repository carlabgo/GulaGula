package com.gulagula.gulagula.controladores;

import com.gulagula.gulagula.entidades.Usuario;
import com.gulagula.gulagula.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;

    @Autowired
    public UsuarioControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String listarUsuarios(ModelMap model) {
        List<Usuario> usuarios = usuarioServicio.listaUs();
        model.addAttribute("usuarios", usuarios);
        return "usuario/lista-usuario";
    }

    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id, ModelMap model) {
        try {
            usuarioServicio.activarUs(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "usuario";
        }
        return "redirect:/usuario";
    }

    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id, ModelMap model) {
        try {
            usuarioServicio.desactivarUs(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "usuario";
        }
        return "redirect:/usuario";
    }

}
