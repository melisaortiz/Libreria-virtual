package com.libreria.libreria.entidades;

import com.libreria.libreria.enums.Categoria;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

/**
 * La entidad libro modela los libros que están disponibles en la biblioteca
 * para ser prestados/vendidos. En esta entidad, el atributo “ejemplares”
 * contiene la cantidad total de ejemplares de ese libro, mientras que el
 * atributo “ejemplaresPrestados” contiene cuántos de esos ejemplares se
 * encuentran prestados en este momento y el atributo “ejemplaresRestantes”
 * contiene cuántos de esos ejemplares quedan para prestar.
 *
 *
 */
@Entity
public class Libro {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String titulo;
    private Long isbn;
    private String descripcion;
    private Integer anio;
    private Integer precio;
    private boolean compra;
    private boolean alta;
    private Integer ejemplares;
    private Integer ejemplaresPrestados;
    private Integer ejemplaresRestantes;

    @OneToOne
    private Autor autor;
    @OneToOne
    private Editorial editorial;
    @OneToOne
    private Foto foto;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    // CONSTRUCTORES
    public Libro() {
    }

    public Libro(String id, String titulo, Long isbn, String descripcion, Integer anio, Integer precio, boolean compra, boolean alta, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, Autor autor, Editorial editorial, Foto foto, Categoria categoria) {
        this.id = id;
        this.titulo = titulo;
        this.isbn = isbn;
        this.descripcion = descripcion;
        this.anio = anio;
        this.precio = precio;
        this.compra = compra;
        this.alta = alta;
        this.ejemplares = ejemplares;
        this.ejemplaresPrestados = ejemplaresPrestados;
        this.ejemplaresRestantes = ejemplaresRestantes;
        this.autor = autor;
        this.editorial = editorial;
        this.foto = foto;
        this.categoria = categoria;
    }

 
    //GETTERS Y SETTERS

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public boolean isCompra() {
        return compra;
    }

    public void setCompra(boolean compra) {
        this.compra = compra;
    }

    public boolean isAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    public Integer getEjemplares() {
        return ejemplares;
    }

    public void setEjemplares(Integer ejemplares) {
        this.ejemplares = ejemplares;
    }

    public Integer getEjemplaresPrestados() {
        return ejemplaresPrestados;
    }

    public void setEjemplaresPrestados(Integer ejemplaresPrestados) {
        this.ejemplaresPrestados = ejemplaresPrestados;
    }

    public Integer getEjemplaresRestantes() {
        return ejemplaresRestantes;
    }

    public void setEjemplaresRestantes(Integer ejemplaresRestantes) {
        this.ejemplaresRestantes = ejemplaresRestantes;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    

    //TO STRING
    @Override
    public String toString() {
        return "Libro{" + "id=" + getId() + ", isbn=" + getIsbn() + ", titulo=" + getTitulo() + ", anio=" + getAnio() + ", ejemplares=" + getEjemplares() + ", ejemplaresPrestados=" + getEjemplaresPrestados() + ", ejemplaresRestantes=" + getEjemplaresRestantes() + ", alta=" + isAlta() + ", autor=" + getAutor() + ", editorial=" + getEditorial() + '}';
    }

}
