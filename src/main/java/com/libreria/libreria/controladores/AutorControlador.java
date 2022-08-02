package com.libreria.libreria.controladores;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.errores.ErroresServicio;
import com.libreria.libreria.servicios.AutorServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;

    private Autor autor;

    @GetMapping("/autor")
    public String autor() {
        return "/autor";
    }

    @GetMapping("/crear_autor")
    public String crearAutor() {
        return "/crear_autor";
    }

    @PostMapping("/registrar_autor")
    public String registrarAutor(ModelMap modelo, @RequestParam String nombre) {
        try {

            autorServicio.crear(nombre);
            System.out.println("Nombre: " + nombre);
            modelo.put("exito", "Autor registrado con éxito.");
            return "/crear_autor";

        } catch (ErroresServicio ex) {

            modelo.put("error", ex.getMessage()); //Con la clase modelMap podemos mostrar los errores de las excepciones en nuestro html
            return "/crear_autor";
        }
    }

    @GetMapping("/modificar_autor")
    public String modificarAutor(ModelMap modelo) {

        try {
            List<Autor> autores = autorServicio.listarTodos();
            modelo.put("autores", autores);
        } catch (Exception ex) {    
            modelo.put("error", ex.getMessage()); //Con la clase modelMap podemos mostrar los errores de las excepciones en nuestro html
            return "/crear_autor";
        }

        return "/modificar_autor";
    }

    @PostMapping("/modificar_autor")
    public String editarAutor(ModelMap modelo, @RequestParam String id, @RequestParam String nombreNuevo) {
        try {

            autorServicio.modificar(id, nombreNuevo);
            System.out.println("Nombre: " + nombreNuevo);
            modelo.put("exito", "Autor modificado con éxito.");
            return "/modificar_autor";

        } catch (ErroresServicio ex) {

            modelo.put("error", ex.getMessage()); //Con la clase modelMap podemos mostrar los errores de las excepciones en nuestro html
            return "/modificar_autor";
        }

    }

    @GetMapping("/listar_autores")
    public String listarAutores(ModelMap modelo) {
        modelo.addAttribute("listarAutores", autorServicio.listarTodos());
        return "/listar_autores";
    }

    @PostMapping("/altaBaja")
    public String altaBajaAutor(ModelMap modelo, @RequestParam String id) {
        try {
            if (autor.getAlta() == true) {
                autorServicio.deshabilitar(id);
                modelo.put("altaBaja", "Dar de baja");
            } else if (autor.getAlta() == false) {
                autorServicio.habilitar(id);
                modelo.put("altaBaja", "Dar de alta");
            }

        } catch (ErroresServicio ex) {
            modelo.put("mensaje1", "Error al deshabilitar Autor " + ex.getMessage());
            modelo.put("clase1", "danger");
            return "listar_autores";
        }
         return "listar_autores";
    }

//    @PostMapping("/autor/habilitar")
//    public String habilitarAutor(ModelMap modelo, @RequestParam String idA, RedirectAttributes redirectAttrs) {
//        try {
//            autorServicio.habilitar(idA);
//            redirectAttrs
//                    .addFlashAttribute("mensaje", "Autor habilitado correctamente")
//                    .addFlashAttribute("clase", "success");
//
//            return "redirect:/listarautor";
//        } catch (ErroresServicio ex) {
//            modelo.put("mensaje1", "Error al deshabilitar Autor " + ex.getMessage());
//            modelo.put("clase1", "danger");
//            return "listar_autores";
//        }
//    }
}
