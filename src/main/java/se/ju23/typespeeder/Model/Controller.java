package se.ju23.typespeeder.Model;

import com.sun.source.tree.ConditionalExpressionTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.MENU.*;
import se.ju23.typespeeder.Repositories.PlayerRepo;
import se.ju23.typespeeder.Repositories.ResultsRepo;
import se.ju23.typespeeder.Repositories.SentencesRepo;
import se.ju23.typespeeder.Repositories.WordsRepo;
import se.ju23.typespeeder.Services.*;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

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
    private WordsRepo wordsRepo;
    @Autowired
    private ResultsService resultsService;
    @Autowired
    private SentencesRepo sentencesRepo;
    private Optional<Player> currentPlayer;
    @Autowired
    private NewsLetterServise newsLetterServise;

    public Controller(PlayerRepo playerRepo, Menu menu, InputService inputService , ResultsService resultsService, NewsLetterServise newsLetterServise) {
        this.playerRepo = playerRepo;
        this.menu = menu;
        this.inputService = inputService;
        this.resultsService = resultsService;
        this.newsLetterServise = newsLetterServise;
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

        while (true){
            if(currentPlayer.isEmpty()){
                menu.displayMenu(LoginMenu.class);
                LoginMenu userInput = inputService.getUserChoice(LoginMenu.values());
                switch (userInput){
                    case LOGIN -> currentPlayer = playerService.login();
                    case REGISTER -> currentPlayer = playerService.regNewPlayer();
                    case EXIT ->System.exit(0);
                    default-> printer.printError("Invalid input");
                }
            }else {
                menu.displayMenu(MainMenu.class);
                MainMenu userInput = inputService.getUserChoice(MainMenu.values());
                switch (userInput){
                    case CHALLANGE_SETTINGS -> challangeSettings();
                    case START_CHALLANGE -> {
                        game.startChallenge();
                        game.showScore();
                        Result result = new Result(currentPlayer.get(), game.getChallengeMode(), game.getComplexity() ,game.getScore());
                        resultsService.saveResult(result);
                        printer.printMessage("");
                    }
                    case SHOW_MY_RESULTS ->resultsService.showMyResults(currentPlayer.get(),game.getChallengeMode(),game.getComplexity());
                    case SHOW_TOP_10_RESULTS -> System.out.println("Top players");
                    case SETTINGS -> accountSetttings(currentPlayer.get());
                    case ADMIN_MENU -> adminMenu();
                    case LOG_OUT -> currentPlayer = Optional.empty();
                    case EXIT -> System.exit(0);
                    default-> System.out.println("Invalid input");
                }
            }
        }
    }
    private void challangeSettings(){
        while (true) {
            menu.displayMenu(ChallengeSettings.class);
            ChallengeSettings userInput = inputService.getUserChoice(ChallengeSettings.values());
            switch (userInput) {
                case SHOW_RULES -> game.showRules();
                case CHANGE_MODE -> game.changeChallengeMode();
                case CHANGE_COMPLEXITY -> game.changeChallengeComplexity();
                case CHANGE_LANGUAGE -> game.changeLanguage();
                case EXIT -> {return;}
                default -> printer.printError("Invalid input");
            }
        }

    }

    private void adminMenu() {
        if(currentPlayer.get().getType() == PlayerType.ADMIN) {
            while(true) {
                menu.displayMenu(AdminMenu.class);
                AdminMenu userInput = inputService.getUserChoice(AdminMenu.values());
                switch (userInput) {
                    case SEND_NEWLETTER -> newsLetterServise.sendMessage(currentPlayer.get());
                    case EXIT ->{return;}
                    default -> printer.printError("Invalid input");
                }
            }
        }else {
            printer.printError("You are not an admin");
        }
    }

    private void accountSetttings(Player player) {
        SettingsMenu userInput;
        while (true){
            menu.displayMenu(SettingsMenu.class);
            userInput = inputService.getUserChoice(SettingsMenu.values());
            switch (userInput){
                case CHANGE_PASSWORD -> playerService.changePassword(player);
                case CHANGE_NICKNAME -> playerService.changeNickname(player);
                case CHANGE_USERNAME -> playerService.changeUsername(player);
                case OPEN_MESSAGES -> newsLetterServise.openMesseges();
                case BACK -> {return;}
                default -> printer.printError("Invalid input");
            }
        }
    }

}
