package se.ju23.typespeeder.Model;

public enum  GameComplexity {
    EASY(0,5),
    MEDIUM(4,7),
    HARD(7,20);

    private int minWordLength;
    private int maxWordLength;

    GameComplexity(int minWordLength, int maxWordLength) {
        this.minWordLength = minWordLength;
        this.maxWordLength = maxWordLength;
    }

    public int getMinWordLength() {
        return minWordLength;
    }

    public int getMaxWordLength() {
        return maxWordLength;
    }
}
