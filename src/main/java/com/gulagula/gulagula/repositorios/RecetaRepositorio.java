package com.gulagula.gulagula.repositorios;

import com.gulagula.gulagula.entidades.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecetaRepositorio extends JpaRepository<Receta, String> {

}
