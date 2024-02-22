package se.ju23.typespeeder.MENU;

public enum LoginMenu  implements MenuOption{
    LOGIN("Login"),
    REGISTER("Register"),
    EXIT("Exit");

    private  String option;
    LoginMenu(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }
}
