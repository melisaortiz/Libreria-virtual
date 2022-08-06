package com.libreria.libreria.controladores;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.entidades.Editorial;
import com.libreria.libreria.enums.Categoria;
import com.libreria.libreria.servicios.AutorServicio;
import com.libreria.libreria.servicios.EditorialServicio;
import com.libreria.libreria.servicios.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

    @PostMapping("/crear_libro")
    public String crearLibro(ModelMap model, MultipartFile archivo, @RequestParam Long isbn,
            @RequestParam String titulo, @RequestParam Integer anio, @RequestParam String descripcion,
            @RequestParam Integer precio, @RequestParam Categoria categoria, 
            @RequestParam Integer ejemplares, String idAutor, String idEditorial) {
        Autor autor;
        Editorial editorial;
        
//         Autor autorObjeto = autorServicio.buscarPorNombre(autor);
//        Editorial editorialObjeto = editorialServicio.buscarPorNombre(editorial);
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
            if (e.getMessage() == null || titulo == null || anio == null || descripcion == null || precio == null || idAutor == null|| idEditorial == null || categoria == null) {
                model.put("error", "Error al intentar guardar el libro: faltó completar algún campo.");
            } else {
                model.put("error", "Error al intentar guardar el libro: " + e.getMessage());
            }
              System.out.println("Entro al catch");
            return "administrador.html";
        }
    }

}
