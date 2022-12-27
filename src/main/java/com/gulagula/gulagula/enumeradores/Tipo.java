package com.gulagula.gulagula.enumeradores;

public enum Tipo {
    COMIDA("Comida"),
    POSTRE("Postre"),
    BEBIDA("Bebida");

    private final String displayValue;

    private Tipo(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
