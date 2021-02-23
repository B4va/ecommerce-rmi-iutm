package app;

import services.ServiceBanque;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Lanceur de l'application banque.
 */
public class App {

  /*
  Base de données Banque
  - DB : db
  - USER : user
  - PWD : pass
  - HOST/PORT : localhost:5400
   */

  private static final int PORT = 8070;
  private static final String URL = "rmi://localhost:" + PORT + "/banque";

  public static void main(String[] args) {
    try {
      LocateRegistry.createRegistry(PORT);
      Naming.rebind(URL, new ServiceBanque());
      System.out.println("Ok Banque");
    } catch (RemoteException | MalformedURLException e) {
      System.out.println("Echec Banque");
      e.printStackTrace();
    }
  }
}
