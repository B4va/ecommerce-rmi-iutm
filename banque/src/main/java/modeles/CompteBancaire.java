package modeles;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Compte bancaire.
 */
@Entity
@Table(name = "compte_bancaire")
public class CompteBancaire extends Modele {

  @Id
  @Generated(GenerationTime.INSERT)
  @GenericGenerator(name = "generator", strategy = "increment")
  @GeneratedValue(generator = "generator")
  @Column(name = "id")
  private int id;

  @Column(name = "reference", nullable = false)
  private String reference;

  @Column(name = "solde", nullable = false)
  private float solde;

  public CompteBancaire() {
  }

  public CompteBancaire(String reference, float solde) {
    this.reference = reference;
    this.solde = solde;
  }

  @Override
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public float getSolde() {
    return solde;
  }

  public void setSolde(float solde) {
    this.solde = solde;
  }
}
