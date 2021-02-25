package controleurs;

import app.App;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modeles.Session;
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

  private Text connexion;
  private TextField email;
  private PasswordField motDePasse;
  private Button valider;
  private Text notification;
  private VBox layout;

  @Override
  protected void init() {
    connexion = new Text("Connexion");
    connexion.setFont(Font.font(20));
    email = new TextField();
    email.setPromptText("email");
    motDePasse = new PasswordField();
    motDePasse.setPromptText("mot de passe");
    valider = new Button("Connexion");
    notification = new Text();
    layout = new VBox(20);
    layout.getChildren().addAll(connexion, email, motDePasse, valider, notification);
    layout.setAlignment(Pos.CENTER);
    layout.setPadding(new Insets(20, 40, 20, 40));
    scene = new Scene(layout, 400, 350);
  }

  @Override
  protected void gererElements() {
    valider.setOnAction(this::gererConnexion);
    scene.setOnKeyPressed(e -> {
      if (e.getCode().equals(KeyCode.ENTER)) gererConnexion(e);
    });
  }

  @Override
  protected void nettoyerContexte() {
  }

  private void gererConnexion(Event e) {
    try {
      boolean identifiantsValides = controlerIdentifiants(email.getText(), motDePasse.getText());
      if (identifiantsValides) {
        primaryStage.setScene(new ListeMagasinsControleur(primaryStage).getScene());
      } else {
        notification.setText("Identifiants invalides");
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      notification.setText("Erreur serveur");
    }
  }

  private boolean controlerIdentifiants(String email, String motDePasse) throws RemoteException, NotBoundException, MalformedURLException {
      IMagasin serviceMagasin = (IMagasin) Naming.lookup(App.URL_API_MAGASIN);
      int userId = serviceMagasin.validerUtilisateur(email, motDePasse);
      if (userId == -1) {
        return false;
      } else {
        Session.getInstance().setUserId(userId);
        return true;
      }
  }
}
