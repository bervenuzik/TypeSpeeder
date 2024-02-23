package se.ju23.typespeeder.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.ju23.typespeeder.Model.GameComplexity;
import se.ju23.typespeeder.Model.GameMode;
import se.ju23.typespeeder.Model.Player;
import se.ju23.typespeeder.Model.Result;
import java.util.List;

@Repository
public interface ResultsRepo extends JpaRepository <Result ,Long> {
    List<Result> findResultsByPlayerAndGameModeAndComplexity(Player player, GameMode gameMode, GameComplexity complexity);

    @Query(value = "SELECT r.id, r.player_id, r.result ,  r.game_mode ,r.complexity FROM" +
            " results r INNER JOIN (SELECT player_id, MAX(result) as max_result , complexity , game_mode  FROM" +
            " results WHERE complexity = :complexity AND game_mode = :gameMode GROUP BY player_id) mr ON r.player_id = mr.player_id AND r.result = mr.max_result  and r.complexity = mr.complexity and r.game_mode = mr.game_mode ORDER BY r.result DESC LIMIT 10"
            , nativeQuery = true)
    List<Result> findTop10ByGameModeAndComplexity(@Param("gameMode") GameMode gameMode, @Param("complexity") GameComplexity complexity);


}
