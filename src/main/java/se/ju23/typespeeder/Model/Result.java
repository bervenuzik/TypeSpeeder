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
    private double result;

    public Result(Player player , GameMode gameMode,GameComplexity complexity , double result) {
        this.player = player;
        this.result = result;
        this.complexity = complexity;
        this.gameMode = gameMode;
        id = 1;
    }

    public Result() {
    }

    @Override
    public String toString() {
        return String.format("player= %-15s gameMode= %-12s complexity= %-12s result= %-12.2f", player.getNickname(), gameMode, complexity, result);
    }
}
