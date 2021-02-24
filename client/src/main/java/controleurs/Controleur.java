package controleurs;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Gère les interactions de l'utilisateur avec une vue.
 */
public abstract class Controleur {

  protected Stage primaryStage;
  protected Scene scene;

  public Controleur(Stage primaryStage) {
    this.primaryStage = primaryStage;
  }

  public Scene getScene() {
    init();
    gererElements();
    return scene;
  }

  protected abstract void init();

  protected abstract void gererElements();
}
