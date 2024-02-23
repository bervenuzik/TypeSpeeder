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
        final int MIN_PASSWORD_LENGTH =  8;
        if(password.isEmpty()){
            System.out.println("password can't be empty");
            return false;
        }
        char tempChar;
        boolean upperCaseFlag = false;
        boolean lowererCaseFlag = false;
        boolean numberFlag = false;
        boolean lengthFlag = false;
        if(password.length() < MIN_PASSWORD_LENGTH){
            printer.printError("password must be at least 8 characters long");
            return false;
        }
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
        }
        if(!numberFlag){
            printer.printError("password must contain at least one number");
            return false;
        }
        if(!upperCaseFlag){
            printer.printError("password must contain at least one uppercase letter");
            return false;
        }
        if(!lowererCaseFlag){
            printer.printError("password must contain at least one lowercase letter");
            return false;
        }
        return true;
    }


    @Override
    public  boolean userNameValidator(String userName){

        if(userName.isEmpty()){
            System.out.println("username can't be empty.");
            return false;
        }
        if (userName.length() < 8) {
            System.out.println("username can't be less than 3 characters.");
            return false;
        }
        if (userName.length() > 20) {
            System.out.println("username can't be more than 20 characters.");
            return false;
        }
        if (userName.contains(" ")) {
            System.out.println("username can't contain spaces.");
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
            if (!nicknameValidator(nickname)) {
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

    private boolean nicknameValidator(String nickname) {
        if(nickname.isEmpty()){
            printer.printError("nickname can't be empty.");
            return false;
        }
        if (nickname.length() < 3) {
            printer.printError("nickname can't be less than 3 characters.");
            return false;
        }
        if (nickname.length() > 20) {
            printer.printError("nickname can't be more than 20 characters.");
            return false;
        }
        if (nickname.contains(" ")) {
            printer.printError("nickname can't contain spaces.");
            return false;
        }
        return true;
    }

    @Override
    public void changeUsername(Player player) {
        String newUsername;
        Optional<Player> playerByUserName;
        while (true) {
            newUsername = takeInUserName();
            if (!userNameValidator(newUsername)) {
                if (!printer.tryAgain()) {
                    return;
                } else {
                    continue;
                }
            }
            playerByUserName = playerRepo.findPlayerByUsername(newUsername);
            if(playerByUserName.isPresent()){
                printer.printError("Player with such Username is already exists");
                printer.printError("Find another username");
                if(!printer.tryAgain()) {
                    return;
                }else {
                    continue;
                }
            }
            player.setUsername(newUsername);
            playerRepo.save(player);
            printer.printSuccess("Username is changed");
            break;
        }

    }

    @Override
    public void changePassword(Player player) {
        String newPassword;
        while (true) {
            newPassword = takeInPassword();
            if (!passwordValidator(newPassword)) {
                if (!printer.tryAgain()){
                    return;
                } else {
                    continue;
                }
            }
            player.setPassword(newPassword);
            playerRepo.save(player);
            printer.printSuccess("Password is changed");
            break;
        }

    }

    @Override
    public void changeNickname(Player player) {
        String newNickname;
        Optional<Player> playerByNickname;
        while (true) {
            newNickname = takeInNickname();
            if (!nicknameValidator(newNickname)) {
                if (!printer.tryAgain()) {
                    return;
                } else {
                    continue;
                }
            }
            playerByNickname = playerRepo.findPlayerByNickname(newNickname);
            if(playerByNickname.isPresent()){
                printer.printError("Player with such nickname is already exists");
                printer.printError("Find another nickname");
                if(!printer.tryAgain()) {
                    return;
                }else {
                    continue;
                }
            }
            player.setNickname(newNickname);
            playerRepo.save(player);
            printer.printSuccess("Nickname is changed");
            break;
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
