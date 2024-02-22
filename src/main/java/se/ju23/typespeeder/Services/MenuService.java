package se.ju23.typespeeder.Services;

import se.ju23.typespeeder.MENU.MenuOption;

import java.util.List;

public interface MenuService {


    <T extends Enum & MenuOption > List<String> displayMenu(Class<T> menu);
    List<String> getMenuOptions();

}
