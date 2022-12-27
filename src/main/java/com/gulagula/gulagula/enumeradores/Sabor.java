package com.gulagula.gulagula.enumeradores;

public enum Sabor {
    DULCE("Dulce"),
    SALADO("Salado"),
    PICANTE("Picante"),
    AGRIDULCE("Agridulce");

    private final String displayValue;

    private Sabor(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
