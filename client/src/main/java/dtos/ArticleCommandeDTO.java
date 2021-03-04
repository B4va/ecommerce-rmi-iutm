package dtos;

import java.io.Serializable;

/**
 * Data Transfert Object - Article associé à une commande.
 */
public class ArticleCommandeDTO implements Serializable {

  private String libelle;
  private int qte;
  private double prix;

  public ArticleCommandeDTO(String libelle, int qte, double prix) {
    this.libelle = libelle;
    this.qte = qte;
    this.prix = prix;
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

  public double getPrix() {
    return prix;
  }

  public void setPrix(double prix) {
    this.prix = prix;
  }
}
