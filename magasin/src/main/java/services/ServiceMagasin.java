package services;

import dtos.CommandeDTO;
import modeles.ArticleCommande;
import modeles.Client;
import modeles.Commande;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

/**
 * Implémentation des services fournis par l'application magasin via protocole RMI.
 */
public class ServiceMagasin extends UnicastRemoteObject implements IMagasin {

  public ServiceMagasin() throws RemoteException {
    super();
  }


  @Override
  public int validerUtilisateur(String email, String motDePasse) throws RemoteException {
    Client client = Client.chargerTous(Client.class)
      .stream()
      .filter(c -> c.getMail().equals(email))
      .findFirst()
      .orElse(null);
    if (isNull(client)) return -1;
    return client.getMotDePasse().equals(motDePasse) ? client.getId() : -1;
  }

  @Override
  public List<CommandeDTO> recupererListeCommande(int idClient) throws RemoteException {
    return Commande.chargerTous(Commande.class)
      .stream()
      .filter(c -> c.getClient().getId() == idClient)
      .map(c -> new CommandeDTO(
        c.getId(),
        c.getAdresse(),
        c.getDate(),
        c.isLivree(),
        c.getArticlesCommande().stream().map(ArticleCommande::getId).collect(Collectors.toSet())
      ))
      .collect(Collectors.toList());
  }
}
