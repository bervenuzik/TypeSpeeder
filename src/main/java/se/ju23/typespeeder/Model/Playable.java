package se.ju23.typespeeder.Model;

import java.util.Date;
import java.util.List;

public interface Playable {
    void showRules();
    int calculateScore(Date time, List<Word> results);

    List<Word> generateWords(int limit);

    void changeGameStatus();
   void startGame();

    void startWordGame();

    void stopWordGame();
    void startSentencesGame();
    void stopSentencesGame();

    void setGameMode(GameMode mode);

    void setComplexity(GameComplexity value);
}
