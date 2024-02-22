package se.ju23.typespeeder.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.ju23.typespeeder.Model.GameComplexity;
import se.ju23.typespeeder.Model.GameMode;
import se.ju23.typespeeder.Model.Player;
import se.ju23.typespeeder.Model.Result;
import java.util.List;

@Repository
public interface ResultsRepo extends JpaRepository <Result ,Long> {
    List<Result> findResultsByPlayerAndGameModeAndComplexity(Player player, GameMode gameMode, GameComplexity complexity);

}
