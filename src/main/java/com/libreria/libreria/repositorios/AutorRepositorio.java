package com.libreria.libreria.repositorios;

import com.libreria.libreria.entidades.Autor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String> {

    @Query("SELECT a FROM Autor a WHERE a.nombre = :nombre")
    public List<Autor> buscarPorNombre(@Param("nombre") String nombre);
    
    @Query("SELECT a FROM Autor a WHERE a.id = :id")
    public List<Autor> listarPorId(@Param("id") String id);

    //@Query("INSERT INTO autor VALUES (:nombre, :alta")
    //public Autor guardar(@Param("nombre") String nombre, @Param("alta") Boolean alta); Existe el save en JPA

    //@Query("UPDATE autor a SET a.alta = :alta WHERE a.id = :id ")
    //public Autor persistir(@Param("alta") Boolean alta, @Param("id") String id);
    
    //@Query("UPDATE autor a SET a.nombre = :nombre WHERE a.id = :id ")
    //public Autor modificar(@Param("nombre") String nombre, @Param("id") String id);
    
    //@Query("SELECT * FROM autor a")
    //public List<Autor> listarTodos(); Existe el método findAll 
    
    
}

