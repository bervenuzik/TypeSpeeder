package se.ju23.typespeeder.Model;

import jakarta.persistence.*;
import se.ju23.typespeeder.PrintColors;

@Entity
@Table( name = "words")
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "word", unique = true, nullable = false)
    private String word;
    @Transient
    private int points;
    @Transient
    private boolean typedCorrectly;

    @Transient
    private WordComplexity complexity;

    public Word() {

    }

    public Word(String word) {
        this.word = word;
        this.id = 1;
        this.typedCorrectly  = false;
        calculateComplexity();
    }


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
    @PostLoad
    private void calculateComplexity(){
        if(word.length() <= GameComplexity.EASY.getMaxWordLength()){
             complexity = WordComplexity.EASY;
        }else if(word.length() <= GameComplexity.MEDIUM.getMaxWordLength()){
             complexity = WordComplexity.MEDIUM;
        }else{
             complexity = WordComplexity.HARD;
        }
        points = complexity.getPoints();
    }

    public int getPoints() {
        return points;
    }

    public boolean isTypedCorrectly() {
        return typedCorrectly;
    }

    public void setTypedCorrectly(boolean typedCorrectly) {
        this.typedCorrectly = typedCorrectly;
    }

    @Override
    public String toString() {
        switch (complexity){
            case EASY -> {return word;}
            case MEDIUM -> {return PrintColors.YELLOW.getColor() + word + PrintColors.RESET.getColor();}
            case HARD -> {return PrintColors.RED.getColor() + word + PrintColors.RESET.getColor();}
            default -> {return word;}
            }
        }
}
