package services;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Services fournis par l'application magasin via protocole RMI.
 */
public interface IMagasin extends Remote {

  String test() throws RemoteException;

  int validerUtilisateur(String email, String motDePasse) throws RemoteException;
}
