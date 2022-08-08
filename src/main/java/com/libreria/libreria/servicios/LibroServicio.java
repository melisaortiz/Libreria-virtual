package com.libreria.libreria.servicios;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.entidades.Editorial;
import com.libreria.libreria.entidades.Foto;
import com.libreria.libreria.entidades.Libro;
import com.libreria.libreria.enums.Categoria;
import com.libreria.libreria.errores.ErroresServicio;
import com.libreria.libreria.repositorios.AutorRepositorio;
import com.libreria.libreria.repositorios.EditorialRepositorio;
import com.libreria.libreria.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Esta clase tiene la responsabilidad de llevar adelante las funcionalidades
 * necesarias para administrar libros (consulta, creación, modificación y dar de
 * baja).
 *
 */
@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Autowired
    private EditorialServicio editorialServicio;

    @Autowired
    private FotoServicio fotoServicio;

    /**
     * Método para registrar un libro:
     *
     * @param archivo --> foto
     * @param isbn
     * @param titulo
     * @param anio
     * @param descripcion
     * @param precio
     * @param ejemplares
     * @param autor
     * @param categoria
     * @param editorial
     * @throws com.libreria.libreria.errores.ErroresServicio
     * @throws Exception
     */
    @Transactional(propagation = Propagation.NESTED)
    public void crearLibro(MultipartFile archivo, Long isbn, String titulo, Integer anio, String descripcion, Integer precio, Autor autor, Editorial editorial, Categoria categoria, Integer ejemplares) throws ErroresServicio, Exception {
        try {
            //Se validan que esten bien los datos ingresados
            System.out.println("Se validan los datos");
            validar(isbn, titulo, anio, descripcion, precio, ejemplares);
            System.out.println("datos validados");
            //Creación del libro
            Libro libro = new Libro();

            //seteo de atributos
            libro.setAlta(true);
            libro.setCompra(true);
            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setDescripcion(descripcion);
            libro.setPrecio(precio);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresPrestados(0);
            libro.setEjemplaresRestantes(ejemplares);
            libro.setCategoria(categoria);
            System.out.println("seteo de tributos bien, sin autor ni editor");
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            System.out.println("Seteo de autor y editorial");

//            // Se da de alta el autor en caso de que esté dado de baja:
//            if (!libro.getAutor().isAlta()) {
//                autorServicio.habilitarAutor(libro.getAutor().getId());
//            }
//
//            //Se da de alta la editorial en caso de que esté dado de baja:
//            if (!libro.getEditorial().isAlta()) {
//                editorialServicio.habilitar(libro.getEditorial().getId());
//            }
            //Se guarda la foto
            Foto foto = fotoServicio.guardar(archivo);
            libro.setFoto(foto);

            //Persistencia en BD
            libro.setCategoria(categoria);
            libroRepositorio.save(libro);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
//            throw new Exception("Error al intentar guardar el arte.");
        }

    }

    /**
     * Método para modificar un libro.
     *
     * @param id
     * @param archivo
     * @param isbn
     * @param titulo
     * @param anio
     * @param descripcion
     * @param ejemplares
     * @param precio
     * @param autor
     * @param editorial
     * @param categoria
     * @param ejemplaresPrestados
     * @throws com.libreria.libreria.errores.ErroresServicio
     * @throws Exception
     */
    @Transactional
    public void modificarLibro(String id, MultipartFile archivo, Long isbn, String titulo, Integer anio, String descripcion, Integer ejemplares, Integer precio, Autor autor, Editorial editorial, Categoria categoria) throws ErroresServicio, Exception {
        try {
            //Validación de datos ingresados:
            validar(isbn, titulo, anio, descripcion, precio, ejemplares);

            // Usamos el repositorio para que busque el libro cuyo id sea el pasado como parámetro.
            Optional<Libro> respuesta = libroRepositorio.findById(id);
            if (respuesta.isPresent()) { // El libro con ese id SI existe en la DB
                Libro libro = respuesta.get();
                // Seteo de atributos:
                libro.setIsbn(isbn);
                libro.setTitulo(titulo);
                libro.setAnio(anio);
                libro.setDescripcion(descripcion);
                libro.setPrecio(precio);
                libro.setCategoria(categoria);
                libro.setEjemplares(ejemplares);
              
                // Seteo de Autor y Editorial:
                libro.setAutor(autor);
                libro.setEditorial(editorial);

                //Seteo de foto
                if (!archivo.isEmpty()) {
                    String idFoto = null;
                    if (libro.getFoto() != null) {
                        idFoto = libro.getFoto().getId();
                    }
                    Foto foto = fotoServicio.actualizar(idFoto, archivo);
                    libro.setFoto(foto);
                }

                // Persistencia en la DB:
                libroRepositorio.save(libro);
            } else { // El arte con ese id NO existe en la DB
                throw new ErroresServicio("No se encontró el libro solicitado.");
            }
        } catch (ErroresServicio e) {
            throw new Exception(e.getMessage());
//            throw new Exception("Error al intentar modificar el libro.");
        }

    }

    /**
     * El método borra el libro de la DB (no se utiliza para darlo de baja).
     *
     * @param id
     * @throws java.lang.Exception
     */
    @Transactional
    public void eliminarLibro(String id) throws Exception {
        try {
            // Usamos el repositorio para que busque el libro cuyo id sea el pasado como parámetro.        
            Optional<Libro> respuesta = libroRepositorio.findById(id);
            if (respuesta.isPresent()) { // El libro con ese id SI existe en la DB
                Libro libro = respuesta.get();
                // Se eliminan todos los préstamos del libro:
                //Persistencia en la DB:
                libroRepositorio.delete(libro);
            } else { // El arte con ese id NO existe en la DB
                throw new ErroresServicio("No se encontró el libro solicitado.");
            }
        } catch (ErroresServicio e) {
            throw new Exception("Error al intentar eliminar el libro.");
        }
    }

    /**
     * El método sirve para setear como 'false' el atributo 'alta' del libro.
     *
     * @param id
     * @throws Exception
     */
    @Transactional
    public void deshabilitarLibro(String id) throws ErroresServicio, Exception {
        try {
            // Usamos el repositorio para que busque el libro cuyo id sea el pasado como parámetro.
            Libro libro = libroRepositorio.getById(id);
            if (libro != null) { // El libro con ese id SI existe en la DB
                libro.setAlta(Boolean.FALSE);
                libroRepositorio.save(libro);
            } else { // El libro con ese id NO existe en la DB
                throw new ErroresServicio("No se encontró el libro solicitado.");
            }

        } catch (ErroresServicio e) {
            throw new Exception("Error al intentar dar de baja el libro.");
        }
    }

    /**
     * El método sirve para setear como 'false' el atributo 'compra' del libro.
     *
     * @param id
     * @throws Exception
     */
    @Transactional
    public void bajaDeCompra(String id) throws Exception {
        try {
            // Usamos el repositorio para que busque el libro cuyo id sea el pasado como parámetro.
            Libro libro = libroRepositorio.getById(id);
            if (libro != null) { // El libro con ese id SI existe en la DB
                // Se dan de baja los préstamos del libro:

                libro.setCompra(false);
                libroRepositorio.save(libro);
            } else { // El libro con ese id NO existe en la DB
                throw new Exception("No existe libro con el id indicado.");
            }
        } catch (Exception e) {
            throw new Exception("Error al intentar dar de baja el libro.");
        }
    }

    /**
     * El método sirve para setear como 'true' el atributo 'alta' del libro.
     *
     * @param id
     * @throws Exception
     */
    @Transactional
    public void habilitarLibro(String id) throws ErroresServicio, Exception {
        try {
            // Usamos el repositorio para que busque el arte cuyo id sea el pasado como parámetro.
            Libro libro = libroRepositorio.getById(id);
            if (libro != null) { // El libro con ese id SI existe en la DB
                // Se da de alta el libro:
                libro.setAlta(true);
                libroRepositorio.save(libro);
            } else { // El libro con ese id NO existe en la DB
                throw new Exception("No existe libro con el id indicado.");
            }
        } catch (Exception e) {
            throw new Exception("Error al intentar dar de baja el libro.");
        }
    }

    /**
     * El método sirve para setear como 'false' el atributo 'compra' del libro.
     *
     * @param id
     * @throws Exception
     */
    @Transactional
    public void altaDeCompra(String id) throws Exception {
        try {
            // Usamos el repositorio para que busque el libro cuyo id sea el pasado como parámetro.
            Libro libro = libroRepositorio.getById(id);
            if (libro != null) { // El libro con ese id SI existe en la DB
                // Se dan de baja los préstamos del libro:
                libro.setCompra(true);
                libroRepositorio.save(libro);
            } else { // El libro con ese id NO existe en la DB
                throw new Exception("No existe libro con el id indicado.");
            }
        } catch (Exception e) {
            throw new Exception("Error al intentar dar de baja el libro.");
        }
    }

    /**
     * No se tienen en cuenta ni el Autor ni la Editorial, ya que se podrán
     * seleccionar de una lista.
     *
     * @param isbn
     * @param titulo
     * @param anio
     * @param descripcion
     * @param precio
     * @param ejemplares
     * @throws com.libreria.libreria.errores.ErroresServicio
     */
    public void validar(Long isbn, String titulo, Integer anio, String descripcion, Integer precio, Integer ejemplares) throws ErroresServicio {

        if (isbn == null || isbn.toString().length() != 8) {
            throw new ErroresServicio("El ISBN debe tener 8 caracteres");
        }
        System.out.println("validacion isbn pasada");
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new ErroresServicio("El titulo no debe estar vacío");
        }
        System.out.println("validacion titulo pasada");
        if (anio == null || anio.toString().length() > 4) {
            throw new ErroresServicio("El año no debe tener más de 4 caracteres");
        }
        System.out.println("validacion año pasada");
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new ErroresServicio("La descripción es obligatoria.");
        }
        System.out.println("validacion descripcion 1 pasada");
        if (descripcion.length() > 255) {
            throw new ErroresServicio("La descripción no puede tener más de 200 caracteres.");
        }
        System.out.println("validacion descripcion 2 pasada");
        if (ejemplares == null || ejemplares <= 0) {
            throw new ErroresServicio("La cantidad de ejemplares no debe ser nula.");
        }
        System.out.println("validacion ejemplares pasada");
        if (precio < 0 || precio == null) {
            throw new ErroresServicio("Precio ingresado no es válido.");
        }
        System.out.println("validacion precio pasada");
    }

    public void validarEjemplares(Integer ejemplares, Integer ejemplaresPrestados) throws ErroresServicio {
        //Validamos que la cantidad de ejempalares para que no arroje un numero negativo en los ejemplares restantes
        if (ejemplares < ejemplaresPrestados) {
            throw new ErroresServicio("La cantidad de ejemplares no puede ser menor a la cantidad de ejemplares prestados");
        }
    }

    // ------------------------------ MÉTODOS DEL REPOSITORIO ------------------------------
    /**
     * Busca los libros por id
     *
     * @param id
     * @return
     */
    public Libro getById(String id) {
        return libroRepositorio.getById(id);
    }

    /**
     *
     * Lista los libros según su autor
     *
     * @param idAutor
     * @return
     */
    public List<Libro> buscarPorAutor(String idAutor) {
        return libroRepositorio.buscarPorAutor(idAutor);
    }

    /**
     *
     * Lista los libros según su editorial
     *
     * @param idEditorial
     * @return
     */
    public List<Libro> buscarPorEditorial(String idEditorial) {
        return libroRepositorio.buscarPorEditorial(idEditorial);
    }

    /**
     * Sólo devuelve todos los libros.
     *
     * @return
     */
    public List<Libro> findAll() {
        return libroRepositorio.findAll();
    }

    /**
     * Sólo devuelve todos los libros dados de alta.
     *
     * @return
     */
    public List<Libro> findAllAltaIsTrue() {
        return libroRepositorio.findAllAltaIsTrue();
    }

    /**
     * Sólo devuelve los libro dados de baja.
     *
     * @return
     */
    public List<Libro> listarDeBaja() {
        return libroRepositorio.listarDeBaja();
    }
    /**
     * Devuelve los libro comprados.
     *
     * @return
     */
//    public List<Libro> listarDeCompra() {
//        return libroRepositorio.listarDeCompra();
//    }
//    
//    /**
//     *Devuelve los libro añadidos al carrito de compra.
//     *
//     * @return
//     */
//    
//    public List<Long> sumaCarrito() {
//        return libroRepositorio.sumaCarrito();
//    }
    /**
     *
     * Lista los libros según su categoria
     *
     * @param categoria
     * @return
     */
    public List<Libro> buscarPorCategoria(Categoria categoria) {
        return libroRepositorio.buscarPorCategoria(categoria);
    }

}
