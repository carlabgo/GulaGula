package com.gulagula.gulagula.entidades;

import com.gulagula.gulagula.enumeradores.Categoria;
import com.gulagula.gulagula.enumeradores.Sabor;
import com.gulagula.gulagula.enumeradores.Temperatura;
import com.gulagula.gulagula.enumeradores.Tipo;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Receta {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String instrucciones;

    @ManyToMany(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    })
    @JoinTable(
            name = "receta_ingrediente",
            joinColumns = {
                @JoinColumn(name = "receta_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "ingrediente_id")}
    )
    private List<Ingrediente> ingredientes;

    private Temperatura temp;
    private Sabor sabor;
    private String nombre;
    private Categoria categoria;
    private Tipo tipo;
    private String tiempoDeCoccion;

    @OneToOne
    private Imagen imagen;

    public Receta() {
    }

    public Receta(String id, String instrucciones, List<Ingrediente> ingredientes, Temperatura temp, Sabor sabor, String nombre, Categoria categoria, Tipo tipo, String tiempoDeCoccion, Imagen imagen) {
        this.id = id;
        this.instrucciones = instrucciones;
        this.ingredientes = ingredientes;
        this.temp = temp;
        this.sabor = sabor;
        this.nombre = nombre;
        this.categoria = categoria;
        this.tipo = tipo;
        this.tiempoDeCoccion = tiempoDeCoccion;
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public Temperatura getTemp() {
        return temp;
    }

    public void setTemp(Temperatura temp) {
        this.temp = temp;
    }

    public Sabor getSabor() {
        return sabor;
    }

    public void setSabor(Sabor sabor) {
        this.sabor = sabor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getTiempoDeCoccion() {
        return tiempoDeCoccion;
    }

    public void setTiempoDeCoccion(String tiempoDeCoccion) {
        this.tiempoDeCoccion = tiempoDeCoccion;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Receta{" + "id=" + id + ", instrucciones=" + instrucciones + ", ingredientes=" + ingredientes + ", temp=" + temp + ", sabor=" + sabor + ", nombre=" + nombre + ", categoria=" + categoria + ", tipo=" + tipo + ", tiempoDeCoccion=" + tiempoDeCoccion + ", imagen=" + imagen + '}';
    }

}
