package se.ju23.typespeeder.Model;

import jakarta.persistence.*;

@Entity
@Table( name = "words")
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "word", unique = true, nullable = false)
    String word;

    public Word() {
    }

    public Word(String word) {
        this.word = word;
        this.id = 1;
    }

    public long getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
