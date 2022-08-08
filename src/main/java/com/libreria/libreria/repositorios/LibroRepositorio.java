package com.libreria.libreria.repositorios;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.entidades.Editorial;
import com.libreria.libreria.entidades.Libro;
import com.libreria.libreria.enums.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * (libroRepositorio) debe contener los métodos necesarios para
 * guardar/actualizar libros en la base de datos, realizar consultas o dar de
 * baja según corresponda. Extiende de JpaRepository: será un repositorio de
 * libro con la Primary Key de tipo String.
 *
 * Los métodos save(), findById() y delete() se implementan por JpaRepository.
 *
 */
@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String> {

    //Método que devuelve el libro segun el isbn pasado por parametro
    @Query("SELECT l FROM Libro l WHERE l.isbn = :isbn")
    public Libro buscarPorIsbn(@Param("isbn") Long isbn);

    // Método que devuelve el libro/s vinculado a un Autor segun su id:
    @Query("SELECT l FROM Libro l WHERE l.autor.id = :id")
    public List<Libro> buscarPorAutor(@Param("id") String id);

    // Método que devuelve el libro/s vinculado a una Editorial segun su id:
    @Query("SELECT l FROM Libro l WHERE l.editorial.id = :id")
    public List<Libro> buscarPorEditorial(@Param("id") String id);

    // Método que devuelve el libro/s vinculado a una Editorial segun su nombre:
    @Query("SELECT l FROM Libro l WHERE l.editorial.nombre = :nombreEditorial")
    public List<Libro> buscarPorNombreEditorial(@Param("nombreEditorial") Editorial editorial);

    // Método que devuelve el libro/s vinculado a un autor segun su nombre:
    @Query("SELECT l FROM Libro l WHERE l.autor.nombre = :nombreAutor")
    public List<Libro> buscarPorNombreAutor(@Param("nombreAutor") Autor autor);

    // Método que sólo devuelve los libros dados de alta.
    @Query("SELECT l FROM Libro l WHERE l.alta IS true ORDER BY l.titulo ASC")
    public List<Libro> findAllAltaIsTrue();

    // Método que sólo devuelve los libros dados de baja.
    @Query("SELECT l FROM Libro l WHERE l.alta IS false ORDER BY l.titulo ASC")
    public List<Libro> listarDeBaja();

    // Método que sólo devuelve los libros comprados.
    @Query("SELECT l FROM Libro l WHERE l.compra IS true ORDER BY l.titulo ASC")
    public List<Libro> listarDeCompra();

    // Método que sólo devuelve los libros agregados al carrito.
    @Query("SELECT SUM(l.precio) FROM Libro l WHERE l.compra IS true")
    public List<Long> sumaCarrito();

    // Método que devuelve el libro/s vinculado a una categoria:
    @Query("SELECT l FROM Libro l WHERE l.categoria = :categoria")
    public List<Libro> buscarPorCategoria(@Param("categoria") Categoria categoria);

    // Método que devuelve el libro/s vinculado a una título:
    @Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")
    public List<Libro> buscarPorTitulo(@Param("titulo") String titulo);

    // Método que devuelve el libro/s vinculado a un año:
    @Query("SELECT l FROM Libro l WHERE l.anio = :anio")
    public List<Libro> buscarPorAnio(@Param("anio") Integer anio);

}
