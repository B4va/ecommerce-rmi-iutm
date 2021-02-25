package elements;

import controleurs.ConnexionControleur;
import controleurs.ListeCommandesControleur;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import modeles.Session;

public class MenuElement extends Element<HBox> {

  private Stage primaryStage;
  private HBox menu;
  private Button deconnexion;
  private Button commandes;

  public MenuElement(Stage primaryStage) {
    this.primaryStage = primaryStage;
  }

  public HBox getElement() {
    deconnexion = new Button("Déconnexion");
    deconnexion.setOnAction(this::gererDeconnexion);
    commandes = new Button("Commandes");
    commandes.setOnAction(this::gererCommandes);
    menu = new HBox(20);
    menu.setPadding(new Insets(10));
    menu.setStyle("-fx-border-color : black; -fx-border-width : 0 0 1 0");
    menu.getChildren().addAll(deconnexion, commandes);
    return menu;
  }

  private void gererDeconnexion(ActionEvent e) {
    Session.init();
    primaryStage.setScene(new ConnexionControleur(primaryStage).getScene());
  }

  private void gererCommandes(ActionEvent e) {
    primaryStage.setScene(new ListeCommandesControleur(primaryStage).getScene());
  }
}
