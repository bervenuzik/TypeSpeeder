package se.ju23.typespeeder.Model;

public enum WordComplexity {
    EASY(1),
    MEDIUM(3),
    HARD(7);

    private int points;

    WordComplexity(int points){
        this.points = points;
    }
    public int getPoints(){
        return points;
    }
}
