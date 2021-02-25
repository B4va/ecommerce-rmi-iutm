package controleurs;

import elements.MenuElement;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modeles.Session;

public class ErreurControleur extends Controleur {

  private VBox layout;
  private Text erreur;

  public ErreurControleur(Stage primaryStage) {
    super(primaryStage);
  }

  @Override
  protected void init() {
    layout = new VBox(20);
    layout.setAlignment(Pos.CENTER);
    erreur = new Text(Session.getInstance().getErreur());
    layout.getChildren().addAll(erreur);
    scene = new Scene(layout, 1000, 800);
  }

  @Override
  protected void gererElements() {
  }

  @Override
  protected void nettoyerContexte() {
    Session.getInstance().setErreur(null);
  }
}
