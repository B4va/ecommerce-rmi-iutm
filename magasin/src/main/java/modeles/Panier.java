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
@Table(name = "panier")
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

  @OneToMany(mappedBy = "panier")
  private Set<ArticlePanier> articlePaniers;

  public Panier() {
  }

  public Panier(Client client, Set<ArticlePanier> articlePaniers) {
    this.client = client;
    this.articlePaniers = articlePaniers;
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

  public Set<ArticlePanier> getArticlePaniers() {
    return articlePaniers;
  }

  public void setArticlePaniers(Set<ArticlePanier> articlePaniers) {
    this.articlePaniers = articlePaniers;
  }
}
