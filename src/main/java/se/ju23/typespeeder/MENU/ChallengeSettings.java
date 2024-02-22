package se.ju23.typespeeder.MENU;

public enum ChallengeSettings implements MenuOption{
     SHOW_RULES ("Show rules"),
     CHANGE_MODE ("Change mode"),
     CHANGE_COMPLEXITY ("Change complexity"),
    CHANGE_LANGUAGE ("Change language"),
    EXIT ("Exit");

    private String option;

    ChallengeSettings(String option) {
        this.option = option;
    }

    @Override
    public String getOption() {
        return option;
    }
}
