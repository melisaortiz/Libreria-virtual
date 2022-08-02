package com.libreria.libreria.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Libro {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false) //Esta anotación es para indicar que esa columna no puede estar vacia
    private Long isbn;
    @Column(nullable = false)
    private String titulo;
    private Integer anio;
    private Integer ejemplares;
    private Integer ejemplaresPrestados;
    private Integer ejemplaresRestantes;
    private Boolean alta;

    @OneToOne
    private Autor autor;
    @OneToOne
    private Editorial editorial;

    // CONSTRUCTORES
    
    public Libro() {
    }

    public Libro( Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, Boolean alta, Autor autor, Editorial editorial) {
       
        this.isbn = isbn;
        this.titulo = titulo;
        this.anio = anio;
        this.ejemplares = ejemplares;
        this.ejemplaresPrestados = ejemplaresPrestados;
        this.ejemplaresRestantes = ejemplaresRestantes;
        this.alta = alta;
        this.autor = autor;
        this.editorial = editorial;
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
     * @return the isbn
     */
    public Long getIsbn() {
        return isbn;
    }

    /**
     * @param isbn the isbn to set
     */
    public void setIsbn(Long isbn) {
        this.setIsbn(isbn);
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the anio
     */
    public Integer getAnio() {
        return anio;
    }

    /**
     * @param anio the anio to set
     */
    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    /**
     * @return the ejemplares
     */
    public Integer getEjemplares() {
        return ejemplares;
    }

    /**
     * @param ejemplares the ejemplares to set
     */
    public void setEjemplares(Integer ejemplares) {
        this.ejemplares = ejemplares;
    }

    /**
     * @return the ejemplaresPrestados
     */
    public Integer getEjemplaresPrestados() {
        return ejemplaresPrestados;
    }

    /**
     * @param ejemplaresPrestados the ejemplaresPrestados to set
     */
    public void setEjemplaresPrestados(Integer ejemplaresPrestados) {
        this.ejemplaresPrestados = ejemplaresPrestados;
    }

    /**
     * @return the ejemplaresRestantes
     */
    public Integer getEjemplaresRestantes() {
        return ejemplaresRestantes;
    }

    /**
     * @param ejemplaresRestantes the ejemplaresRestantes to set
     */
    public void setEjemplaresRestantes(Integer ejemplaresRestantes) {
        this.ejemplaresRestantes = ejemplaresRestantes;
    }

    /**
     * @return the alta
     */
    public Boolean getAlta() {
        return alta;
    }

    /**
     * @param alta the alta to set
     */
    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    /**
     * @return the autor
     */
    public Autor getAutor() {
        return autor;
    }

    /**
     * @param autor the autor to set
     */
    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    /**
     * @return the editorial
     */
    public Editorial getEditorial() {
        return editorial;
    }

    /**
     * @param editorial the editorial to set
     */
    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    
    //TO STRING
    
    @Override
    public String toString() {
        return "Libro{" + "id=" + getId() + ", isbn=" + getIsbn() + ", titulo=" + getTitulo() + ", anio=" + getAnio() + ", ejemplares=" + getEjemplares() + ", ejemplaresPrestados=" + getEjemplaresPrestados() + ", ejemplaresRestantes=" + getEjemplaresRestantes() + ", alta=" + getAlta() + ", autor=" + getAutor() + ", editorial=" + getEditorial() + '}';
    }

    

}
