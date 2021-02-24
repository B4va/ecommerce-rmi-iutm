package elements;

import controleurs.ConnexionControleur;
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

  public MenuElement(Stage primaryStage) {
    this.primaryStage = primaryStage;
  }

  public HBox getElement() {
    menu = new HBox();
    menu.setPadding(new Insets(10));
    menu.setStyle("-fx-border-color : black; -fx-border-width : 0 0 1 0");
    deconnexion = new Button("Déconnexion");
    menu.getChildren().addAll(deconnexion);
    deconnexion.setOnAction(this::gererDeconnexion);
    return menu;
  }

  private void gererDeconnexion(ActionEvent e) {
    Session.init();
    primaryStage.setScene(new ConnexionControleur(primaryStage).getScene());
  }
}
