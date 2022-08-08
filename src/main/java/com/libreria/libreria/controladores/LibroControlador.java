package com.libreria.libreria.controladores;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.entidades.Editorial;
import com.libreria.libreria.entidades.Libro;
import com.libreria.libreria.enums.Categoria;
import com.libreria.libreria.servicios.AutorServicio;
import com.libreria.libreria.servicios.EditorialServicio;
import com.libreria.libreria.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
//@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;

    @Autowired
    private EditorialServicio editorialServicio;

    @Autowired
    private AutorServicio autorServicio;

    //Crear un libro
    @PostMapping("/crear_libro")
    public String crearLibro(ModelMap model, MultipartFile archivo, @RequestParam Long isbn,
            @RequestParam String titulo, @RequestParam Integer anio, @RequestParam String descripcion,
            @RequestParam Integer precio, @RequestParam Categoria categoria,
            @RequestParam Integer ejemplares, String idAutor, String idEditorial) {
        Autor autor;
        Editorial editorial;


        try {

//             Seteo del autor:
            try {
                autor = autorServicio.getById(idAutor);

            } catch (Exception e) {
                throw new Exception("Debe seleccionar un autor");
            }

//             Seteo de la editorial:
            try {
                editorial = editorialServicio.getById(idEditorial);

            } catch (Exception e) {
                throw new Exception("Debe seleccionar un autor");
            }
            System.out.println("Entro al try");

            // Seteo del libro:            
            libroServicio.crearLibro(archivo, isbn, titulo, anio, descripcion, precio, autor, editorial, categoria, ejemplares);
            // Mensaje de éxito inyectado al modelo de "admin-arte.html":
            model.put("exito", "El libro " + titulo + "' fue registrado exitosamente.");

            return "redirect:/admin";

        } catch (Exception e) {
            // Mensaje de error inyectado al modelo de "error.html":
            if (e.getMessage() == null || titulo == null || anio == null || descripcion == null || precio == null || idAutor == null || idEditorial == null || categoria == null) {
                model.put("error", "Error al intentar guardar el libro: faltó completar algún campo.");
            } else {
                model.put("error", "Error al intentar guardar el libro: " + e.getMessage());
            }
            System.out.println("Entro al catch");
            return "administrador.html";
        }
    }

    //Controlador para modificar un libro
    @PostMapping("/modificar_libro")
    public String modificarLibro(ModelMap model, MultipartFile archivo, @RequestParam String idLibro,
            @RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio,
            @RequestParam String descripcion, @RequestParam Integer precio, @RequestParam Categoria categoria,
            @RequestParam Integer ejemplares, String idAutor, String idEditorial) {

        Libro libro;
        Autor autor;
        Editorial editorial;
        try {

            //Seteo del id del libro:
            try {
                libro = libroServicio.getById(idLibro);

            } catch (Exception e) {
                throw new Exception("Debe seleccionar un libro");
            }
            //Seteo del id del autor:
            try {
                autor = autorServicio.getById(idAutor);

            } catch (Exception e) {
                throw new Exception("Debe seleccionar un autor");
            }
            //Seteo del id de la editorial:
            try {
                editorial = editorialServicio.getById(idEditorial);

            } catch (Exception e) {
                throw new Exception("Debe seleccionar un autor");
            }

            libroServicio.modificarLibro(libro.getId(), archivo, isbn, titulo, anio, descripcion, ejemplares, precio, autor, editorial, categoria);

            // Mensaje de éxito inyectado al modelo:
            model.put("exito", "El autor fue modificado exitosamente.");
            // Datos inyectados al modelo de "admin-autor.html":
            List<Libro> libros = libroServicio.findAll();
            model.put("libros", libros);
            return "redirect:/admin";

        } catch (Exception e) {

            // Mensaje de error inyectado al modelo:
//            Autor autor = autorServicio.getById(id);
//            model.put("autorModif", autor);
//            model.put("error", "Error al intentar modificar el autor: " + e.getMessage());
            return "administrador.html";
        }
    }

    /**
     * Función que carga la vista para modificar los datos de un arte elegido
     * previamente. Busca el arte en el repositorio por id, y lo inyecta al
     * modelo para tener todos sus datos. También se inyecta la lista de Autores
     * y Editoriales.
     *
     * @param model
     * @param idLibroElegido
     * @return
     */
//    
//    @GetMapping("/modificar-libro-elegido/{idLibroModif}")
//    public String modificarLibroElegido(ModelMap model,
//            @PathVariable String idLibroElegido) {
//        
//        Libro libro = libroServicio.getById(idLibroElegido);
//        model.addAttribute("libroModif", libro);
//        List<Autor> autores = autorServicio.findAll();
//        model.addAttribute("autores", autores);
//         List<Editorial> editoriales = editorialServicio.findAll();
//        model.addAttribute("editoriales", editoriales);
//        model.addAttribute("categorias", Categoria.values());
//        return "administrador.html";
//    }
    /**
     * Función para dar de baja un libro. Una vez modificado el atributo "alta"
     * desde el servicio, muestra la página de "administrador.html" con mensajes
     * inyectados al modelo. Es una url con "path variable" (id del arte a dar
     * de baja).
     *
     * ESTE MÉTODO USA EL MODEL PARA QUE APAREZCA LA ALERTA ("success" O
     * "error") EN LA MISMA PLANTILA DE "admin-arte.html".
     *
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/baja/{id}")
    public String darBaja(ModelMap model, @PathVariable String id) {
        try {
            libroServicio.deshabilitarLibro(id);
            // Mensaje de éxito inyectado al modelo:
            model.put("exito", "El libro '" + libroServicio.getById(id).getTitulo() + "' fue dado de baja exitosamente.");
        } catch (Exception e) {
            // Mensaje de error inyectado al modelo:
            model.put("error", "Error al intentar dar de baja el arte: " + e.getMessage());
        }
        // Datos inyectados al modelo de "administrador.html":
            
        return "redirect:/admin";
    }   

    //Dar de alta un libro
    @GetMapping("/alta/{id}")
    public String alta(ModelMap model, @PathVariable String id) {
        try {
            libroServicio.habilitarLibro(id);
            // Mensaje de éxito inyectado al modelo:
            model.put("exito", "El libro '" + libroServicio.getById(id).getTitulo() + "' fue dado de alta exitosamente.");
        } catch (Exception e) {
            // Mensaje de error inyectado al modelo:
            model.put("error", "Error al intentar dar de baja el arte: " + e.getMessage());
        }

        return "redirect:/admin";
    }
}
