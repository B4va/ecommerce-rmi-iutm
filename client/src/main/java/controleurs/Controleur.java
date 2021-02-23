package controleurs;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Gère les interactions de l'utilisateur avec une vue.
 */
public abstract class Controleur {

  protected Stage primaryStage;

  public Controleur(Stage primaryStage) {
    this.primaryStage = primaryStage;
  }

  public abstract Scene getScene();
}
