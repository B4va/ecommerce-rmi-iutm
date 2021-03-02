package services;

import dtos.ArticlePanierDTO;
import dtos.BoutiqueDTO;
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

  List<ArticlePanierDTO> recupererArticlesPanier(int idClient) throws RemoteException;

  void supprimerArticlePanier(int idArticlePanier) throws RemoteException;

  void viderPanierClient(int idClient) throws RemoteException;

  void modifierQunatiteArticlePanier(int idArticlePanier, int qte) throws RemoteException;

  List<BoutiqueDTO> recupererListeMagasins() throws RemoteException;
}
