package com.libreria.libreria.servicios;

import com.libreria.libreria.entidades.Libro;
import com.libreria.libreria.errores.ErroresServicio;
import com.libreria.libreria.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    public void validar(Long isbn, String titulo, Integer anio, Integer ejemplares) throws ErroresServicio {

        if (isbn == null || isbn.toString().length() != 8) {
            throw new ErroresServicio("El ISBN debe tener 8 caracteres");
        }

        if (titulo == null || titulo.trim().isEmpty()) {
            throw new ErroresServicio("El titulo no debe estar vacío");
        }

        if (anio == null || isbn.toString().length() > 4) {
            throw new ErroresServicio("El año no debe tener más de 4 caracteres");
        }

        if (ejemplares == null || ejemplares >= 0) {
            throw new ErroresServicio("La cantidad de ejemplares no debe ser nula.");
        }

    }

    @Transactional(propagation = Propagation.NESTED)
    public void crear(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPresatdos, Integer ejemplaresRestantes) throws ErroresServicio {
        validar(isbn, titulo, anio, ejemplares);
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(0);
        libro.setEjemplaresRestantes(ejemplares);
        libro.setAlta(Boolean.TRUE);

        libroRepositorio.save(libro);

    }

    @Transactional(propagation = Propagation.NESTED)
    public void modificar(String id, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes) throws ErroresServicio {

        validar(isbn, titulo, anio, ejemplares);
        validarEjemplares(ejemplares, ejemplaresPrestados);
        Optional<Libro> respuesta = libroRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresPrestados(ejemplaresPrestados);
            libro.setEjemplaresRestantes(ejemplares - ejemplaresPrestados);

            libroRepositorio.save(libro);
        } else {
            throw new ErroresServicio("No se encontró el libro solicitado.");
        }

    }

    public void validarEjemplares(Integer ejemplares, Integer ejemplaresPrestados) throws ErroresServicio {
        //Validamos que la cantidad de ejempalares para que no arroje un numero negativo en los ejemplares restantes
        if (ejemplares < ejemplaresPrestados) {
            throw new ErroresServicio("La cantidad de ejemplares no puede ser menor a la cantidad de ejemplares prestados");
        }
    }

    @Transactional(propagation = Propagation.NESTED)
    public void deshabilitar(String id) throws ErroresServicio {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(Boolean.FALSE);
            libroRepositorio.save(libro);

        } else {
            throw new ErroresServicio("No se encontró el libro solicitado.");
        }
    }

    @Transactional(propagation = Propagation.NESTED)
    public void habilitar(String id) throws ErroresServicio {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(Boolean.TRUE);
            libroRepositorio.save(libro);

        } else {
            throw new ErroresServicio("No se encontró el libro solicitado.");
        }
    }

    @Transactional(readOnly = true)
    public List<Libro> buscarTodosPorNombre() {

        List<Libro> libros = libroRepositorio.findAll();

        return libros;

    }

    @Transactional(propagation = Propagation.NESTED)
    public void eliminar(String id) throws ErroresServicio {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libroRepositorio.delete(libro);
        } else {
            throw new ErroresServicio("No se encontró el libro solicitado.");
        }
    }
}
