package services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Implémentation des services fournis par l'application banque via protocole RMI.
 */
public class ServiceBanque extends UnicastRemoteObject implements IBanque {

  public ServiceBanque() throws RemoteException {
    super();
  }

  @Override
  public String test() throws RemoteException {
    return "Hello world !";
  }
}
