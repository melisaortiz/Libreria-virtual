package com.libreria.libreria.controladores;

import com.libreria.libreria.entidades.Usuario;
import com.libreria.libreria.errores.ErroresServicio;
import com.libreria.libreria.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

//    @Autowired
//    private Usuario usuario;

   
    @PostMapping("/registrar_usuario")
    public String registrarUsuario(MultipartFile archivo, ModelMap modelo, @RequestParam String nombre, @RequestParam String dni, @RequestParam String telefono, @RequestParam String mail, @RequestParam String clave, @RequestParam String clave2) throws Exception {
        try {

            usuarioServicio.registrar(archivo, nombre, dni, telefono, mail, clave, clave2);

            modelo.put("exito", "Usuario registrado con éxito.");
            return "libros.html";

        } catch (ErroresServicio ex) {
            modelo.put("error", ex.getMessage()); //Con la clase modelMap podemos mostrar los errores de las excepciones en nuestro html
            modelo.put("nombre", nombre);
            modelo.put("dni", dni);
            modelo.put("telefono", telefono);
            modelo.put("mail", mail);
            modelo.put("clave", clave);

            return "login.html";
        }
    }
//
//    @GetMapping("/modificar_autor")
//    public String modificarAutor(ModelMap modelo) {
//
//        try {
//            List<Autor> autores = autorServicio.listarTodos();
//            modelo.put("autores", autores);
//        } catch (Exception ex) {    
//            modelo.put("error", ex.getMessage()); //Con la clase modelMap podemos mostrar los errores de las excepciones en nuestro html
//            return "/crear_autor";
//        }
//
//        return "/modificar_autor";
//    }
//
//    @PostMapping("/modificar_autor")
//    public String editarAutor(ModelMap modelo, @RequestParam String id, @RequestParam String nombreNuevo, MultipartFile archivo, String descripcion, Pais pais) throws Exception {
//        try {
//
//            autorServicio.modificarAutor(id,archivo, nombreNuevo, descripcion, pais);
//            System.out.println("Nombre: " + nombreNuevo);
//            modelo.put("exito", "Autor modificado con éxito.");
//            return "/modificar_autor";
//
//        } catch (ErroresServicio ex) {
//
//            modelo.put("error", ex.getMessage()); //Con la clase modelMap podemos mostrar los errores de las excepciones en nuestro html
//            return "/modificar_autor";
//        }
//
//    }
//
//    @GetMapping("/listar_autores")
//    public String listarAutores(ModelMap modelo) {
//        modelo.addAttribute("listarAutores", autorServicio.listarTodos());
//        return "/listar_autores";
//    }
//
//    @PostMapping("/altaBaja")
//    public String altaBajaAutor(ModelMap modelo, @RequestParam String id) throws Exception {
//        try {
//            if (autor.isAlta() == true) {
//                autorServicio.deshabilitarAutor(id);
//                modelo.put("altaBaja", "Dar de baja");
//            } else if (autor.isAlta() == false) {
//                autorServicio.habilitarAutor(id);
//                modelo.put("altaBaja", "Dar de alta");
//            }
//
//        } catch (ErroresServicio ex) {
//            modelo.put("mensaje1", "Error al deshabilitar Autor " + ex.getMessage());
//            modelo.put("clase1", "danger");
//            return "listar_autores";
//        }
//         return "listar_autores";
//    }
//
////    @PostMapping("/autor/habilitar")
////    public String habilitarAutor(ModelMap modelo, @RequestParam String idA, RedirectAttributes redirectAttrs) {
////        try {
////            autorServicio.habilitar(idA);
////            redirectAttrs
////                    .addFlashAttribute("mensaje", "Autor habilitado correctamente")
////                    .addFlashAttribute("clase", "success");
////
////            return "redirect:/listarautor";
////        } catch (ErroresServicio ex) {
////            modelo.put("mensaje1", "Error al deshabilitar Autor " + ex.getMessage());
////            modelo.put("clase1", "danger");
////            return "listar_autores";
////        }
////    }
//

}
