package se.ju23.typespeeder.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.MENU.MainMenu;
import se.ju23.typespeeder.MENU.MenuOption;
import se.ju23.typespeeder.Services.MenuService;
import se.ju23.typespeeder.PrintColors;
import se.ju23.typespeeder.Services.PrintService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Menu implements MenuService {
    @Autowired
    private PrintService printer;

    public Menu(PrintService printer) {
        this.printer = printer;
    }

    public Menu(){
    }

    @Override
    public <T extends Enum & MenuOption > List<String> displayMenu(Class<T> menu) {
        T [] options = menu.getEnumConstants();
        for (int i = 0; i < options.length; i++) {
            printer.printMenu(i+1 + ". "+options[i].getOption());
        }
        return List.of(Arrays.stream(menu.getEnumConstants()).map(MenuOption::getOption).toArray(String[]::new));
    }

    @Override
    public List<String> getMenuOptions() {
        MainMenu[] options = MainMenu.values();
        List<String> optionsList = new ArrayList<>();
        for (MainMenu option : options) {
            optionsList.add(option.getOption());
        }
        return optionsList;
    }
}
