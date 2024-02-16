package se.ju23.typespeeder;

import org.springframework.stereotype.Component;


public enum PrintColors {
    GREEN("\u001B[32m"),
    RED("\u001B[31m"),
    YELLOW("\u001B[33m"),
    RESET("\u001B[0m");
    private final String  color;
    PrintColors(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }


}
