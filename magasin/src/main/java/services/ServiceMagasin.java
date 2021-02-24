package services;

import app.App;
import modeles.Client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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
    Client client = Client.chargerAvecEmail(email);
    if (isNull(client)) return -1;
    return client.getMotDePasse().equals(motDePasse) ? client.getId() : -1;
  }
}
