package controleurs;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Controleur de la vue "liste de magasins".
 */
public class ListeMagasinsControleur extends Controleur {

  public ListeMagasinsControleur(Stage primaryStage) {
    super(primaryStage);
  }

  @Override
  public Scene getScene() {
    Text label = new Text("Magasins");
    VBox layout = new VBox(20);
    layout.getChildren().addAll(label);
    layout.setAlignment(Pos.CENTER);
    return new Scene(layout, 300, 250);
  }
}
