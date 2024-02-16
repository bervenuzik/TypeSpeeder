package se.ju23.typespeeder.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.Model.Player;
import se.ju23.typespeeder.Repositories.PlayerRepo;

import java.util.Optional;
import java.util.Scanner;

@Component
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    PlayerRepo playerRepo;

    @Autowired
    PrintServiceImpl printer;
    private Scanner input = new Scanner(System.in);

    public PlayerServiceImpl() {
    }

    public PlayerServiceImpl(PlayerRepo playerRepo , PrintServiceImpl printer) {
        this.playerRepo = playerRepo;
        this.printer = printer;
    }



    @Override
    public  boolean passwordValidator(String password){
        if(password.isEmpty()){
            System.out.println("password can't be empty");
            return false;
        }
        char tempChar;
        boolean upperCaseFlag = false;
        boolean lowererCaseFlag = false;
        boolean numberFlag = false;

        for(int i= 0; i < password.length(); i++){
            tempChar = password.charAt(i);
            if(Character.isDigit(tempChar)){
                numberFlag = true;
            }
            if(Character.isUpperCase(tempChar)){
                upperCaseFlag = true;
            }
            if(Character.isLowerCase(tempChar)){
                lowererCaseFlag = true;
            }
            if(numberFlag && upperCaseFlag && lowererCaseFlag){
                return true;
            }
        }
        return false;
    }


    @Override
    public  boolean userNameValidator(String userName){
        if(userName.isEmpty()){
            System.out.println("username can't be empty.");
            return false;
        }
        return true;
    }

    public Optional<Player> login() {
        String username;
        String password;
        Optional<Player> player;

        while (true) {

            username = takeInUserName();
            password = takeInPassword();

            player = playerRepo.findPlayerByUsernameAndPassword(username, password);

            if (player.isPresent()) {
                    printer.printSuccess("Login is success ");
                    printer.printSuccess("Welcome, " + player.get().getNickname());
                    return player;
            }else {
                printer.printError("Opssss, login is unsuccessful. Control username and password");
                if(!printer.tryAgain()) return Optional.empty();
            }
        }
    }

    public Optional<Player> regNewPlayer(){
        String username;
        String password;
        String nickname;
        Optional<Player> playerByUserName;
        Optional<Player> playerByNickname;

        while (true) {

            username = takeInUserName();
            //todo fix validation for username and nickname
            if (!userNameValidator(username)) {
                printer.printError("Wrong format of Username, try again");
                if(!printer.tryAgain()) return Optional.empty();
                continue;
            }
            password = takeInPassword();

            if (!passwordValidator(password)) {
                printer.printError("Wrong format of password, try again");
                if(!printer.tryAgain()) return Optional.empty();
                continue;
            }
            nickname = takeInNickname();

            if (!userNameValidator(nickname)) {
                printer.printError("Wrong format of nickname, try again");
                if(!printer.tryAgain()) return Optional.empty();
                continue;
            }

            playerByUserName = playerRepo.findPlayerByUsername(username);
            playerByNickname = playerRepo.findPlayerByNickname(nickname);

            if(playerByUserName.isPresent()) {
                printer.printError("Player with such Username is already exists");
                printer.printError("Login insted or find another username");
                if(!printer.tryAgain()) return Optional.empty();
            }else if(playerByNickname.isPresent()){
                printer.printError("Player with such nickname is already exists");
                printer.printError("Find another nickname");
                if(!printer.tryAgain()) return Optional.empty();
            }else{
                Player newPlayer = playerRepo.save(new Player(username, password, nickname));
                printer.printSuccess("Registration is successed ");
                printer.printSuccess("Welcome, " + newPlayer.getNickname());
                return Optional.of(newPlayer);
            }
        }

    }

    private String takeInNickname() {
        String  nickname;
        printer.printMessage("Write in your nickname:");
        nickname = input.nextLine().trim();
        input.nextLine();
        return nickname;
    }

    private String takeInUserName(){
        String  username;
        printer.printMessage("\nWrite in your Username :");
        username = input.nextLine().trim();
        input.nextLine();
        return username;
    }
    private String takeInPassword(){
        String  password;
        printer.printMessage("Write in your password:");
        if(System.console() == null) {
            password = input.nextLine().trim();
            input.nextLine();
        }else {
            password = String.valueOf(System.console().readPassword());
        }
        return password;
    }


}
