package modeles;

/**
 * Singleton permettant d'enregistrer et consulter des informations durant une session
 * d'utilisation de l'application.
 */
public class Session {

  private static Session SESSION;

  private int userId;

  private Session() {
  }

  /**
   * Initialise la session.
   */
  public static void init() {
    SESSION = new Session();
  }

  /**
   * Retourne l'instance de la session.
   *
   * @return instance de la session
   */
  public static Session getInstance() {
    return SESSION;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }
}
