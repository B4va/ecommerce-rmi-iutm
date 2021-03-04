package services;

import modeles.CompteBancaire;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import static java.util.Objects.isNull;

/**
 * Implémentation des services fournis par l'application banque via protocole RMI.
 */
public class ServiceBanque extends UnicastRemoteObject implements IBanque {

  public ServiceBanque() throws RemoteException {
    super();
  }


  @Override
  public String payer(String refCompte, double montant) throws RemoteException {
    CompteBancaire compteBancaire = CompteBancaire.chargerTous(CompteBancaire.class)
      .stream()
      .filter(cb -> cb.getReference().equals(refCompte))
      .findFirst()
      .orElse(null);
    if (isNull(compteBancaire)) return "Informations bancaires invalides";
    if (compteBancaire.getSolde() < montant) return "Crédit insuffisant";
    compteBancaire.setSolde(compteBancaire.getSolde() - (float) montant);
    compteBancaire.mettreAjour();
    return null;
  }
}
