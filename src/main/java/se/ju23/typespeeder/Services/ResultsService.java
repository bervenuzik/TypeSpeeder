package se.ju23.typespeeder.Services;

import se.ju23.typespeeder.Model.GameComplexity;
import se.ju23.typespeeder.Model.GameMode;
import se.ju23.typespeeder.Model.Player;
import se.ju23.typespeeder.Model.Result;

public interface ResultsService {
    void saveResult(Result result);
    void showMyResults(Player player  , GameMode gameMode, GameComplexity complexity);
}
