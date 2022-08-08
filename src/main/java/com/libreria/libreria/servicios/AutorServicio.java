package com.libreria.libreria.servicios;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.entidades.Foto;
import com.libreria.libreria.enums.Pais;
import com.libreria.libreria.errores.ErroresServicio;
import com.libreria.libreria.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Esta clase tiene la responsabilidad de llevar adelante las funcionalidades
 * necesarias para administrar autores (consulta, creación, modificación y dar
 * de baja).
 *
 *
 */
@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private FotoServicio fotoServicio;

    /**
     * Método para registrar un autor.
     *
     * @param archivo
     * @param nombre
     * @param descripcion
     * @param pais
     * @throws Exception
     */
    @Transactional
    public void agregarAutor(MultipartFile archivo, String nombre, String descripcion, Pais pais) throws Exception {

        // Valido los datos ingresados:
        validar(nombre, descripcion);
        Autor autor = new Autor();
        // Seteo de atributos:
        autor.setAlta(true);
        autor.setNombre(nombre);
        autor.setDescripcion(descripcion);
        autor.setPais(pais);
        // Seteo de la foto:
        Foto foto = fotoServicio.guardar(archivo);
        autor.setFoto(foto);
        // Persistencia en la DB:
        autorRepositorio.save(autor);
    }

    /**
     * Método para modificar un autor.
     *
     * @param id
     * @param archivo
     * @param nombre
     * @param descripcion
     * @param pais
     * @throws Exception
     */
    @Transactional
    public void modificarAutor(String id, MultipartFile archivo, String nombre, String descripcion, Pais pais) throws Exception {
        try {
            // Valido los datos ingresados:
//            validar(nombre, descripcion);
            Optional<Autor> respuesta = autorRepositorio.findById(id);
            if (respuesta.isPresent()) { // El autor con ese id SI existe en la DB
                Autor autor = respuesta.get();
                // Seteo de atributos:
                autor.setNombre(nombre);
                autor.setDescripcion(descripcion);
                autor.setPais(pais);
                Foto foto = fotoServicio.guardar(archivo);
                autor.setFoto(foto);
                // Persistencia en la DB:
                autorRepositorio.save(autor);
            } else { // El autor con ese id NO existe en la DB
                throw new Exception("No existe el autor con el id indicado.");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    // VALIDACION
    public void validar(String nombre, String descripcion) throws ErroresServicio {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ErroresServicio("El nombre del autor no puede estar vacío.");
        }
        if (descripcion == null || descripcion.isEmpty()) {
            throw new ErroresServicio("La descripción es obligatoria.");
        }
        if (descripcion.length() > 255) {
            throw new ErroresServicio("La descripción no puede tener más de 200 caracteres.");
        }

    }

    // ------------------------------ MÉTODOS DEL REPOSITORIO ------------------------------
    /**
     *
     * @param nombre
     * @return
     */
    public Autor buscarPorNombre(String nombre) {
        return autorRepositorio.buscarPorNombre(nombre);
    }

    /**
     * Lista todos los autores dados de alta
     *
     * @return
     */
    public List<Autor> findAll() {
        return autorRepositorio.findAll();
    }

    /**
     *
     * @param id
     * @return
     */
    public Autor getById(String id) {
        return autorRepositorio.getById(id);
    }

    /**
     * Elimina el autor cuyo id se pasa por parametro
     *
     * @return
     */
    @Transactional
    public void eliminarAutor(String id) throws Exception {
        try {
            Autor autor = autorRepositorio.getById(id);
            if (autor != null) { // El autor con ese id SI existe en la DB

                autorRepositorio.delete(autor);

            } else { // El autor con ese id NO existe en la DB
                throw new Exception("No existe el autor con el id indicado.");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Da de baja el autor cuyo id se pasa por parametro
     *
     * @return
     */
    @Transactional
    public void deshabilitarAutor(String id) throws ErroresServicio, Exception {
        try {
            Autor autor = autorRepositorio.getById(id);
            if (autor != null) { // El autor con ese id SI existe en la DB
                // Dar de baja todos sus libros:
                autor.setAlta(false);
                // Persistencia en la DB:
                autorRepositorio.save(autor);
            } else { // El autor con ese id NO existe en la DB
                throw new Exception("No existe el autor con el id indicado.");
            }
        } catch (Exception e) {
            throw new Exception("Error al intentar dar de baja el Autor.");
        }
    }

    /**
     * Da de alta el autor cuyo id se pasa por parametro
     *
     * @return
     */
    @Transactional
    public void habilitarAutor(String id) throws ErroresServicio, Exception {
        try {
            Optional<Autor> respuesta = autorRepositorio.findById(id);
            if (respuesta.isPresent()) { // El autor con ese id SI existe en la DB
                Autor autor = respuesta.get();
                autor.setAlta(true);
                // Persistencia en la DB:
                autorRepositorio.save(autor);
                // Dar de alta todos sus libros:

            } else { // El autor con ese id NO existe en la DB
                throw new Exception("No existe el autor con el id indicado.");
            }
        } catch (Exception e) {
            throw new Exception("Error al intentar dar de alta el Autor.");
        }
    }

    /**
     * Sólo devuelve todos los libros dados de alta.
     *
     * @return
     */
    public List<Autor> findAllAltaIsTrue() {
        return autorRepositorio.findAllAltaIsTrue();
    }
    
    @Transactional(readOnly = true)
    public List<Autor> listarTodos() {
        List<Autor> autores = autorRepositorio.findAll();
        return autores;
    }
}
