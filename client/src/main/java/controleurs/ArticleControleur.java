package controleurs;

import elements.MenuElement;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controleur de la vue "article".
 */
public class ArticleControleur extends Controleur {

  private VBox layout;
  private HBox menu;

  public ArticleControleur(Stage primaryStage) {
    super(primaryStage);
  }

  @Override
  protected void init() {
    layout = new VBox(20);
    menu = new MenuElement(primaryStage).getElement();
    layout.getChildren().addAll(menu);
    scene = new Scene(layout, 1000, 800);
  }

  @Override
  protected void gererElements() {
  }
}
