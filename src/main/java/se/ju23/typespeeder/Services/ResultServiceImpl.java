package se.ju23.typespeeder.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.Model.Result;
import se.ju23.typespeeder.Repositories.ResultsRepo;
@Component
public class ResultServiceImpl  implements ResultsService{
    @Autowired
    private ResultsRepo resultRepo;

    public ResultServiceImpl(ResultsRepo resultRepo) {
        this.resultRepo = resultRepo;
    }

    public ResultServiceImpl() {
    }

    public void saveResults(Result result){
        resultRepo.save(result);
    }
}
