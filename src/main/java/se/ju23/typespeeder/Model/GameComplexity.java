package se.ju23.typespeeder.Model;

;public enum  GameComplexity  implements  Ordered{
    EASY(1,0,5,10),
    MEDIUM(2,6,10,15),
    HARD(3,11,20,30),
    MIX(4,0,20 , 0);

    private int minWordLength;
    private int maxWordLength;
    private int order;
    private int timeBounderies;

    GameComplexity(int order ,int minWordLength, int maxWordLength , int timeBounderies) {
        this.order = order;
        this.minWordLength = minWordLength;
        this.maxWordLength = maxWordLength;
        this.timeBounderies = timeBounderies;
    }


    public int getMinWordLength() {
        return minWordLength;
    }

    public int getMaxWordLength() {
        return maxWordLength;
    }
    public int getTimeBounderies() {
        return timeBounderies;
    }
    @Override
    public int getOrder(){return order;}
}
