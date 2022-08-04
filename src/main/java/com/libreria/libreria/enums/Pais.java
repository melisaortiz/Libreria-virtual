package com.libreria.libreria.enums;

/**
 *
 * El enum Pais enumera los países de donde pueden provenir los autores
 */

public enum Pais {
    Argentina("Argentina"),
    Chile("Chile"),
    Peru("Perú"),
    Brasil("Brasil"),
    Uruguay("Uruguay"),
    Paraguay("Paraguay"),
    Bolivia("Bolivia"),
    Ecuador("Ecuador"),
    Venezuela("Venezuela"),
    Colombia("Colombia"),
    Costa_Rica("Costa Rica"),
    Mexico("México"),
    Estados_Unidos("Estados Unidos"),
    Canada("Canadá"),
    Cuba("Cuba"),
    Republica_Dominicana("República Dominicana"),
    Puerto_Rico("Puerto Rico");

    private final String displayName;

    Pais(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }
}
