package se.ju23.typespeeder.MENU;

public enum SettingsMenu implements MenuOption{
    CHANGE_NICKNAME("Change nickname"),
    CHANGE_USERNAME("Change username"),
    CHANGE_PASSWORD("Change password"),
    OPEN_MESSAGES("Open messages"),
    BACK("Back");


    private  String option;
    @Override
    public String getOption() {
        return option;
    }

    SettingsMenu(String option) {
        this.option = option;
    }
}
