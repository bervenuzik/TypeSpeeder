package se.ju23.typespeeder.Services;

import org.springframework.stereotype.Component;

import java.util.Scanner;
@Component
public class InputServiceImpl implements InputService {
    Scanner scanner = new Scanner(System.in);


    public String getUsersInput() {
        String input = scanner.nextLine();
        return input;
    }
}
