package se.ju23.typespeeder.Model;

import se.ju23.typespeeder.MENU.MenuOption;

public enum Language  implements MenuOption {
    ENGLISH( "English","\"!@#$%^&*()_+{}|:<>?\""),
    SWEDISH( "Svenska" , "\"!äåö@#$%^&*()_+{}|:<>?\"");

    private String option;
    private String symbols;

    Language(String option , String symbols){
        this.option = option;
        this.symbols = symbols;
    }

    public String getSymbols(){
        return symbols;
    }

    @Override
    public String getOption() {
        return option;
    }
}
