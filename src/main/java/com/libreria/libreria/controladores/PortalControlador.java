package com.libreria.libreria.controladores;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.entidades.Editorial;
import com.libreria.libreria.entidades.Foto;
import com.libreria.libreria.entidades.Libro;
import com.libreria.libreria.enums.Categoria;
import com.libreria.libreria.enums.Pais;
import com.libreria.libreria.servicios.AutorServicio;
import com.libreria.libreria.servicios.EditorialServicio;
import com.libreria.libreria.servicios.FotoServicio;
import com.libreria.libreria.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private LibroServicio libroServicio;
//    @Autowired
//    Pais pais;

    @Autowired
    private EditorialServicio editorialServicio;

//      @Autowired
//    private UsuarioServicio usuarioServicio;
    @Autowired
    private AutorServicio autorServicio;
//
//    @Autowired
//    private Usuario usuario;
    @Autowired
    private FotoServicio fotoServicio;

   
    @GetMapping("/index")
    public String index(ModelMap model) {
        return "/index";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }
    
    @GetMapping("/editar_perfil")
    public String editarPerfil() {      
        return "editar_perfil.html";
    }

    @GetMapping("/iniciar-sesion")
    public String inicioSesion() {
        return "usuario.html";
    }
    
   
    //Inyeccion de datos sobre libros en libros.html
    @GetMapping("/libros")
    public String libros(ModelMap model) {
        // Datos inyectados para mostrar el listado de libros en "libros.html"
        List<Libro> libros = libroServicio.findAll();
        model.put("libros", libros);

        //Listado para mostrar los libros activos
        List<Libro> librosActivos = libroServicio.findAllAltaIsTrue();
        model.put("librosActivos", librosActivos);
        return "libros.html";
    }

    //Inyeccion de datos sobre el autor en libros.html
    @GetMapping("/autor")
    public String autores(ModelMap model) {
        // Datos inyectados para mostrar el listado de autores en "libros.html" 
        List<Autor> autores = autorServicio.findAll();
        model.put("autores", autores);

        //Listado para mostrar los libros activos
        List<Autor> autoresActivos = autorServicio.findAllAltaIsTrue();
        model.put("autoresActivos", autoresActivos);
        return "libros.html";
    }

    //Inyeccion de datos en administrador.html
    @GetMapping("/admin")
    public String admin(ModelMap model) {
        // Datos inyectados al modelo de "administrador.html" para crear un libro, un autor y/o una editorial:
        List<Autor> autores = autorServicio.findAll();
        model.put("autores", autores);
        List<Editorial> editoriales = editorialServicio.findAll();
        model.put("editoriales", editoriales);
        model.addAttribute("categorias", Categoria.values());
        model.addAttribute("paises", Pais.values());

        List<Libro> libros = libroServicio.findAll();
        model.put("libros", libros);

        //Listado para mostrar los libros activos
        List<Libro> librosActivos = libroServicio.findAllAltaIsTrue();
        model.put("librosActivos", librosActivos);

        //Listado para mostrar los autores activos
        List<Autor> autoresActivos = autorServicio.findAllAltaIsTrue();
        model.put("autoresActivos", autoresActivos);

        //Listado para mostrar las editoriales activas
        List<Editorial> editorialesActivas = editorialServicio.findAllAltaIsTrue();
        model.put("editorialesActivas", editorialesActivas);

        return "administrador.html";
    }

    //Inyeccion de datos en usuario.html
    @GetMapping("/usuario")
    public String usuario(ModelMap model) {
        // Datos inyectados para mostrar el listado de libros en "libros.html"
        List<Libro> libros = libroServicio.findAll();
        model.put("libros", libros);

        //Listado para mostrar los libros activos
        List<Libro> librosActivos = libroServicio.findAllAltaIsTrue();
        model.put("librosActivos", librosActivos);

        // Datos inyectados para mostrar el listado de autores en "usuario.html"
        List<Autor> autores = autorServicio.findAll();
        model.put("autores", autores);

        //Listado para mostrar los autores activos
        List<Autor> autoresActivos = autorServicio.findAllAltaIsTrue();
        model.put("autoresActivos", autoresActivos);

        //Listado para mostrar las editoriales activas
        List<Editorial> editorialesActivas = editorialServicio.findAllAltaIsTrue();
        model.put("editorialesActivas", editorialesActivas);

        return "usuario.html";
    }

    //Inyeccion de fotos en libro.html
    @GetMapping("/fotos")
    public String foto(ModelMap model) {
        // Datos inyectados para mostrar el listado de libros en "libros.html"
        List<Foto> fotos = fotoServicio.findAll();
        model.put("fotos", fotos);

        return "libro.html";
    }

    //Inyeccion de fotos en usuario.html
    @GetMapping("/foto_usuario")
    public String foto_usuario(ModelMap model) {
        List<Foto> fotos = fotoServicio.findAll();
        model.put("fotos", fotos);

        return "usuario.html";
    }

    /**
     * Vista principal para los usuarios logueados. Para los ADMIN se ve el Menú
     * Administrativo (acceso a Dashboard, Gestión de artes/Autores/Editoriales
     * y Gestión de Préstamos); para los USUARIOS se ve la lista de todos los
     * artes activos disponibles para solicitar préstamos, además del cuadro
     * para filtrar la búsqueda por autor.
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USUARIO')")
    @GetMapping("/inicio")
    public String inicio(ModelMap model) {
//         try {
//            arteServicio.sumaCarrito();
//        } catch (Exception e) {
//            // Mensaje de error inyectado al modelo:
//            model.put("error", "Error al intentar sumar el precio final: " + e.getMessage());
//        }
        // Datos inyectados al modelo de "admin-arte.html":
//        List<Long> sumaCarrito = arteServicio.sumaCarrito();
//        model.addAttribute("sumaCarrito", sumaCarrito);
//        List<Arte> artesDeCompra = arteServicio.listarDeCompra();
//        model.addAttribute("artesDeCompra", artesDeCompra);
//        List<Autor> autores = autorServicio.findAll();
//        model.addAttribute("autores", autores);
//        model.addAttribute("autorSelected", null);
//        List <Arte> artes = arteServicio.findAll();
//        model.addAttribute("artes", artes);

        return "index.html";
    }
}
