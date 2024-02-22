package se.ju23.typespeeder.Services;

import se.ju23.typespeeder.MENU.MenuOption;

public interface InputService {

    String getUsersInput();

    int getIntInput();
    <T extends MenuOption> T getUserChoice(T[] options);
    int getIntInput(int maxValue);
    String getInputText();
}
