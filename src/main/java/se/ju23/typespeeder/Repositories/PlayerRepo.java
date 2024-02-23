package se.ju23.typespeeder.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import se.ju23.typespeeder.Model.Player;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepo extends JpaRepository<Player,Long> {

    Optional<Player> findPlayerByUsernameAndPassword(String username ,String password);
    Optional<Player> findPlayerByNickname(String nickname);
    Optional<Player> findPlayerByUsername(String username);

}
