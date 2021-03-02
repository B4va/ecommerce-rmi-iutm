package dtos;

import java.io.Serializable;

/**
 * Data Transfert Object - ArticlePanier.
 */
public class ArticlePanierDTO implements Serializable {

  private int idArticle;
  private int idArticlePanier;
  private String libelle;
  private int qte;
  private boolean dispo;
  private double prix;

  public ArticlePanierDTO(int idArticle, int idArticlePanier, String libelle, int qte, boolean dispo, double prix) {
    this.idArticle = idArticle;
    this.idArticlePanier = idArticlePanier;
    this.libelle = libelle;
    this.qte = qte;
    this.dispo = dispo;
    this.prix = prix;
  }

  public int getIdArticle() {
    return idArticle;
  }

  public void setIdArticle(int idArticle) {
    this.idArticle = idArticle;
  }

  public int getIdArticlePanier() {
    return idArticlePanier;
  }

  public void setIdArticlePanier(int idArticlePanier) {
    this.idArticlePanier = idArticlePanier;
  }

  public String getLibelle() {
    return libelle;
  }

  public void setLibelle(String libelle) {
    this.libelle = libelle;
  }

  public int getQte() {
    return qte;
  }

  public void setQte(int qte) {
    this.qte = qte;
  }

  public boolean isDispo() {
    return dispo;
  }

  public void setDispo(boolean dispo) {
    this.dispo = dispo;
  }

  public double getPrix() {
    return prix;
  }

  public void setPrix(double prix) {
    this.prix = prix;
  }
}
