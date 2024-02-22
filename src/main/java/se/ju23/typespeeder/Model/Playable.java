package se.ju23.typespeeder.Model;

import java.util.List;

public interface Playable {
    void showRules();

    List<Word> generateWords(int limit);

    void startChallenge();

    void startWordChallenge();

    void startSentencesChallenge();

    void changeChallengeMode();
    void changeChallengeComplexity();
    void showScore();
    GameComplexity getComplexity();
    GameMode getChallengeMode();

    double getScore();

    void changeLanguage();
}
