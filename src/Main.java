/**
 * Created by tristangreeno on 4/22/16.
 */

import spark.*;

import java.util.HashMap;


public class Main {

  static HashMap<String, User> users = new HashMap<>();
  private static HashMap<String, String> games = new HashMap<>();

  public static void main(String[] args) {
    Spark.init();

    Spark.post(
      "/login",
      (request, response) -> {

        // substring limits the characters the user can enter for security
        String json = request.body();
        User login = ReadWriteFile.readUserJson(request, response, json);
        Session s = request.session();
        assert login != null;
        s.attribute("userName", login.getName());
        User user = users.get(login.getName());

        if(user == null){
          users.put(login.getName(), new User(login.getName(), login.getPassword()));
        }

        response.status(200);
        return "";
      }
    );

    Spark.post(
      "/logout",
      (request, response) -> {
        Session s = request.session();
        s.invalidate();
        response.status(200);
        return "";
      }
    );

    Spark.get(
      "/games-list",
      (request, response) -> {
        ReadWriteFile.writeJson(request, response);
        return "";
      }
    );

    Spark.post(
      "/add-game",
      (request, response) -> {
        User.checkIfUserIsLoggedIn(request, response);
        String gameName = request.queryParams("gameName");
        Integer gameNum = Integer.valueOf(request.queryParams("gameNumber"));
        Game.addGame(gameName, gameNum);
        response.status(200);
        return "";
      }
    );

    Spark.post(
      "/checkout-game",
      (request, response) -> {
        User.checkIfUserIsLoggedIn(request, response);
        String gameName = request.queryParams("gameName");

        Session s = request.session();
        String username = s.attribute("userName");

        String id = User.getId(username);

        HashMap<String, Integer> gamesList = Game.getGamesList();
        if(gamesList.get(gameName) == 0) {
          Game.checkoutGame(gameName);
          games.put(id, gameName);
          response.status(200);
        }

        else response.status(403);

        return "";
      }
    );

    // Sends user to 404 page if a page is not found
    Spark.get(
      "*",
      (request, response) -> {
        response.status(404);
        return "";
      }
    );
  }
}


