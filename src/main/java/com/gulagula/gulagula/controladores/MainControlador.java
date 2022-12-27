package com.gulagula.gulagula.controladores;

import com.gulagula.gulagula.entidades.Usuario;
import com.gulagula.gulagula.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
//@RequestMapping("/")
public class MainControlador {

    private final UsuarioServicio usuarioServicio;

    @Autowired
    public MainControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @GetMapping
    public String home() {
        return "/index";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap model, @RequestParam(required = false) String logout) {
        if (error != null) {
            model.put("error", "Usuario o Clave incorrecta");
        }
        if (logout != null) {
            model.put("logout", "Ha salido correctamente.");
        }
        return ("/login");
    }

    @GetMapping("/registro")
    public String mostrarFormulario(ModelMap model, @RequestParam(required = false) String id, RedirectAttributes attr) {
        if (id == null) {
            model.addAttribute("usuario", new Usuario());
            return "usuario/usuario-form";
        } else {
            try {
                Usuario usuario = usuarioServicio.buscarUsId(id);
                model.addAttribute("usuario", usuario);
                return "usuario/usuario-form";
            } catch (Exception e) {
                attr.addFlashAttribute("errorMsj", e.getMessage());
                return "redirect:/registro";
            }
        }
    }

    @PostMapping("/registro")
    public String procesarFormulario(@ModelAttribute Usuario usuario, ModelMap model) {
        try {
            usuarioServicio.guardarUsuario(usuario);
        } catch (Exception e) {
            System.out.println("error form");
            model.addAttribute("error", e.getMessage());
            System.out.println(e.getMessage());
            return "usuario/usuario-form";
        }
        return "redirect:/login";
    }

}
