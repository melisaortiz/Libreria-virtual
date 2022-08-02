package com.libreria.libreria.repositorios;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.entidades.Editorial;
import com.libreria.libreria.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String> {
    
    @Query("SELECT l FROM Libro l WHERE l.isbn = :isbn")
    public Libro buscarPorIsbn(@Param("isbn") Long isbn);

    @Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")
    public List<Libro> buscarPorTitulo(@Param("titulo") String titulo);

    @Query("SELECT l FROM Libro l WHERE l.anio = :anio")
    public List<Libro> buscarPorAnio(@Param("anio") Integer anio);

    @Query("SELECT l FROM Libro l WHERE l.autor.nombre = :nombreAutor")
     public List<Libro> buscarPorAutor(@Param("nombreAutor") Autor autor);
     
     @Query("SELECT l FROM Libro l WHERE l.editorial.nombre = :nombreEditorial")
     public List<Libro> buscarPorEditorial(@Param("nombreEditorial") Editorial editorial);

    
}
