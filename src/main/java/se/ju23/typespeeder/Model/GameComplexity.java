package se.ju23.typespeeder.Model;

;public enum  GameComplexity  implements  Ordered{
    EASY(1,0,5),
    MEDIUM(2,6,10),
    HARD(3,11,20),
    MIX(4,0,20);

    private int minWordLength;
    private int maxWordLength;
    private int order;

    GameComplexity(int order ,int minWordLength, int maxWordLength) {
        this.order = order;
        this.minWordLength = minWordLength;
        this.maxWordLength = maxWordLength;
    }


    public int getMinWordLength() {
        return minWordLength;
    }

    public int getMaxWordLength() {
        return maxWordLength;
    }
    @Override
    public int getOrder(){return order;}
}
