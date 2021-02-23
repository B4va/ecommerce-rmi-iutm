package modeles;

import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.BaseDeDonneesUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;

/**
 * Méthodes d'accès aux données propres aux différents modèles.
 */
public abstract class Modele implements Serializable {

  public abstract int getId();

  /**
   * Crée l'objet en base de données.
   *
   * @return id identifiant de l'objet en base de données
   */
  public int creer() {
    Session session = BaseDeDonneesUtils.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();
    Serializable id = session.save(this);
    transaction.commit();
    session.close();
    return (int) id;
  }

  /**
   * Récupère un objet en base de données.
   *
   * @param id id de l'objet
   * @param c  classe de l'objet
   * @return objet mappé
   */
  public static <T extends Modele> T charger(Serializable id, Class<T> c) {
    Session session = BaseDeDonneesUtils.getSessionFactory().openSession();
    T object = session.get(c, id);
    session.close();
    return object;
  }

  /**
   * Met à jour l'objet en base de données.
   */
  public void mettreAjour() {
    Session session = BaseDeDonneesUtils.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();
    session.update(this);
    transaction.commit();
    session.close();
  }

  /**
   * Supprime l'objet de la base de données.
   */
  public void supprimer() {
    Session session = BaseDeDonneesUtils.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();
    session.delete(this);
    transaction.commit();
    session.close();
  }

  /**
   * Récupère toutes les instances d'un objet en base de données.
   *
   * @param c classe des objets à supprimer
   */
  public static <T> List<T> chargerTous(Class<T> c) {
    Session session = BaseDeDonneesUtils.getSessionFactory().openSession();
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<T> criteria = builder.createQuery(c);
    criteria.from(c);
    List<T> list = session.createQuery(criteria).getResultList();
    session.close();
    return list;
  }

  /**
   * Supprime toutes les instances d'un objet en base de données.
   *
   * @param c classe des objets à supprimer
   */
  public static <T> void supprimerTous(Class<T> c) {
    Session session = BaseDeDonneesUtils.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();
    session.createQuery("delete from " + c.getSimpleName()).executeUpdate();
    transaction.commit();
    session.close();
  }
}
