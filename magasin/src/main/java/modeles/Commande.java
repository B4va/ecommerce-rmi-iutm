package modeles;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Client du magasin.
 */
@Entity
@Table(name = "commande")
public class Commande extends Modele {

  @Id
  @Generated(GenerationTime.INSERT)
  @GenericGenerator(name = "generator", strategy = "increment")
  @GeneratedValue(generator = "generator")
  @Column(name = "id")
  private int id;

  @Column(name = "adresse", nullable = false)
  private String adresse;

  @Column(name = "date", nullable = false)
  private Date date;

  @Column(name = "livree", nullable = false)
  private boolean livree;

  @OneToMany(mappedBy = "commande")
  private Set<ArticleCommande> articlesCommande;

  @ManyToOne
  @JoinColumn(name = "client_id", nullable = false)
  private Client client;

  public Commande() {
  }

  public Commande(String adresse, Date date, boolean livree, Set<ArticleCommande> articlesCommande, Client client) {
    this.adresse = adresse;
    this.date = date;
    this.livree = livree;
    this.articlesCommande = articlesCommande;
    this.client = client;
  }

  @Override
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getAdresse() {
    return adresse;
  }

  public void setAdresse(String adresse) {
    this.adresse = adresse;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public boolean isLivree() {
    return livree;
  }

  public void setLivree(boolean livree) {
    this.livree = livree;
  }

  public Set<ArticleCommande> getArticlesCommande() {
    return articlesCommande;
  }

  public void setArticlesCommande(Set<ArticleCommande> articlesCommande) {
    this.articlesCommande = articlesCommande;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
