package se.ju23.typespeeder.MENU;

public enum MainMenu implements MenuOption{
        CHALLANGE_SETTINGS("Challenge settings"),
        START_CHALLANGE("Start Challenge"),
        SHOW_MY_RESULTS("My results"),
        SHOW_TOP_10_RESULTS("Top players"),
        SETTINGS("Account settings"),
        ADMIN_MENU("Admin menu"),
        LOG_OUT("Log out"),
        EXIT("Exit");

        private final String option;

    MainMenu(String option) {
        this.option = option;
    }
    public String getOption(){
        return option;
    }
}
