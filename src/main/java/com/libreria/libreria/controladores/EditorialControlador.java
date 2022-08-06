package com.libreria.libreria.controladores;

import com.libreria.libreria.entidades.Editorial;
import com.libreria.libreria.errores.ErroresServicio;
import com.libreria.libreria.servicios.EditorialServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {

    @Autowired
    private EditorialServicio editorialServicio;

    @PostMapping("/crear_editorial")
    public String crearEditorial(ModelMap model,
            @RequestParam String nombre) throws Exception {

        try {
            // Seteo de la editorial:
            editorialServicio.agregarEditorial(nombre);
            model.put("exito", "Editorial registrado con éxito.");

            return "redirect:/admin";

        } catch (ErroresServicio ex) {
            model.put("error", ex.getMessage()); //Con la clase modelMap podemos mostrar los errores de las excepciones en nuestro html
            model.put("nombre", nombre);

            return "administrador.html";
        }

    }

    //Controlador para modificar un autor
    @PostMapping("/modificar_editorial")
    public String modificarEditorial(ModelMap model, @RequestParam String idEditorial,
            @RequestParam String nombre) {

        Editorial editorial;
        try {

            //Seteo del id de la editorial:
            try {
                editorial = editorialServicio.getById(idEditorial);

            } catch (Exception e) {
                throw new Exception("Debe seleccionar un autor");
            }


            editorialServicio.modificarEditorial(editorial.getId(), nombre);
            // Mensaje de éxito inyectado al modelo:
            model.put("exito", "La editorial fue modificado exitosamente.");
            // Datos inyectados al modelo de "admin-autor.html":
            List<Editorial> editoriales = editorialServicio.findAll();
            model.put("editoriales", editoriales);
            return "redirect:/admin";

        } catch (Exception e) {

            // Mensaje de error inyectado al modelo:
//            Editorial editorial = editorialServicio.getById(id);
//            model.put("editorialModif", editorial);
//            model.put("error", "Error al intentar modificar la editorial: " + e.getMessage());
            return "administrador.html";
        }
    }
}
