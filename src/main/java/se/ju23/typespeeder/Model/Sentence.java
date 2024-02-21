package se.ju23.typespeeder.Model;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Entity
@Table( name = "sentences")
public class Sentence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "text")
    private String sentence;

    @Column(name = "complexity")
    @Enumerated(EnumType.STRING)
    private SentenceComplexity complexity;

    public Sentence(String sentence) {
        this.sentence = sentence;
        calculateComplexity();
    }

    public Sentence() {
    }

    public String getSentence() {
        return sentence;
    }
    private void  calculateComplexity(){
        if(sentence.length() < 40){
            complexity = SentenceComplexity.EASY;
        }else if(sentence.length() < 70){
            complexity = SentenceComplexity.MEDIUM;
        }else{
            complexity = SentenceComplexity.HARD;
        }
    }
}
