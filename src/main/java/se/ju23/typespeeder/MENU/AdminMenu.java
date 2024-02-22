package se.ju23.typespeeder.MENU;

public enum AdminMenu implements MenuOption{
SEND_NEWLETTER("Send new letter"),
    EXIT("Exit");


    private String option;

    AdminMenu(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }
}
