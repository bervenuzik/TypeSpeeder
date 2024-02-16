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
    private Player playerId;
    @Column(name = "result")
    private int result;


    public Result(long id, Player playerId, int result) {
        this.id = id;
        this.playerId = playerId;
        this.result = result;
    }

    public Result() {
    }
}
