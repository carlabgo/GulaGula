package com.gulagula.gulagula.enumeradores;

public enum Temperatura {
    FRIO("Frio"),
    CALIENTE("Caliente");

    private final String displayValue;

    private Temperatura(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
