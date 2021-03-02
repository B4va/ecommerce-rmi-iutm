package elements;

import controleurs.ConnexionControleur;
import controleurs.ListeCommandesControleur;
import controleurs.ListeMagasinsControleur;
import controleurs.PanierControleur;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import modeles.Session;

/**
 * Menu pouvant être intégré aux différents {@link controleurs.Controleur}.
 */
public class MenuElement extends Element<HBox> {

  private Stage primaryStage;
  private HBox menu;
  private Button magasins;
  private Button deconnexion;
  private Button panier;
  private Button commandes;

  public MenuElement(Stage primaryStage) {
    this.primaryStage = primaryStage;
  }

  public HBox getElement() {
    magasins = new Button("Magasins");
    magasins.setOnAction(this::gererMagasins);
    panier = new Button("Panier");
    panier.setOnAction(this::gererPanier);
    commandes = new Button("Commandes");
    commandes.setOnAction(this::gererCommandes);
    deconnexion = new Button("Déconnexion");
    deconnexion.setOnAction(this::gererDeconnexion);
    menu = new HBox(20);
    menu.setPadding(new Insets(10));
    menu.setStyle("-fx-border-color : black; -fx-border-width : 0 0 1 0");
    menu.getChildren().addAll(magasins, panier, commandes, deconnexion);
    return menu;
  }

  private void gererMagasins(ActionEvent e) {
    primaryStage.setScene(new ListeMagasinsControleur(primaryStage).getScene());
  }

  private void gererDeconnexion(ActionEvent e) {
    Session.init();
    primaryStage.setScene(new ConnexionControleur(primaryStage).getScene());
  }

  private void gererPanier(ActionEvent e) {
    primaryStage.setScene(new PanierControleur(primaryStage).getScene());
  }

  private void gererCommandes(ActionEvent e) {
    primaryStage.setScene(new ListeCommandesControleur(primaryStage).getScene());
  }
}
