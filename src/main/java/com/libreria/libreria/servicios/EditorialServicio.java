package com.libreria.libreria.servicios;

import com.libreria.libreria.entidades.Editorial;
import com.libreria.libreria.errores.ErroresServicio;
import com.libreria.libreria.repositorios.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Esta clase tiene la responsabilidad de llevar adelante las funcionalidades
 * necesarias para administrar editoriales (consulta, creación, modificación y
 * dar de baja).
 *
 *
 */
@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    public void validar(String nombre) throws ErroresServicio {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ErroresServicio("El nombre no puede estar vacío.");
        }
    }

    /**
     * Método para registrar una editorial.
     *
     * @param nombre
     * @throws Exception
     */
    @Transactional
    public void agregarEditorial(String nombre) throws ErroresServicio {
        validar(nombre);
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(Boolean.TRUE);

        editorialRepositorio.save(editorial);
    }

    @Transactional
    public void modificarEditorial(String id, String nombre) throws ErroresServicio {
        validar(nombre);
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            editorialRepositorio.save(editorial);
        } else {
            throw new ErroresServicio("No se encontró la editorial solicitada.");
        }

    }

    @Transactional(propagation = Propagation.NESTED)
    public void deshabilitar(String id) throws ErroresServicio {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setAlta(Boolean.FALSE);
            editorialRepositorio.save(editorial);

        } else {
            throw new ErroresServicio("No se encontró la editorial solicitada.");
        }
    }

    @Transactional(propagation = Propagation.NESTED)
    public void habilitar(String id) throws ErroresServicio {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setAlta(Boolean.TRUE);
            editorialRepositorio.save(editorial);

        } else {
            throw new ErroresServicio("No se encontró el autor solicitado.");
        }
    }

    // ------------------------------ MÉTODOS DEL REPOSITORIO ------------------------------
    /**
     *
     * @param nombre
     * @return
     */
    public Editorial buscarPorNombre(String nombre) {
        return editorialRepositorio.buscarPorNombre(nombre);
    }
    
    /**
     * Sólo devuelve todos los libros dados de alta.
     *
     * @return
     */
    public List<Editorial> findAllAltaIsTrue() {
        return editorialRepositorio.findAllAltaIsTrue();
    }
    
    @Transactional(readOnly = true)
    public List<Editorial> buscarTodosPorNombre() {

        List<Editorial> editoriales = editorialRepositorio.findAll();

        return editoriales;
    }

    public List<Editorial> findAll() {
        return editorialRepositorio.findAll();
    }

    @Transactional(propagation = Propagation.NESTED)
    public void eliminar(String id) throws ErroresServicio {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorialRepositorio.delete(editorial);
        } else {
            throw new ErroresServicio("No se encontró la editorial solicitada.");
        }
    }

     /**
     *
     * @param id
     * @return
     */
    public Editorial getById(String id) {
        return editorialRepositorio.getById(id);
    }
    
}
