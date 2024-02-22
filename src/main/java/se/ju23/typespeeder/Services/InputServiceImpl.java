package se.ju23.typespeeder.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.MENU.MenuOption;

import java.util.InputMismatchException;
import java.util.Scanner;
@Component
public class InputServiceImpl implements InputService {
    @Autowired
    private PrintService printer;
    Scanner scanner = new Scanner(System.in);


    public String getUsersInput() {
        String input =  scanner.nextLine();
        return input;
    }

    @Override
    public int getIntInput() {
        return 0;
    }

    public <T extends MenuOption>  T getUserChoice(T[] options) {
       printer.printMessage("\nEnter your choice:");
        int userChoice = getIntInput(options.length);
        return options[userChoice - 1];

    }
    public int getIntInput(int maxValue) {         //to handle invalid user-input instead of an integer
        int number = -1;
        do {
            try {
                number = scanner.nextInt();
                if (number < 1 || number > maxValue) {
                    printer.printError("Invalid Input! Try again: ");
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                printer.printError("Not an integer! Try Again: ");
            }
            scanner.nextLine();
        } while (number < 1 || number > maxValue);
        return number;
    }

    @Override
    public String getInputText() {
        printer.printMessage("Finish with 'THE-END' line: ");
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            if(!line.equals("THE-END")){
                sb.append(line).append("\n");
            } else {
                break;
            }
        }
        return sb.toString();
    }
}
