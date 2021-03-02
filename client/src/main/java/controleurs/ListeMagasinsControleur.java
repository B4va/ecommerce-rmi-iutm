package controleurs;

import app.App;
import dtos.BoutiqueDTO;
import elements.MenuElement;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modeles.Session;
import services.IMagasin;

import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;

import static modeles.Session.MAGASIN_CTX;

/**
 * Controleur de la vue "liste de magasins".
 */
public class ListeMagasinsControleur extends Controleur {

  private VBox layout;
  private HBox menu;
  private VBox magasinsBox;
  private List<HBox> magasins = new ArrayList<>();
  private List<Text> libelles = new ArrayList<>();
  private List<Button> boutons = new ArrayList<>();

  public ListeMagasinsControleur(Stage primaryStage) {
    super(primaryStage);
  }

  @Override
  protected void init() {
    layout = new VBox(20);
    menu = new MenuElement(primaryStage).getElement();
    layout.getChildren().addAll(menu);
    initialiserMagasins();
    magasinsBox = new VBox(20);
    magasinsBox.setPadding(new Insets(50));
    magasinsBox.getChildren().addAll(magasins);
    layout.getChildren().add(magasinsBox);
    scene = new Scene(layout, 1000, 800);
  }

  @Override
  protected void gererElements() {
    boutons.forEach(b -> b.setOnAction(this::gererVisiterMagasin));
  }

  private void initialiserMagasins() {
    try {
      IMagasin serviceMagasin = (IMagasin) Naming.lookup(App.URL_API_MAGASIN);
      List<BoutiqueDTO> magasins = serviceMagasin.recupererListeMagasins();
      formaterMagasins(magasins);
    } catch (Exception ex) {
      ex.printStackTrace();
      Session.getInstance().setErreur("Erreur");
      primaryStage.setScene(new ErreurControleur(primaryStage).getScene());
    }
  }

  private void formaterMagasins(List<BoutiqueDTO> magasins) {
    magasins.forEach(m -> {
      HBox magasin = new HBox(120);
      magasin.setStyle("-fx-border-color : black; -fx-border-width : 1 1 1 1");
      magasin.setAlignment(Pos.CENTER);
      magasin.setPadding(new Insets(20));
      this.magasins.add(magasin);
      Text nom = new Text(m.getNom());
      nom.setFont(Font.font(25));
      this.libelles.add(nom);
      Button btn = new Button("Visiter");
      btn.setUserData(m.getId());
      this.boutons.add(btn);
    });
    for (int i = 0 ; i < this.magasins.size() ; i++) {
      this.magasins.get(i).getChildren().addAll(libelles.get(i), boutons.get(i));
    }
  }

  private void gererVisiterMagasin(ActionEvent e) {
    Session.getInstance().getContexte().put(MAGASIN_CTX, ((Button) e.getSource()).getUserData());
    primaryStage.setScene(new ListeArticlesControleur(primaryStage).getScene());
  }

  @Override
  protected void nettoyerContexte() {
  }
}
