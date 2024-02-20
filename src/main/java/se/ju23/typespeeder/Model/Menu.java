package se.ju23.typespeeder.Model;

import org.springframework.stereotype.Component;
import se.ju23.typespeeder.Services.MenuService;
import se.ju23.typespeeder.PrintColors;

import java.util.ArrayList;
import java.util.List;

@Component
public class Menu implements MenuService {
   private final List<String> options= new ArrayList<>();
    public Menu(){
        options.add("Show rules");
        options.add("Change mode");
        options.add("Change complexity");
        options.add("Start Game");
        options.add("My results");
        options.add("Top players");
        options.add("Account settings");
        options.add("Log out");
        options.add("Exit");
    }

    @Override
    public List<String> displayMenu() {
        System.out.print(PrintColors.GREEN.getColor());


        for (int i = 0; i < options.size(); i++) {
            String option = options.get(i);
            System.out.println(i+1 + " " + options.get(i));
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
