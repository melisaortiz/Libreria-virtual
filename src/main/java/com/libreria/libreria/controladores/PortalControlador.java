package com.libreria.libreria.controladores;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.entidades.Editorial;
import com.libreria.libreria.enums.Categoria;
import com.libreria.libreria.enums.Pais;
import com.libreria.libreria.servicios.AutorServicio;
import com.libreria.libreria.servicios.EditorialServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PortalControlador {

//    @Autowired
//        private LibroServicio libroServicio;
//    @Autowired
//    Pais pais;

    @Autowired
    private EditorialServicio editorialServicio;

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/index")
    public String index() {
        return "/index";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/libros")
    public String libros() {
        return "libros.html";
    }

    @GetMapping("/admin")
    public String admin(ModelMap model) {
        // Datos inyectados al modelo de "administrador.html" para crear un libro, un autor y/o una editorial:
        List<Autor> autores = autorServicio.findAll();
        model.put("autores", autores);
        List<Editorial> editoriales = editorialServicio.findAll();
        model.put("editoriales", editoriales);
        model.addAttribute("categorias", Categoria.values());
        model.addAttribute("paises", Pais.values());
        
        return "administrador.html";
    }

    @GetMapping("/usuario")
    public String usuario() {
        return "usuario.html";
    }
}
