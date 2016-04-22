
import java.util.HashMap;

/**
 * Stores information about each game, including the name and quantity. Allows checking out games (removing one of them)
 * and adding new games.
 */
public class Game {
  private static HashMap<String, Integer> gamesList = new HashMap<>();
  String name;
  Integer quantity;

  public static HashMap<String, Integer> getGamesList() {
    return gamesList;
  }

  public static void checkoutGame(String name){
    gamesList.remove(name);
  }

  public static void addGame(String name, Integer quantity){
    gamesList.put(name, 1);
  }

}
