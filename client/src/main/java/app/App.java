package app;

import controleurs.ConnexionControleur;
import javafx.application.Application;
import javafx.stage.Stage;
import modeles.Session;

/**
 * Lanceur de l'application client.
 */
public class App extends Application {

  public static final String URL_API_MAGASIN = "rmi://localhost:8080/magasin";

  @Override
  public void start(Stage primaryStage) {
    Session.init();
    primaryStage.setTitle("eCommerce");
    primaryStage.setScene(new ConnexionControleur(primaryStage).getScene());
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
