package modeles;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

/**
 * Client du magasin.
 */
@Entity
@Table(name = "client")
public class Panier extends Modele {

  @Id
  @Generated(GenerationTime.INSERT)
  @GenericGenerator(name = "generator", strategy = "increment")
  @GeneratedValue(generator = "generator")
  @Column(name = "id")
  private int id;

  @ManyToOne
  @JoinColumn(name = "client_id", nullable = false)
  private Client client;

  @OneToMany(mappedBy = "article")
  private Set<Article> articles;

  public Panier() {
  }

  public Panier(Client client, Set<Article> articles) {
    this.client = client;
    this.articles = articles;
  }

  @Override
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public Set<Article> getArticles() {
    return articles;
  }

  public void setArticles(Set<Article> articles) {
    this.articles = articles;
  }
}
