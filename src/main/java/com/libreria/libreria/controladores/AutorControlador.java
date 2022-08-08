package com.libreria.libreria.controladores;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.enums.Pais;
import com.libreria.libreria.errores.ErroresServicio;
import com.libreria.libreria.servicios.AutorServicio;
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
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;

   
//Controlador para crear un autor
    @PostMapping("/crear_autor")
    public String crearAutor(ModelMap model, MultipartFile archivo,
            @RequestParam String nombreAutor, @RequestParam String descripcion,
            @RequestParam Pais pais) throws Exception {

        try {
            // Seteo del autor:
            autorServicio.agregarAutor(archivo, nombreAutor, descripcion, pais);
            model.put("exito", "Autor registrado con éxito.");

            return "redirect:/admin";

        } catch (ErroresServicio ex) {
            model.put("error", ex.getMessage()); //Con la clase modelMap podemos mostrar los errores de las excepciones en nuestro html
            model.put("nombreAutor", nombreAutor);
            model.put("descripcion", descripcion);
            model.put("pais", pais);
            model.put("archivo", archivo);

            return "administrador.html";
        }
    }

    //Controlador para modificar un autor
    @PostMapping("/modificar_autor")
    public String modificarAutor(ModelMap model, MultipartFile archivo, @RequestParam String idAutor,
            @RequestParam String nombre, String descripcion, Pais pais) {
        System.out.println("entra al metodo");
        Autor autor;
        try {

            //Seteo del id del autor:
            try {
                autor = autorServicio.getById(idAutor);

            } catch (Exception e) {
                throw new Exception("Debe seleccionar un autor");
            }

//            //Validaciones 
//            if (nombre == null || nombre.trim().isEmpty()){
//                nombre = autor.getNombre();
//            }
//            if (nombre == null || descripcion.trim().isEmpty()){
//                descripcion = autor.getDescripcion();
//            }
//            if (pais == null || pais.toString().trim().isEmpty()){
//                pais = autor.getPais();
//            }
//             if (archivo.isEmpty()){
//                archivo = (MultipartFile) autor.getFoto();
//            }
            autorServicio.modificarAutor(autor.getId(), archivo, nombre, descripcion, pais);
            // Mensaje de éxito inyectado al modelo:
            model.put("exito", "El autor fue modificado exitosamente.");
            // Datos inyectados al modelo de "admin-autor.html":
            List<Autor> autores = autorServicio.findAll();
            model.put("autores", autores);
            return "redirect:/admin";
            
        } catch (Exception e) {

            // Mensaje de error inyectado al modelo:
//            Autor autor = autorServicio.getById(id);
//            model.put("autorModif", autor);
//            model.put("error", "Error al intentar modificar el autor: " + e.getMessage());
            return "administrador.html";
        }
    }
    
    //Dar de baja un autor
    @GetMapping("/baja/{id}")
    public String darBaja(ModelMap model, @PathVariable String id) {
        try {
            autorServicio.deshabilitarAutor(id);
            // Mensaje de éxito inyectado al modelo:
            model.put("exito", "El autor '" + autorServicio.getById(id).getNombre()+ "' fue dado de baja exitosamente.");
        } catch (Exception e) {
            // Mensaje de error inyectado al modelo:
            model.put("error", "Error al intentar dar de baja el autor: " + e.getMessage());
        }
        // Datos inyectados al modelo de "administrador.html":
            
        return "redirect:/admin";
    }   

    //Dar de alta un libro
    @GetMapping("/alta/{id}")
    public String alta(ModelMap model, @PathVariable String id) {
        try {
            autorServicio.habilitarAutor(id);
            // Mensaje de éxito inyectado al modelo:
            model.put("exito", "El libro '" + autorServicio.getById(id).getNombre()+ "' fue dado de alta exitosamente.");
        } catch (Exception e) {
            // Mensaje de error inyectado al modelo:
            model.put("error", "Error al intentar dar de baja el arte: " + e.getMessage());
        }

        return "redirect:/admin";
    }

}
