package dtos;

import java.io.Serializable;

/**
 * Data Transfert Object - Article.
 */
public class ArticleDTO implements Serializable {

  private int id;
  private String libelle;
  private double prix;
  private String description;
  private boolean dispo;
  private int boutiqueId;

  public ArticleDTO(int id, String libelle, double prix, String description, boolean dispo, int boutiqueId) {
    this.id = id;
    this.libelle = libelle;
    this.prix = prix;
    this.description = description;
    this.dispo = dispo;
    this.boutiqueId = boutiqueId;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isDispo() {
    return dispo;
  }

  public void setDispo(boolean dispo) {
    this.dispo = dispo;
  }

  public int getBoutiqueId() {
    return boutiqueId;
  }

  public void setBoutiqueId(int boutiqueId) {
    this.boutiqueId = boutiqueId;
  }
}
