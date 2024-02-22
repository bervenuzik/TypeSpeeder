package se.ju23.typespeeder.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "messeges")
public class NewsLetter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Column(name = "text",nullable = false)
    String content;

    @ManyToOne
    @JoinColumn(name = "author" , nullable = false)
    private Player author;
    @Column(name = "time" , nullable = false)
    private LocalDateTime publishDateTime;


    public NewsLetter() {
    }

    @PostLoad
    private void convertToDateTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        publishDateTime.format(formatter);
    }

    public NewsLetter(String content, Player author, LocalDateTime publishDateTime) {
        this.content = content;
        this.author = author;
        this.publishDateTime = publishDateTime;
        convertToDateTime();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if(contentValidation(content)){
            this.content = content;
        }
    }

    public boolean contentValidation(String content){
        return content.length() >= 100 && content.length() <= 200;
    }

    public void setPublishDateTime(LocalDateTime publishDateTime) {
        this.publishDateTime = publishDateTime;
    }

    public String getPublishDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return publishDateTime.format(formatter);

    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timeToPublish = publishDateTime.format(formatter);
        return "NewsLetter{" +
                "content='" + content + '\'' +
                ", time=" + timeToPublish +
                '}';
    }

    public Player getAuthor() {
        return author;
    }
}
