package se.ju23.typespeeder.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.Repositories.PlayerRepo;
import se.ju23.typespeeder.Services.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

//todo messages
//todo sentence game
//todo patch message
@Component
public class Controller implements Controllable {
    @Autowired
    private PlayerRepo playerRepo;
    @Autowired
    private Menu menu;
    @Autowired
    private InputService inputService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private Playable game;
    @Autowired
    private PrintService printer;
    @Autowired
    WordService wordService;
    @Autowired
    ResultsService resultsService;
    @Autowired
    SentencesService sentencesService;
    private Optional<Player> currentPlayer;

    public Controller(PlayerRepo playerRepo, Menu menu, InputService inputService , ResultsService resultsService) {
        this.playerRepo = playerRepo;
        this.menu = menu;
        this.inputService = inputService;
        this.resultsService = resultsService;
        currentPlayer = Optional.empty();
    }

    public Controller() {
        //currentPlayer =  Optional.of( new Player());
        currentPlayer = Optional.empty();
    }
    public  void timer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Hello, World!");
            }
        };

        // Schedule the task to run every second (1000 milliseconds)
        timer.schedule(task, 0, 1000);
    }



    @Override
    public void start () {

//        ArrayList<String> texts = new ArrayList<>();
//        texts.add("Red apples hang. Green grass grows. Blue skies shine. Shapes: round, square, triangular.");
//        texts.add("Foxes dart, bears roam. Squirrels climb, rabbits hop. Forest teems with life.");
//        texts.add("Earth spins, Mars rusts. Venus gleams, Jupiter looms. Stars twinkle in space.");
//        texts.add("Winter snows, spring blooms. Summer heats, fall colors. Seasons change, nature thrives.");
//        texts.add("T-Rex roars, Stegosaurus spikes. Triceratops horns, fossils tell. Dinosaurs ruled, now extinct.");
//        for (String text: texts) {
//            ArrayList<String> sentences = new ArrayList<>();
//            String[] split = text.split("\\.");
//            for (String sentence : split) {
//                sentencesService.saveSentence(new Sentence(sentence.trim()+"."));
//            }
//        }

        String userInput;
        while (true){
            if(currentPlayer.isEmpty()){
                menu.displayLoginMenu();
                userInput = inputService.getUsersInput();
                switch (userInput){
                    case "1" -> currentPlayer = playerService.login();
                    case "2" -> currentPlayer = playerService.regNewPlayer();
                    case "3" ->System.exit(0);
                    default-> printer.printError("Invalid input");
                }
            }else {
                System.out.println(currentPlayer.get());
                menu.displayMenu();
                userInput = inputService.getUsersInput();
                switch (userInput){
                    case "1" -> game.showRules();
                    case "2" -> game.changeGameMode();
                    case "3" -> game.changeGameComplexity();
                    case "4" -> {
                        game.startGame();
                        game.showScore();
                        Result result = new Result(currentPlayer.get(), game.getGameMode(), game.getComplexity() ,game.getScore());
                        System.out.println(result);
                        resultsService.saveResults(result);
                    }
                    case "5" -> System.out.println("My results");
                    case "6" -> System.out.println("Top players");
                    case "7" -> changeAccount(currentPlayer.get());
                    case "8" -> currentPlayer = Optional.empty();
                    case "9" -> System.exit(0);
                    default-> System.out.println("Invalid input");
                }
            }
        }
    }

    private void changeAccount(Player player) {
        String userInput;
        main: while (true){
            printer.printMenu("1. Change password");
            printer.printMenu("2. Change nickname");
            printer.printMenu("3. Change username");
            printer.printMenu("4. Back");
            userInput = inputService.getUsersInput();
            switch (userInput){
                case "1" -> playerService.changePassword(player);
                case "2" -> playerService.changeNickname(player);
                case "3" -> playerService.changeUsername(player);
                case "4" -> {break main;}
                default -> printer.printError("Invalid input");
            }
        }
    }

}
