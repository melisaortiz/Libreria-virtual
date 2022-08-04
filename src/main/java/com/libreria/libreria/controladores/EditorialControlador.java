//package com.libreria.libreria.controladores;
//
//import com.libreria.libreria.errores.ErroresServicio;
//import com.libreria.libreria.servicios.EditorialServicio;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//@RequestMapping("/editorial")
//public class EditorialControlador {
//
//    @Autowired
//    private EditorialServicio editorialServicio;
//
//    @GetMapping("/ediorial")
//    public String editorial() {
//        return "/editorial";
//    }
//
//    @GetMapping("/crear_editorial")
//    public String crearEditorial() {
//        return "/crear_editorial";
//    }
//
//    @GetMapping("/modificar_editorial")
//    public String modificarEditorial() {
//        return "/modificar_editorial";
//    }
//
//    @GetMapping("/listar_editoriales")
//    public String listarEditoriales() {
//        return "/listar_editoriales";
//    }
//
//    @PostMapping("/crear_editorial")
//    public String crearEditorial(ModelMap modelo, @RequestParam String nombre) {
//        try {
//            editorialServicio.agregarEditorial(nombre);
//            System.out.println("Nombre: " + nombre);
//            modelo.put("exito", "Autor registrado con éxito.");
//            return "/crear_editorial";
//
//        } catch (ErroresServicio ex) {
//            modelo.put("error", ex.getMessage()); //Con la clase modelMap podemos mostrar los errores de las excepciones en nuestro html
//            return "/crear_editorial";
//        }
//
//    }
//}
//
