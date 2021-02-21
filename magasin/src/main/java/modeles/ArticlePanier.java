package modeles;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Client du magasin.
 */
@Entity
@Table(name = "article_panier")
public class ArticlePanier extends Modele {

  @Id
  @Generated(GenerationTime.INSERT)
  @GenericGenerator(name = "generator", strategy = "increment")
  @GeneratedValue(generator = "generator")
  @Column(name = "id")
  private int id;

  @ManyToOne
  @JoinColumn(name = "article_id", nullable = false)
  private Article article;

  @ManyToOne
  @JoinColumn(name = "panier_id", nullable = false)
  private Panier panier;

  public ArticlePanier() {
  }

  public ArticlePanier(Article article, Panier panier) {
    this.article = article;
    this.panier = panier;
  }

  @Override
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Article getArticle() {
    return article;
  }

  public void setArticle(Article article) {
    this.article = article;
  }

  public Panier getPanier() {
    return panier;
  }

  public void setPanier(Panier panier) {
    this.panier = panier;
  }
}
