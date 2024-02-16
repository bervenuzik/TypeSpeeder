package se.ju23.typespeeder.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.Repositories.PlayerRepo;
import se.ju23.typespeeder.Services.InputService;
import se.ju23.typespeeder.Services.InputServiceImpl;
import se.ju23.typespeeder.Services.PlayerService;

import java.util.Optional;


@Component
public class Controller implements Controllable {
    @Autowired
    PlayerRepo playerRepo;
    @Autowired
    Menu menu;
    @Autowired
    InputService inputService;
    @Autowired
    PlayerService playerService;
    @Autowired
    Playable game;
    Optional<Player> currentPlayer;

    public Controller(PlayerRepo playerRepo, Menu menu, InputService inputService) {
        this.playerRepo = playerRepo;
        this.menu = menu;
        this.inputService = inputService;
        currentPlayer = Optional.empty();
    }

    public Controller() {
        currentPlayer = Optional.empty();
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
                    default-> System.out.println("Invalid input");
                }
            }else {
                menu.displayMenu();
                userInput = inputService.getUsersInput();
                switch (userInput){
                    case "1" -> game.showRules();
                    case "2" -> System.out.println("Start Game");
                    case "3" -> System.out.println("My results");
                    case "4" -> System.out.println("Top players");
                    case "5" -> currentPlayer = Optional.empty();
                    default-> System.out.println("Invalid input");
                }
            }
        }
    }
}
