package se.ju23.typespeeder.Services;

import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.util.Scanner;

@Component
public class PrintServiceImpl implements PrintService {



        private  final String ANSI_RESET = "\u001B[0m";
        private  final String ANSI_BLACK = "\u001B[30m";
        private  final String ANSI_RED = "\u001B[31m";
        private  final String ANSI_GREEN = "\u001B[32m";
        private  final String ANSI_YELLOW = "\u001B[33m";
        private  final String ANSI_BLUE = "\u001B[34m";
        private  final String ANSI_PURPLE = "\u001B[35m";
        private  final String ANSI_CYAN = "\u001B[36m";
        private  final String ANSI_WHITE = "\u001B[37m";
        private Scanner input = new Scanner(System.in);


    public PrintServiceImpl() {

    }


    @Override
    public void printError(String text){
            System.out.println(ANSI_RED + text + ANSI_RESET);
        }
        @Override
        public void printWarning(String text){
            System.out.println(ANSI_YELLOW + text + ANSI_RESET);
        }

        @Override
        public void printMenu(String text){
            System.out.println(ANSI_BLUE + text + ANSI_RESET);
        }

        @Override
        public void printSuccess(String text){
            System.out.println(ANSI_GREEN + text + ANSI_RESET);
        }
        @Override
        public void printQuestion(String text){
            System.out.println(ANSI_CYAN + text + ANSI_RESET);
        }
        @Override
        public  void printMessage(String text){
            System.out.println(text);
        }
        @Override
        public boolean tryAgain(){
            String choise;
            while (true) {
                this.printQuestion("Do you want to try again? Y/N");
                choise = input.nextLine().trim().toUpperCase();
                if (choise.equals("N")) {
                    return false;
                }
                if (choise.equals("Y")) {
                    return true;
                }
                this.printError("Wrong input , try again");
            }
        }


        @Override
        public String toString() {
            return "MassageHandlerService";
        }
    }

