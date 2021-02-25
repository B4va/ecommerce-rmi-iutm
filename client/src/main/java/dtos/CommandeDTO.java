package dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Data Transfert Object - {@link modeles.Commande}.
 */
public class CommandeDTO implements Serializable {

  private int id;
  private String adresse;
  private Date date;
  private boolean livree;
  private Set<Integer> articlesCommandeId;

  public CommandeDTO(int id, String adresse, Date date, boolean livree, Set<Integer> articlesCommandeId) {
    this.id = id;
    this.adresse = adresse;
    this.date = date;
    this.livree = livree;
    this.articlesCommandeId = articlesCommandeId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getAdresse() {
    return adresse;
  }

  public void setAdresse(String adresse) {
    this.adresse = adresse;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public boolean isLivree() {
    return livree;
  }

  public void setLivree(boolean livree) {
    this.livree = livree;
  }

  public Set<Integer> getArticlesCommandeId() {
    return articlesCommandeId;
  }

  public void setArticlesCommandeId(Set<Integer> articlesCommandeId) {
    this.articlesCommandeId = articlesCommandeId;
  }
}
