package services;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Services fournis par l'application banque via protocole RMI.
 */
public interface IBanque extends Remote {

  String payer(String refCompte, double montant) throws RemoteException;
}
