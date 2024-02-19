package se.ju23.typespeeder.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.Repositories.WordsRepo;
import se.ju23.typespeeder.Services.InputService;
import se.ju23.typespeeder.Services.PrintService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Component
public class Game implements Playable {
    private GameMode currentMode;
    private GameComplexity currentComplexity;
    @Autowired
    private WordsRepo wordsRepo;
    @Autowired
    private PrintService printer;
    @Autowired
    private InputService inputService;
    private final int WORD_GAME_TIME = 30;
    private int score;
    private List<String> symbols = "!@#$%^&*()_+{}|:<>?".chars().mapToObj(c -> String.valueOf((char) c)).toList();

    public Game(WordsRepo wordsRepo , PrintService printer , InputService inputService) {
        this.wordsRepo = wordsRepo;
        this.printer = printer;
        this.inputService = inputService;
        this.currentComplexity = GameComplexity.EASY;
        this.currentMode = GameMode.WORDS;
    }

    public Game() {
        currentComplexity = GameComplexity.EASY;
        currentMode = GameMode.WORDS;
    }

    @Override
public void showRules(){
        switch (currentMode){
            case WORDS -> showRulesForWords();
            case SENTENCES -> showRulesForSentences();
        }
}

    @Override
    public int calculateScore(Date time, List<Word> results) {
        return 0;
    }

    @Override
    public List<Word> generateWords(int limit ) {
         return wordsRepo.findRandomWords( currentComplexity.getMinWordLength() , currentComplexity.getMaxWordLength() , limit );
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
        List<Word> words = generateWords(100);
        Thread newThread = new Thread(() -> {
            wordGame(words);
        });
        // Start the new thread
        newThread.start();
        // Create a ScheduledExecutorService
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        // Schedule a task to interrupt the new thread after 30 seconds
        executor.schedule(newThread::interrupt, WORD_GAME_TIME, TimeUnit.SECONDS);
        try {
            // Make the main thread wait for the new thread to finish
            newThread.join();
        } catch (InterruptedException e) {
            System.out.println("New thread was interrupted");
        }
        executor.shutdown();

        score = calculateScore(words);

        printer.printMessage("Your score is: " + score);


    }

    private void wordGame(List<Word> wordsToType) {
        printer.printWarning("Start typing words!");
        String userInput;
        Scanner scanner = new Scanner(System.in);
        for (Word word : wordsToType) {
            printer.printMessage(word.toString());
            userInput = scanner.nextLine();
            if(Thread.currentThread().isInterrupted()){
                return;
            }
            if (userInput.equals(word.getWord())) {
                word.setTypedCorrectly(true);
            }else{
                break;
            }
        }
    }
    private int calculateScore(List<Word> words){
        int score = 0;
        for (Word word : words) {
            if (word.isTypedCorrectly()) {
                score += word.getPoints();
            }
        }
        return score;
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
    public void setGameMode(GameMode mode) {
        currentMode = mode;

    }

    @Override
    public void setComplexity(GameComplexity value) {
        currentComplexity = value;
    }

    private void showRulesForWords(){
        printer.printWarning( "==========================================RULES=================================================== \n" +
                                        "| The game is simple, you will be given a word and you have to type it as fast as you can. \n" +
                                        "| The faster you type the word, the more points you get. \n" +
                                        "| ***REMEMBER!**** The game will be stopped if you do a mistake! \n" +
                                        "| Some words will have a spacial color.\n" +
                                        "| \tWhite words is standard \n" +
                                        "| \tYellow words give you 2 extra points \n" +
                                        "| \tRed words give you 5 extra points \n" +
                                        "| You will have "+ WORD_GAME_TIME +" seconds to type as many words as you can. \n" +
                                        "| Good luck!" +
                                        "==================================================================================================\n" );
    }

    private void showRulesForSentences(){
        printer.printWarning( "==========================================RULES===================================================\n" +
                                        "| The game is simple, you will be given a sentence and you have to type it as fast as you can. \n" +
                                        "| Good luck!" +
                                        "==================================================================================================\n" );
    }

    @Override
    public String toString() {
        return "Game.class";
    }
}
