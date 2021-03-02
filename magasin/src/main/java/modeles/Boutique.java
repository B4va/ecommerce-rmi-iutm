package modeles;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

/**
 * Boutique composant le magasin.
 */
@Entity
@Table(name = "boutique")
public class Boutique extends Modele {

  @Id
  @Generated(GenerationTime.INSERT)
  @GenericGenerator(name = "generator", strategy = "increment")
  @GeneratedValue(generator = "generator")
  @Column(name = "id")
  private int id;

  @Column(name = "nom", nullable = false)
  private String nom;

  @OneToMany(mappedBy = "boutique")
  private Set<Article> articles;

  public Boutique() {
  }

  public Boutique(String nom, Set<Article> articles) {
    this.nom = nom;
    this.articles = articles;
  }

  @Override
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public Set<Article> getArticles() {
    return articles;
  }

  public void setArticles(Set<Article> articles) {
    this.articles = articles;
  }
}
