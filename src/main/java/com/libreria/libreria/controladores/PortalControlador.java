package com.libreria.libreria.controladores;

import com.libreria.libreria.errores.ErroresServicio;
import com.libreria.libreria.servicios.AutorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalControlador {

   
    @GetMapping("/index")
    public String index() {
        return "/index";
    }

 
    

    

}
