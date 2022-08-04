package com.libreria.libreria.entidades;

import com.libreria.libreria.enums.Pais;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

/**
 * La entidad autor modela los autores de los libros.
 */
@Entity
public class Autor {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private String descripcion;
    private Boolean alta;

    @OneToOne
    private Foto foto;
    @Enumerated(EnumType.STRING)
    private Pais pais;
    
    //CONSTRUCTORES

    public Autor() {
    }

    public Autor(String id, String nombre, String descripcion, Boolean alta, Foto foto, Pais pais) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.alta = alta;
        this.foto = foto;
        this.pais = pais;
    }

      
    //GETTERS Y SETTERS
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

   
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
      public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

  
    public Boolean isAlta() {
        return alta;
    }

    
    public void setAlta(Boolean alta) {
        this.alta = alta;
    }
    
    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }
    
    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    //TO STRING 
    
    @Override
    public String toString() {
        return "Autor{" + "id=" + id + ", nombre=" + nombre + ", alta=" + alta + '}';
    }

}
