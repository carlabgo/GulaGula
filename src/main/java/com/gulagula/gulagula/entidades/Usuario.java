package com.gulagula.gulagula.entidades;

import com.gulagula.gulagula.enumeradores.Rols;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private String clave;
    @Column(unique = true)
    private String email;
    private Boolean alta;

    @Enumerated(EnumType.STRING)
    private Rols rol;

    public Usuario() {
    }

    public Usuario(String id, String nombre, String clave, String email, Boolean alta, Rols role) {
        this.id = id;
        this.nombre = nombre;
        this.clave = clave;
        this.email = email;
        this.alta = alta;
        this.rol = rol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    public Rols getRol() {
        return rol;
    }

    public void setRol(Rols rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nombre=" + nombre + ", clave=" + clave + ", email=" + email + ", alta=" + alta + ", rol=" + rol + '}';
    }

}
