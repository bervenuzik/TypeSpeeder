package se.ju23.typespeeder.Model;

import jakarta.persistence.*;

@Entity
@Table( name = "results")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn (name = "playerId")
    private Player player;

    @Enumerated(EnumType.STRING)
    @Column(name = "gameMode")
    private  GameMode gameMode;

    @Enumerated(EnumType.STRING)
    @Column(name = "complexity")
    private GameComplexity complexity;
    @Column(name = "result")
    private int result;

    public Result(Player player , GameMode gameMode,GameComplexity complexity , int result) {
        this.player = player;
        this.result = result;
        this.complexity = complexity;
        this.gameMode = gameMode;
    }

    public Result() {
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", player=" + player +
                ", gameMode=" + gameMode +
                ", complexity=" + complexity +
                ", result=" + result +
                '}';
    }
}
