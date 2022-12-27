package com.gulagula.gulagula.servicios;

import com.gulagula.gulagula.entidades.Usuario;
import com.gulagula.gulagula.enumeradores.Rols;
import com.gulagula.gulagula.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServicio implements UserDetailsService {

    public UsuarioRepositorio usuarioRepositorio;

    @Autowired
    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Transactional
    public void guardarUsuario(Usuario usuario) throws Exception {
        validar(usuario);
        activateIfNew(usuario);
        usuario.setClave(new BCryptPasswordEncoder().encode(usuario.getClave()));
        usuarioRepositorio.save(usuario);
    }

    @Transactional
    public void modificar(Usuario usuario) throws Exception {
        validar(usuario);
        Optional<Usuario> respuesta = usuarioRepositorio.findById(usuario.getId());
        if (respuesta.isPresent()) {
            usuarioRepositorio.save(respuesta.get());
        } else {
            throw new Exception("No se encontró el usuario solicitado");
        }
    }

    @Transactional
    public Usuario buscarUsId(String id) throws Exception {
        return usuarioRepositorio.findById(id).orElseThrow(() -> new Exception("Usuario no encontrado"));
    }

    @Transactional
    public List<Usuario> listaUs() {
        return usuarioRepositorio.findAll();
    }

    @Transactional
    public void activarUs(String id) throws Exception {
        Usuario usuario = this.buscarUsId(id);
        usuario.setAlta(true);
    }

    @Transactional
    public void desactivarUs(String id) throws Exception {
        Usuario usuario = this.buscarUsId(id);
        usuario.setAlta(false);
    }

    @Transactional
    public void cambiarRol(String id) throws Exception {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            if (usuario.getRol().equals(Rols.USER)) {
                usuario.setRol(Rols.ADMIN);
            } else if (usuario.getRol().equals(Rols.ADMIN)) {
                usuario.setRol(Rols.USER);
            }
        }
    }

    @Transactional
    public void validar(Usuario usuario) throws Exception {

        if (usuario.getNombre() == null || usuario.getNombre().isEmpty()) {
            throw new Exception("El nombre no puede estar vacio");
        }
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new Exception("El email no puede estar vacio");
        }
        if (usuario.getClave() == null || usuario.getClave().isEmpty()) {
            throw new Exception("La clave no puede estar vacia");
        }
    }

    private void activateIfNew(Usuario usuario) {
        if (usuario.getAlta() == null) {
            usuario.setAlta(true);
            usuario.setRol(Rols.USER);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarporEmail(email);
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();

            //Creo una lista de permisos! 
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + usuario.getRol());
            permisos.add(p1);

            //Esto me permite guardar el OBJETO USUARIO LOG, para luego ser utilizado
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", usuario); // llave + valor

            User user = new User(usuario.getEmail(), usuario.getClave(), permisos);

            return user;

        } else {
            return null;
        }
    }

}
