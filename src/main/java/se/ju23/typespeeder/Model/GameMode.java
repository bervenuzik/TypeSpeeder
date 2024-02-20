package se.ju23.typespeeder.Model;

public enum  GameMode implements Ordered {
    WORDS(1),
    SENTENCES(2);

    int order;

    GameMode(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

}




