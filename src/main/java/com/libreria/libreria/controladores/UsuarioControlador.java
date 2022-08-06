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

}
