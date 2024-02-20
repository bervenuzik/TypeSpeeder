package se.ju23.typespeeder.Services;

import se.ju23.typespeeder.Model.Player;

import java.util.Optional;

public interface PlayerService {
      boolean userNameValidator(String userName);
      boolean passwordValidator(String password);
      Optional<Player> login();
      Optional<Player> regNewPlayer();
      void changeUsername(Player player);
      void changePassword(Player player);
      void changeNickname(Player player);
}
