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

    @Column(name = "gameMode")
    private  GameMode gameMode;
    @Column(name = "result")
    private int result;


    public Result(long id, Player player , GameMode gameMode, int result) {
        this.id = id;
        this.player = player;
        this.result = result;
        this.gameMode = gameMode;
    }

    public Result() {
    }
}
