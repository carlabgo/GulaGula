package com.gulagula.gulagula.repositorios;

import com.gulagula.gulagula.entidades.Ingrediente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

@Repository
public interface IngredienteRepositorio extends JpaRepository<Ingrediente, String> {

    @Query("SELECT i FROM Ingrediente i ORDER BY i.nombre ASC")
    public List<Ingrediente> ingredientesOrdenados();
}
