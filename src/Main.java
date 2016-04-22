/**
 * Created by tristangreeno on 4/22/16.
 */
import spark.*;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;
import java.util.Map;


public class Main {

  static HashMap<String, User> users = new HashMap<>();
  private static HashMap<String, String> games = new HashMap<>();

  public static void main(String[] args) {
    Spark.init();

    Spark.get(
      "/",
      (request, response) -> {
        Map<Object, Object> map = new HashMap<>();
        Session session = request.session();
        String userName = session.attribute("userName");
        String password = session.attribute("password");

        User user = users.get(userName);

        if(user == null){
          return new ModelAndView(map, "index.html");
        }

        else if(! user.authenticate(user.getId(userName), password)){
          map.put("error", "Incorrect password");
          return new ModelAndView(map, "index.html");
        }

        else {
          map.put("name", user.getName());
          map.put("game", games.get(user.getId(userName)));
          return new ModelAndView(map, "home.html");
        }
      },
      new MustacheTemplateEngine()
    );

    Spark.post(
      "/login",
      (request, response) -> {

        // substring limits the characters the user can enter for security
        String name = request.queryParams("userName");
        String pass = request.queryParams("password");
        User user = users.get(name);

        if(user == null){
          users.put(name, new User(name, pass));
        }

        Session session = request.session();
        session.attribute("userName", name);
        session.attribute("password", pass);

        response.redirect("/");
        return "";
      }
    );

    Spark.post(
      "/logout",
      (request, response) -> {
        Session s = request.session();
        s.invalidate();
        response.redirect("/");
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

        response.redirect("/");
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

        Game.checkoutGame(gameName);
        games.put(id, gameName);

        response.redirect("/");
        return "";
      }
    );
  }
}


