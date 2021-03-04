package dtos;

import java.io.Serializable;

/**
 * Data Transfert Object - Articles associés à un magasin.
 */
public class ArticleMagasinDTO implements Serializable {

  private int id;
  private String libelle;
  private double prix;
  private boolean dispo;

  public ArticleMagasinDTO(int id, String libelle, double prix, boolean dispo) {
    this.id = id;
    this.libelle = libelle;
    this.prix = prix;
    this.dispo = dispo;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getLibelle() {
    return libelle;
  }

  public void setLibelle(String libelle) {
    this.libelle = libelle;
  }

  public double getPrix() {
    return prix;
  }

  public void setPrix(double prix) {
    this.prix = prix;
  }

  public boolean isDispo() {
    return dispo;
  }

  public void setDispo(boolean dispo) {
    this.dispo = dispo;
  }
}
