package se.ju23.typespeeder.Model;

import jakarta.persistence.Table;
import se.ju23.typespeeder.TextComplexity;

@Table
public class Text {
    private String text;
    private TextComplexity complexity;

    public Text(String text, TextComplexity complexity) {
        this.text = text;
        this.complexity = complexity;
    }

    public String getText() {
        return text;
    }

    public TextComplexity getComplexity() {
        return complexity;
    }
}
