package app;

import services.ServiceMagasin;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Lanceur de l'application magasin.
 */
public class App {

  /*
  Base de données Magasin
  - DB : db
  - USER : user
  - PWD : pass
  - HOST/PORT : localhost:5500
   */

  public static final String URL_API_BANQUE = "rmi://localhost:8070/banque";

  private static final int PORT = 8080;
  private static final String URL = "rmi://localhost:" + PORT + "/magasin";

  public static void main(String[] args) {
    try {
      LocateRegistry.createRegistry(PORT);
      Naming.rebind(URL, new ServiceMagasin());
      System.out.println("Ok Magasin");
    } catch (RemoteException | MalformedURLException e) {
      System.out.println("Echec magasin");
      e.printStackTrace();
    }
  }
}
