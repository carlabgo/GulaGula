package com.gulagula.gulagula.repositorios;

import com.gulagula.gulagula.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
    
     @Query("SELECT p FROM Usuario p WHERE p.email LIKE :email")
    public Usuario buscarporEmail (@Param("email") String email);    
    
}
