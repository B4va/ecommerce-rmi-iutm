package services;

import dtos.*;

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

  void modifierQuantiteArticlePanier(int idArticlePanier, int qte) throws RemoteException;

  List<BoutiqueDTO> recupererListeMagasins() throws RemoteException;

  List<ArticleMagasinDTO> recupererArticlesMagasin(int idMagasin) throws RemoteException;

  ArticleDTO recupererArticle(int idArticle) throws RemoteException;

  boolean isArticleDansPanier(int idArticle, int idUser) throws RemoteException;

  void ajouterAuPanier(int idArticle, int idUser) throws RemoteException;
}
