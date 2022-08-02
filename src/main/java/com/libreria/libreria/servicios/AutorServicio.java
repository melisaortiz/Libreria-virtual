package com.libreria.libreria.servicios;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.errores.ErroresServicio;
import com.libreria.libreria.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

        
    public void validar(String nombre) throws ErroresServicio {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ErroresServicio("El nombre/apellido no puede estar vacío.");
        }
    }

    
    @Transactional(propagation = Propagation.NESTED)
    public void crear(String nombre) throws ErroresServicio {
        //VALIDACIÓN
        validar(nombre);
        Autor autor = new Autor();
        //SETEO DE ATRIBUTOS
        autor.setNombre(nombre);
        autor.setAlta(true);

        //PERSISTIR NUEVO OBJETO
        autorRepositorio.save(autor);
    }

    
    
    @Transactional(propagation = Propagation.NESTED)
    public void modificar(String id, String nombre) throws ErroresServicio {
        validar(nombre);
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            autorRepositorio.save(autor);
        } else {
            throw new ErroresServicio("No se encontró el autor solicitado.");
        }

    }

    @Transactional(propagation = Propagation.NESTED)
    public void deshabilitar(String id) throws ErroresServicio {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(Boolean.FALSE);
            autorRepositorio.save(autor);

        } else {
            throw new ErroresServicio("No se encontró el autor solicitado.");
        }
    }

    @Transactional(propagation = Propagation.NESTED)
    public void habilitar(String id) throws ErroresServicio {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(Boolean.TRUE);
            autorRepositorio.save(autor);

        } else {
            throw new ErroresServicio("No se encontró el autor solicitado.");
        }
    }

    @Transactional(readOnly = true)
    public List<Autor> listarTodos() {
        List<Autor> autores = autorRepositorio.findAll();
        return autores;
    }
    
    
    @Transactional(readOnly = true)
    public List<Autor> listarTodosPorId(String id) {
        List<Autor> autores = autorRepositorio.listarPorId(id);
        return autores;
    }
    
     @Transactional(readOnly = true)
    public List<Autor> listarTodosPorNombre(String nombre) {
        List<Autor> autores = autorRepositorio.buscarPorNombre(nombre);
        return autores;
    }

    @Transactional(propagation = Propagation.NESTED)
    public void eliminar(String id) throws ErroresServicio {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autorRepositorio.delete(autor);
        } else {
            throw new ErroresServicio("No se encontró el autor solicitado.");
        }
    }
}
