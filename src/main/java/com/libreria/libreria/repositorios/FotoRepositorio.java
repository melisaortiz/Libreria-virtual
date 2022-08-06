/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.libreria.repositorios;

import com.libreria.libreria.entidades.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * (FotoRepositorio) debe contener los métodos necesarios para
 * guardar/actualizar fotos en la base de datos o dar de baja según corresponda.
 * Extiende de JpaRepository: será un repositorio de Foto con la Primary Key de
 * tipo String.
 *
 * Los métodos save(), findById() y delete() se implementan por JpaRepository.
 *
 */
@Repository
public interface FotoRepositorio extends JpaRepository<Foto, String> {

}
