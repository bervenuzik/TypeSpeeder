package se.ju23.typespeeder.Model;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

@Entity
@Table( name = "players")
public class Player {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @NonNull
    private long id;

    @Column( name = "username" , unique = true , nullable = false)
    private String username;

    @Column( name = "password" ,nullable = false)
    private String password;
    @Column( name = "nickname" ,unique = true, nullable = false)
    private String nickname;
    @Column( name = "type" ,nullable = false)
    @Enumerated(EnumType.STRING)
    private PlayerType type;


    public Player(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }
    public Player() {
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    private void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

//    @Override
//    public String toString() {
//        return "Player{" +
//                "nickname='" + nickname + '\'' +
//                '}';
//    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", type=" + type +
                '}';
    }
}
