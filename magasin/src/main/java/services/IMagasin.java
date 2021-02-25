package services;

import dtos.CommandeDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Services fournis par l'application magasin via protocole RMI.
 */
public interface IMagasin extends Remote {

  int validerUtilisateur(String email, String motDePasse) throws RemoteException;

  List<CommandeDTO> recupererListeCommande(int idClient) throws RemoteException;
}
