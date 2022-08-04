
package com.libreria.libreria.enums;

/**
 *
 * Enumera las categorías a las que puede pertenecer un libro
 */
public enum Categoria {
    
    Ciencia_Ficcion("Ciencia ficción"),
    Historia("Historia"),
    Literatura("Literatura"),
    Cocina("Cocina"),
    Drama("Drama"),
    Humor("Humor"),
    Salud("Salud"),
    Arte("Arte"),
    Idiomas("Idiomas"),
    Ciencia("Ciencia"),
    Matematicas("Matemáticas"),
    Novelas("Novelas"),
    Economia_Y_Finanzas("Economía y finanzas"),
    Politica("Política");
    
   private final String displayName;

    Categoria(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() { return displayName; }
 
}
