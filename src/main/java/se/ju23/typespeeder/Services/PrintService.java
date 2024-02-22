package se.ju23.typespeeder.Services;

import java.util.Scanner;

public interface PrintService {
    void printError(String text);

    void printWarning(String text);

    void printMenu(String text);

    void printSuccess(String text);

    void printQuestion(String text);

    void printMessage(String text);

    boolean tryAgain();
}
