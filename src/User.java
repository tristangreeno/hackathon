
import java.util.HashMap;

/**
 * Stores passwords, usernames, and the ID of each user.
 */
public class User {
  public static final Integer MAX_LENGTH = 15;
  private String id;
  private String name;
  private String password;

  public String getName() {
    return name;
  }

  private HashMap<String, String> passwords = new HashMap<>();
  private HashMap<String, String> ids = new HashMap<>();

  public User(String name, String password){
    this.name = name;
    this.password = password;
    this.id = IdGenerator.nextSessionId();
    ids.put(name, id);
    passwords.put(id, this.password);
  }

  public final String getId(String name){
    return ids.get(name);
  }

  public final boolean authenticate(String id, String password){
    return password.equals(passwords.get(id));
  }
}

