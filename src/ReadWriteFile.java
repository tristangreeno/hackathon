import jodd.json.JsonException;
import jodd.json.JsonParser;
import jodd.json.JsonSerializer;
import spark.*;

import java.io.*;
import java.util.HashMap;

/**
 * Reads information from a JSON, and writes
 */
public class ReadWriteFile extends Game {

  public static File writeJson(Request request, Response response) throws IOException {
    JsonSerializer js = new JsonSerializer();
    try {
      File gamesFile = new File("src/games.json");
      FileWriter fw = new FileWriter(gamesFile);
      BufferedWriter bw = new BufferedWriter(fw);

      HashMap<String, Integer> map = Game.getGamesList();
      String json = js.include("*").serialize(map);
      bw.write(json);
      bw.close();

      return gamesFile;
    } catch (FileNotFoundException e) {
      response.status(500);
      return null;
    }
  }

  public static Game readGames(Request request, Response response) throws FileNotFoundException {
    String contents = request.body();

    JsonParser parser = new JsonParser();
    try {
      return parser.parse(contents, Game.class);
    } catch (JsonException e) {
      response.status(500);
      return  null;
    }
  }

  public static User readUserJson(Request request, Response response, String json){
    JsonParser parser = new JsonParser();
    try{
      User user = parser.parse(json, User.class);
      response.status(200);
      return user;
    }catch(JsonException e){
      response.status(500);
      return null;
    }
  }
}
