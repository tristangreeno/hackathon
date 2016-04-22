
import java.util.HashMap;

/**
 * Stores information about each game, including the name and quantity. Allows checking out games (removing one of them)
 * and adding new games.
 */
public class Game {
  private static HashMap<String, Integer> gamesList = new HashMap<>();
  String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  Integer quantity;

  public static HashMap<String, Integer> getGamesList() {
    return gamesList;
  }

  public static void checkoutGame(String name){
    gamesList.put(name, gamesList.get(name) - 1);
  }

  public static void addGame(String name, Integer quantity){
    gamesList.put(name, quantity);
  }

}
