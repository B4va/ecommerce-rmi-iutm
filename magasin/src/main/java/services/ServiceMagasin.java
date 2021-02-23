package services;

import app.App;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Implémentation des services fournis par l'application magasin via protocole RMI.
 */
public class ServiceMagasin extends UnicastRemoteObject implements IMagasin {

  public ServiceMagasin() throws RemoteException {
    super();
  }

  public String test() throws RemoteException {
    try {
      IBanque serviceBanque = (IBanque) Naming.lookup(App.URL_API_BANQUE);
      return serviceBanque.test();
    } catch (NotBoundException | MalformedURLException | RemoteException e) {
      e.printStackTrace();
      return "Erreur";
    }
  }
}
