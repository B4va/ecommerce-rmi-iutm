package modeles;

import org.hibernate.Session;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;
import utils.BaseDeDonneesUtils;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Client du magasin.
 */
@Entity
@Table(name = "client")
public class Client extends Modele {

  @Id
  @Generated(GenerationTime.INSERT)
  @GenericGenerator(name = "generator", strategy = "increment")
  @GeneratedValue(generator = "generator")
  @Column(name = "id")
  private int id;

  @Column(name = "nom", nullable = false)
  private String nom;

  @Column(name = "prenom", nullable = false)
  private String prenom;

  @Column(name = "mail", nullable = false)
  private String mail;

  @Column(name = "mot_de_passe", nullable = false)
  private String motDePasse;

  public Client() {
  }

  public Client(String nom, String prenom, String mail, String motDePasse) {
    this.nom = nom;
    this.prenom = prenom;
    this.mail = mail;
    this.motDePasse = motDePasse;
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

  public String getPrenom() {
    return prenom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public String getMotDePasse() {
    return motDePasse;
  }

  public void setMotDePasse(String motDePasse) {
    this.motDePasse = motDePasse;
  }
}
