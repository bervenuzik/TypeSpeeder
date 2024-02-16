package se.ju23.typespeeder.Model;

import org.springframework.stereotype.Component;
import se.ju23.typespeeder.Services.MenuService;
import se.ju23.typespeeder.PrintColors;

import java.util.ArrayList;
import java.util.List;

@Component
public class Menu implements MenuService {
    List<String> options= new ArrayList<>();
    public Menu(){
        options.add("Show rules");
        options.add("Start Game");
        options.add("My results");
        options.add("Top players");
        options.add("Log out");
    }

    @Override
    public List<String> displayMenu() {
        System.out.print(PrintColors.GREEN.getColor());
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print(PrintColors.RESET.getColor());
        return getMenuOptions();

    }

    public void displayLoginMenu(){
        System.out.print(PrintColors.GREEN.getColor());
        System.out.println("=====================Welcome to TypeSpeeder!=====================");
        System.out.println("1. Log in");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.println("=================================================================");
        System.out.print(PrintColors.RESET.getColor());
    }

    @Override
    public List<String> getMenuOptions() {
        return options;
    }
}
