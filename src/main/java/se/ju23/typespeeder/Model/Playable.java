package se.ju23.typespeeder.Model;

import java.util.Date;
import java.util.List;

public interface Playable {
    void showRules();

    List<Word> generateWords(int limit);

    void changeGameStatus();
    void startGame();

    void startWordGame();

    void startSentencesGame();

    void changeGameMode();
    void changeGameComplexity();
    void showScore();
    GameComplexity getComplexity();
    GameMode getGameMode();

    double getScore();
}
