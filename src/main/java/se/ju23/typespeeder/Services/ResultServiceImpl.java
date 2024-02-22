package se.ju23.typespeeder.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.Model.GameComplexity;
import se.ju23.typespeeder.Model.GameMode;
import se.ju23.typespeeder.Model.Player;
import se.ju23.typespeeder.Model.Result;
import se.ju23.typespeeder.Repositories.ResultsRepo;

import java.util.List;

@Component
public class ResultServiceImpl  implements ResultsService{
    @Autowired
    private ResultsRepo resultRepo;
    @Autowired
    private PrintService printer;

    public ResultServiceImpl(ResultsRepo resultRepo, PrintService printer) {
        this.printer = printer;
        this.resultRepo = resultRepo;
    }

    public ResultServiceImpl() {
    }

        @Override
        public void saveResult(Result result) {
            resultRepo.save(result);
        }

        @Override
    public void showMyResults(Player player  , GameMode gameMode, GameComplexity complexity){
        List<Result> results= resultRepo.findResultsByPlayerAndGameModeAndComplexity(player , gameMode , complexity);
        for (Result result : results) {
            printer.printMessage(result.toString());
        }
    }
}

