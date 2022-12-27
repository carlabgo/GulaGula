package com.gulagula.gulagula.enumeradores;

public enum Categoria {
    VEGETARIANA("Vegetariana"), 
    S_TACC("Sin TACC"), 
    VEGANA("Vegana"),
    CARNIVORA("Carnivora");
    
    private final String displayValue;
    
    private Categoria(String displayValue) {
        this.displayValue = displayValue;
    }
    
    public String getDisplayValue() {
        return displayValue;
    }
}
