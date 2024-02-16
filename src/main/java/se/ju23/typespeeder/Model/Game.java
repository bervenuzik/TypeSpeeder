package se.ju23.typespeeder.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.Repositories.WordsRepo;
import se.ju23.typespeeder.Services.PrintService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class Game implements Playable {
    private GameMode currentMode;
    private GameComplexity currentComplexity;
    @Autowired
    private WordsRepo wordsRepo;
    @Autowired
    private PrintService printService;

    public Game(WordsRepo wordsRepo , PrintService printService) {
        this.wordsRepo = wordsRepo;
    }

    public Game() {
    }

    @Override
public void showRules(){
    System.out.println("Welcome to TypeSpeeder! \n" +
            "The game is simple, you will be given a word and you have to type it as fast as you can. \n" +
            "The faster you type the word, the more points you get. \n" +
            "Some words will have a spacial collor.\n" +
            "\tWhite words is standart \n" +
            "\tYellow words give you 2 extra points \n" +
            "\tRed words give you 5 extra points \n" +
            "You will have 30 seconds to type as many words as you can. \n" +
            "Good luck!");
}

    @Override
    public int calculateScore(Date time, List<Word> results) {
        return 0;
    }

    @Override
    public List<Word> generateWords(int limit ) {
        switch (currentComplexity){
            case EASY ->    {   return wordsRepo.findRandomWords(limit , currentComplexity.EASY.getMinWordLength() , currentComplexity.EASY.getMaxWordLength());      }
            case MEDIUM ->  {   return wordsRepo.findRandomWords(limit , currentComplexity.MEDIUM.getMinWordLength() , currentComplexity.MEDIUM.getMaxWordLength());  }
            case HARD ->    {   return wordsRepo.findRandomWords(limit , currentComplexity.HARD.getMinWordLength() , currentComplexity.HARD.getMaxWordLength());      }
            default ->      {   return new ArrayList<Word>();                                                                                           }
        }
    }

    @Override
    public void changeGameStatus() {

    }

    @Override
    public void startGame() {
    switch (currentMode){
        case WORDS -> startWordGame();
        case SENTENCES -> startSentencesGame();
    }
    }

    @Override
    public void startWordGame() {

    }

    @Override
    public void stopWordGame() {

    }

    @Override
    public void startSentencesGame() {

    }

    @Override
    public void stopSentencesGame() {

    }

    @Override
    public void changeGameMode(GameMode mode) {
        currentMode = mode;

    }


}
