import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Generates a random ID for the user using alphanumeric characters.
 */
public final class IdGenerator {
  private static SecureRandom random = new SecureRandom();

  public static String nextSessionId(){
    return new BigInteger(130, random).toString();
  }
}
