package com.libreria.libreria.repositorios;

import com.libreria.libreria.entidades.Editorial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String> {

    @Query("SELECT e FROM Editorial e WHERE e.nombre = :nombre")
    public List<Editorial> buscarTodosPorNombre(@Param("nombre") String nombre);
    
    // Método que devuelve el Autor buscado por su nombre:
    @Query("SELECT e FROM Editorial e WHERE e.nombre = :nombre")
    public Editorial buscarPorNombre(@Param("nombre") String nombre);
    
}
