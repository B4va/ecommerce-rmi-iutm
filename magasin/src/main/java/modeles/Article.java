package modeles;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Articles vendus par une boutique.
 */
@Entity
@Table(name = "article")
public class Article extends Modele {

  @Id
  @Generated(GenerationTime.INSERT)
  @GenericGenerator(name = "generator", strategy = "increment")
  @GeneratedValue(generator = "generator")
  @Column(name = "id")
  private int id;

  @Column(name = "libelle", nullable = false)
  private String libelle;

  @Column(name = "prix", nullable = false)
  private double article;

  @Column(name = "description")
  private String description;

  @Column(name = "stock", nullable = false)
  private int stock;

  @Column(name = "image_blob")
  private byte[] imageBlob;

  @ManyToOne
  @JoinColumn(name = "boutique_id", nullable = false)
  private Boutique boutique;

  public Article() {
  }

  public Article(String libelle, double article, String description, int stock, byte[] imageBlob, Boutique boutique) {
    this.libelle = libelle;
    this.article = article;
    this.description = description;
    this.stock = stock;
    this.imageBlob = imageBlob;
    this.boutique = boutique;
  }

  @Override
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

  public double getArticle() {
    return article;
  }

  public void setArticle(double article) {
    this.article = article;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }

  public byte[] getImageBlob() {
    return imageBlob;
  }

  public void setImageBlob(byte[] imageBlob) {
    this.imageBlob = imageBlob;
  }

  public Boutique getBoutique() {
    return boutique;
  }

  public void setBoutique(Boutique boutique) {
    this.boutique = boutique;
  }
}
