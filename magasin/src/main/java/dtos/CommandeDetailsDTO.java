package dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Data Transfert Object - {@link modeles.Commande}.
 */
public class CommandeDetailsDTO implements Serializable {

  private Date dateLivraison;
  private double prix;
  private boolean livree;
  private List<ArticleCommandeDTO> articles;

  public CommandeDetailsDTO(Date dateLivraison, double prix, boolean livree, List<ArticleCommandeDTO> articles) {
    this.dateLivraison = dateLivraison;
    this.prix = prix;
    this.livree = livree;
    this.articles = articles;
  }

  public Date getDateLivraison() {
    return dateLivraison;
  }

  public void setDateLivraison(Date dateLivraison) {
    this.dateLivraison = dateLivraison;
  }

  public double getPrix() {
    return prix;
  }

  public void setPrix(double prix) {
    this.prix = prix;
  }

  public boolean isLivree() {
    return livree;
  }

  public void setLivree(boolean livree) {
    this.livree = livree;
  }

  public List<ArticleCommandeDTO> getArticles() {
    return articles;
  }

  public void setArticles(List<ArticleCommandeDTO> articles) {
    this.articles = articles;
  }
}
