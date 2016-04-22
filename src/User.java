
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.HashMap;

/**
 * Stores passwords, usernames, and the ID of each user.
 */
public class User {
  public static final Integer MAX_LENGTH = 15;
  private static String id;
  private String name;
  private String password;

  public String getName() {
    return name;
  }

  private HashMap<String, String> passwords = new HashMap<>();
  private static HashMap<String, String> ids = new HashMap<>();

  public String getPassword() {
    return password;
  }

  public User(String name, String password) {
    this.name = name;
    this.password = password;
    id = IdGenerator.nextSessionId();
    ids.put(name, id);
    passwords.put(id, this.password);

  }

  final static String getId(String name) {
    return ids.get(name);
  }

  final boolean authenticate(String id, String password) {
    return password.equals(passwords.get(id));
  }

  static void checkIfUserIsLoggedIn(Request request, Response response) {
    Session s = request.session();
    String username = s.attribute("userName");
    User user = Main.users.get(username);

    if (user == null) {
      response.status(401);
    }
  }
}

