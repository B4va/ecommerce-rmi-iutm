package modeles;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Singleton permettant d'enregistrer et consulter des informations durant une session
 * d'utilisation de l'application.
 */
public class Session {

  public static final String COMMANDE_CTX = "COMMANDE";

  private static Session SESSION;

  private int userId;
  private String erreur;
  private Map<String, Object> contexte = new HashMap<>();

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

  public String getErreur() {
    return erreur;
  }

  public void setErreur(String erreur) {
    this.erreur = erreur;
  }

  public Map<String, Object> getContexte() {
    return contexte;
  }

  public void setContexte(Map<String, Object> contexte) {
    this.contexte = contexte;
  }
}
