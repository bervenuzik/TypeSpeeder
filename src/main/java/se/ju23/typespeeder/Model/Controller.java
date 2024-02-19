package se.ju23.typespeeder.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.Repositories.PlayerRepo;
import se.ju23.typespeeder.Services.*;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;


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
    private Optional<Player> currentPlayer;

    public Controller(PlayerRepo playerRepo, Menu menu, InputService inputService) {
        this.playerRepo = playerRepo;
        this.menu = menu;
        this.inputService = inputService;
        currentPlayer = Optional.empty();
    }

    public Controller() {
        currentPlayer =  Optional.of( new Player());
        //currentPlayer = Optional.empty();
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
                menu.displayMenu();
                userInput = inputService.getUsersInput();
                switch (userInput){
                    case "1" -> game.showRules();
                    case "2" -> changeGameMode();
                    case "3" -> game.startGame();
                    case "4" -> System.out.println("My results");
                    case "5" -> System.out.println("Top players");
                    case "6" -> currentPlayer = Optional.empty();
                    default-> System.out.println("Invalid input");
                }
            }
        }
    }

    private void changeGameMode() {
        String userInput;
        printer.printMessage("Choose game mode: ");
        for (int i = 0; i < GameMode.values().length; i++) {
            printer.printMenu(i+1 + ". " + GameMode.values()[i].toString());
        }
        userInput = inputService.getUsersInput();
        switch (userInput){
            case "1" -> game.setGameMode(GameMode.WORDS);
            case "2" -> game.setGameMode(GameMode.SENTENCES);
            default -> printer.printError("Invalid input");
        }
    }
    private void changeGameComplexity() {
        String userInput;
        printer.printMessage("Choose complexity: ");
        for (int i = 0; i < GameComplexity.values().length; i++) {
            printer.printMenu(i+1 + ". " + GameMode.values()[i].toString());
        }
        userInput = inputService.getUsersInput();
        for (int i = 1; i <= GameComplexity.values().length; i++) {
            if(userInput.equals(GameComplexity.values()[i].getOrder()+"")){
                game.setComplexity(GameComplexity.values()[i]);
                return;
            }
        }
        printer.printError("Invalid input");
    }
}
