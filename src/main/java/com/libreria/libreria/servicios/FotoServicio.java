
package com.libreria.libreria.servicios;

import com.libreria.libreria.entidades.Foto;
import com.libreria.libreria.repositorios.FotoRepositorio;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *Esta clase tiene la responsabilidad de llevar adelante las funcionalidades
 * necesarias para administrar fotos (creación y modificación).
 */
@Service
public class FotoServicio {
    
    @Autowired
    private FotoRepositorio fotoRepositorio;

    /**
     * Método para guardar la foto; "multipartfile" es la interfaz que modela el
     * archivo donde se almacena la foto.
     *
     * @param archivo
     * @return
     * @throws IOException
     */
    @Transactional
    public Foto guardar(MultipartFile archivo) throws IOException {
        if (archivo != null && !archivo.isEmpty()) {
            try {
                Foto foto = new Foto();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                // La lectura del contenido es la que puede generar un error, por eso está todo en un try/catch:
                foto.setContenido(archivo.getBytes());
                return fotoRepositorio.save(foto);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    /**
     * Método para actualizar una foto.
     * @param idFoto
     * @param archivo
     * @return
     * @throws IOException
     */
    @Transactional
    public Foto actualizar(String idFoto, MultipartFile archivo) throws IOException {
        if (archivo != null) {
            try {
                Foto foto = new Foto();
                if (idFoto != null) {
                    foto = fotoRepositorio.getById(idFoto);
                }
                // Se actualizan los datos de la foto:
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                // La lectura del contenido es la que puede generar un error, por eso está todo en un try/catch:
                foto.setContenido(archivo.getBytes());
                return fotoRepositorio.save(foto);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
    
    
    /**
     * Lista todos las fotos de los libros
     *
     * @return
     */
    public List<Foto> findAll() {
        return fotoRepositorio.findAll();
    }
    
}
