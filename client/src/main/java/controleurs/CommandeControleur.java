package controleurs;

import elements.MenuElement;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modeles.Session;

import static modeles.Session.COMMANDE_CTX;

/**
 * Controleur de la vue "commande".
 */
public class CommandeControleur extends Controleur {

  private VBox layout;
  private HBox menu;

  public CommandeControleur(Stage primaryStage) {
    super(primaryStage);
  }

  @Override
  protected void init() {
    layout = new VBox(20);
    menu = new MenuElement(primaryStage).getElement();
    // teste la récupération de l'id de la commande pour affichage.
    Text test = new Text(String.valueOf(Session.getInstance().getContexte().get(COMMANDE_CTX)));
    layout.getChildren().addAll(menu, test);
    scene = new Scene(layout, 1000, 800);
  }

  @Override
  protected void gererElements() {
  }

  @Override
  protected void nettoyerContexte() {
    Session.getInstance().getContexte().remove(COMMANDE_CTX);
  }

}
