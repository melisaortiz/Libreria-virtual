package com.libreria.libreria.controladores;

import com.libreria.libreria.errores.ErroresServicio;
import com.libreria.libreria.servicios.EditorialServicio;
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
}
