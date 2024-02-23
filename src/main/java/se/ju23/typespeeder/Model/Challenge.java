package se.ju23.typespeeder.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.Repositories.SentencesRepo;
import se.ju23.typespeeder.Repositories.WordsRepo;
import se.ju23.typespeeder.Services.InputService;
import se.ju23.typespeeder.Services.MenuService;
import se.ju23.typespeeder.Services.PrintService;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class Challenge implements Playable {
    private GameMode gameMode;
    private GameComplexity complexity;
    @Autowired
    private WordsRepo wordRepo;
    @Autowired
    private PrintService printer;
    @Autowired
    private InputService inputService;
    @Autowired
    private SentencesRepo sentenceRepo;
    @Autowired
    MenuService menu;
    private final int WORD_GAME_TIME_SECONDS = 30;
    private  final int SECONDS_CONVERSION = 1_000_000_000;
    private double score;

    private Language language;
    private  CountDownLatch latch = new CountDownLatch(1);

    public Challenge(WordsRepo wordService,SentencesRepo sentenceRepo , PrintService printer , InputService inputService, MenuService menu) {
        this.wordRepo = wordService;
        this.printer = printer;
        this.inputService = inputService;
        this.sentenceRepo = sentenceRepo;
        this.menu = menu;
        this.complexity = GameComplexity.EASY;
        this.gameMode = GameMode.WORDS;
        this.language = Language.ENGLISH;
    }

    public Challenge() {
        complexity = GameComplexity.EASY;
        gameMode = GameMode.SENTENCES;
        language = Language.ENGLISH;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;

    }

    @Override
    public void showRules(){
        switch (gameMode){
            case WORDS -> showRulesForWords();
            case SENTENCES -> showRulesForSentences();
        }
        printer.printMessage(String.valueOf(gameMode.ordinal()));
    }


    private int calculateScoreWords(List<Word> words) {
        int score = 0;
        for (Word word : words) {
            if (word.isTypedCorrectly()) {
                score += word.getPoints();
            }
        }
        return score;
    }



    @Override
    public List<Word> generateWords(int limit ) {
        List<Word> words = wordRepo.generateRandomWords( complexity.getMinWordLength() , complexity.getMaxWordLength() , limit );
        makeWordsMoreComplex(words);
        return words;
    }


    @Override
    public void startChallenge() {
        score = 0;
        switch (gameMode){
            case WORDS -> startWordChallenge();
            case SENTENCES -> startSentencesChallenge();
        }
    }
    @Override
    public GameMode getChallengeMode() {
        return gameMode;
    }

    public GameComplexity getComplexity() {
        return complexity;
    }



    @Override
    public double getScore() {
        return score;
    }

    @Override
    public void startWordChallenge() {
        List<Word> words = generateWords(80);
        Thread newThread = new Thread(() -> {
            wordChallenge(words);
        });
        // Start the new thread
        newThread.start();
        // Create a ScheduledExecutorService
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        // Schedule a task to interrupt the new thread after 30 seconds
        executor.schedule(newThread::interrupt, WORD_GAME_TIME_SECONDS, TimeUnit.SECONDS);
        try {
            // Make the main thread wait for the new thread to finish
            newThread.join();
        } catch (InterruptedException e) {
            System.out.println("New thread was interrupted");
        }
        executor.shutdown();

        score = calculateScoreWords(words);
    }

    private void wordChallenge(List<Word> wordsToType) {
        printer.printWarning("Start typing words!");
        String userInput;
        for (Word word : wordsToType) {
            printer.printMessage(word.toString());
            userInput = inputService.getUsersInput();
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


    @Override
    public void startSentencesChallenge() {
        SentenceComplexity sentenceComplexity;
        switch (complexity){
            case EASY -> sentenceComplexity = SentenceComplexity.EASY;
            case MEDIUM -> sentenceComplexity = SentenceComplexity.MEDIUM;
            case HARD -> sentenceComplexity = SentenceComplexity.HARD;
            default -> sentenceComplexity = SentenceComplexity.EASY;
        }

        List<Word> wordsToType = generateSentences(sentenceComplexity);
        startCountDown();

        try {
            latch.await();// Wait for the countdown to finish
            latch = new CountDownLatch(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sentenceChallenge(wordsToType);

    }

    private List<Word> generateSentences(SentenceComplexity sentenceComplexity) {
        List<Sentence> sentences = sentenceRepo.findSentencesByComplexity(sentenceComplexity);
        Sentence sentence = sentences.get(new Random().nextInt(sentences.size()));
        String sentenceToType = sentence.getSentence();

        List<Word> words = new ArrayList<>();
        for (String word : sentenceToType.split(" +")) {
            words.add(new Word(word));
        }
        makeWordsMoreComplex(words);
        return words;
    }

    private void sentenceChallenge(List<Word> wordsToType) {
        String userInput;
        printSentence(wordsToType);
        long startTime = System.nanoTime();
        userInput = inputService.getUsersInput();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / SECONDS_CONVERSION;
        printer.printMessage("Your time is: " + duration + " seconds");
        calculateScoreSentences(wordsToType, userInput, duration);
    }

    private void printSentence(List<Word> wordsToType){
        StringBuilder stringBuilder = new StringBuilder();
        printer.printWarning("Start typing sentence!");
        for (Word word: wordsToType) {
            stringBuilder.append(word.getWord() + " ");
        }
        printer.printMessage(stringBuilder.toString());
    }

    private void calculateScoreSentences(List<Word> wordsToType, String userInput , long duration){
        String[] userWords = userInput.split(" +");
        int i = 0;
        boolean allRight = true;
        for (String word : userWords) {
            if(word.equals(wordsToType.get(i).getWord())){
                score += wordsToType.get(i).getPoints();
                if(wordsToType.get(i).isModified()){
                    score += 2;
                }
                i++;
            }else{
            allRight = false;
            }
        }
        double  timeForBonus = (complexity.getTimeBounderies() - duration) * 0.3;
        String formatedTime = String.format("%.2f", timeForBonus);
        formatedTime = formatedTime.replace(",",".");
        timeForBonus = Double.parseDouble(formatedTime);
        if(timeForBonus > 0 && allRight){
            score += timeForBonus;
            printer.printMessage("You got extra points for time: " + timeForBonus);
        }else if(timeForBonus < 0) {
            score -= timeForBonus;
            printer.printMessage("You are out of : "+complexity.getTimeBounderies()  +" seconds, you will get minus " + timeForBonus + "points");
        }
        if(score < 0){
            score = 0;
        }
    }
    private void startCountDown(){
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        // Schedule a task to run every second
        executor.scheduleAtFixedRate(new Runnable() {
            int countdown = 3;
            @Override
            public void run() {
                if (countdown > 0) {
                    printer.printMessage(countdown+"");
                    countdown--;
                } else {
                    executor.shutdown();
                    latch.countDown(); // Countdown is finished, release the latch
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void showRulesForWords(){
        printer.printWarning(      "==========================================RULES=================================================== \n" +
                                        "| The game is simple, you will be given a word and you have to type it as fast as you can. \n" +
                                        "| The faster you type the word, the more points you get. \n" +
                                        "| ***REMEMBER!**** The game will be stopped if you do a mistake! \n" +
                                        "| Some words will have a blue color.\n" +
                                        "| You will get 2 extra point for them.\n" +
                                        "| You will have "+ WORD_GAME_TIME_SECONDS +" seconds to type as many words as you can. \n" +
                                        "| Good luck! \n" +
                                        "==================================================================================================\n" );
    }

    private void showRulesForSentences(){
        printer.printWarning(       "==========================================RULES===================================================\n" +
                                        "| The game is simple, you will be given a sentence and you have to type it as fast as you can. \n" +
                                        "| Good luck! \n" +
                                        "==================================================================================================\n" );
    }

    private void makeWordsMoreComplex(List<Word> words){
        Random random = new Random();
        if(complexity == GameComplexity.EASY){
            return;
        }
        for (Word word : words) {
            if(random.nextInt(2) ==1){
                switch (complexity){
                    case MEDIUM -> {changeOneLetterToUpperCase(word);}
                    case HARD -> {changeOneLetterToSymbol(word);}
                    case MIX -> {
                        if(random.nextInt(2) == 1){
                            changeOneLetterToSymbol(word);
                        }else{
                            changeOneLetterToUpperCase(word);
                        }
                    }
                }
                word.setModified(true);
            }
        }

    }
    private void changeOneLetterToUpperCase(Word word){
        Random random = new Random();
        int position = random.nextInt(word.getWord().length() -1 ) + 1;
        char[] lettersFromWord = word.getWord().toCharArray();
        char letter = lettersFromWord[position];
        letter = Character.toUpperCase(letter);
        lettersFromWord[position] = letter;
        word.setWord(String.valueOf(lettersFromWord));
    }

    private void changeOneLetterToSymbol(Word word){
        Random random = new Random();
        int position = random.nextInt(word.getWord().length());
        char letterToReplace = word.getWord().charAt(position);
        char randomSymbol = language.getSymbols().toCharArray()[random.nextInt(language.getSymbols().length())];
        word.setWord(word.getWord().replace(letterToReplace,randomSymbol));
    }


    public void changeChallengeMode() {
        String userInput;
        printer.printMessage("Choose game mode: ");
        showValuesToChoose(GameMode.class);
        userInput = inputService.getUsersInput();
        switch (userInput){
            case "1" -> gameMode = GameMode.WORDS;
            case "2" -> {
                if(complexity == GameComplexity.MIX){
                    printer.printError("You can't choose sentences mode with MIX complexity");
                    return;
                }
                gameMode = GameMode.SENTENCES;
            }
            default -> printer.printError("Invalid input");
        }
    }
    public void changeChallengeComplexity() {
        String userInput;
        printer.printMessage("Choose complexity: ");
        showValuesToChoose(GameComplexity.class);
        userInput = inputService.getUsersInput();
        switch (userInput){
            case "1" -> complexity = GameComplexity.EASY;
            case "2" -> complexity = GameComplexity.MEDIUM;
            case "3" -> complexity = GameComplexity.HARD;
            case "4" -> {
                if(gameMode == GameMode.SENTENCES){
                    printer.printError("You can't choose MIX complexity with sentences mode");
                    return;
                }
                complexity = GameComplexity.MIX;
            }
            default -> printer.printError("Invalid input");
        }
    }

    public void changeLanguage() {
        Language userInput;
        if(language == Language.ENGLISH){
            printer.printMessage("Choose language: ");
        }
        if(language == Language.SWEDISH){
            printer.printMessage("Välj språk :");
        }
        menu.displayMenu(Language.class);
        userInput = inputService.getUserChoice(Language.values());
        switch (userInput) {
            case ENGLISH -> {
                language = Language.ENGLISH;
                printer.printMessage("English chosen.");
            }
            case SWEDISH -> {
                language = Language.SWEDISH;
                printer.printMessage("Svenska valt.");
            }
            default -> {
                if (language == Language.ENGLISH) {
                    printer.printError("Invalid input");
                }
                if (language == Language.SWEDISH) {
                    printer.printError("Ogiltig inmatning");
                }
            }
        }
    }

    @Override
    public void showScore() {
        printer.printMessage("Your score is: " + score);
    }

    private <T extends Enum<?> & Ordered> void showValuesToChoose(Class<T> enumToDisplay){
        for (int i = 0; i < enumToDisplay.getEnumConstants().length; i++) {
            printer.printMenu(i+1 + ". " + enumToDisplay.getEnumConstants()[i].toString());
        }

    }

    @Override
    public String toString() {
        return "Challenge.class";
    }
}
