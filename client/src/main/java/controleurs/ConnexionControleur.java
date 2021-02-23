package controleurs;

import app.App;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.IMagasin;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Controleur de la vue "connexion".
 */
public class ConnexionControleur extends Controleur {

  public ConnexionControleur(Stage primaryStage) {
    super(primaryStage);
  }

  @Override
  public Scene getScene() {
    Text label = new Text("Connexion");
    Text testRMI = new Text(testRMI());
    Button button = new Button("Magasins");
    button.setOnAction(e -> primaryStage.setScene(new ListeMagasinsControleur(primaryStage).getScene()));
    VBox layout = new VBox(20);
    layout.getChildren().addAll(label, button, testRMI);
    layout.setAlignment(Pos.CENTER);
    return new Scene(layout, 300, 250);
  }

  private String testRMI() {
    try {
      IMagasin serviceMagasin = (IMagasin) Naming.lookup(App.URL_API_MAGASIN);
      return serviceMagasin.test();
    } catch (NotBoundException | MalformedURLException | RemoteException e) {
      e.printStackTrace();
      return "Erreur";
    }
  }
}
