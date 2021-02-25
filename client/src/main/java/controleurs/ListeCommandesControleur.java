package controleurs;

import app.App;
import dtos.CommandeDTO;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static modeles.Session.COMMANDE_CTX;

/**
 * Controleur de la vue "liste de commandes".
 */
public class ListeCommandesControleur extends Controleur {

  private VBox layout;
  private HBox menu;
  private VBox commandesBox;
  private List<HBox> commandes = new ArrayList<>();
  private List<Text> adresses = new ArrayList<>();
  private List<Text> livrees = new ArrayList<>();
  private List<Text> dates = new ArrayList<>();
  private List<Button> boutonsCommandes = new ArrayList<>();

  public ListeCommandesControleur(Stage primaryStage) {
    super(primaryStage);
  }

  @Override
  protected void init() {
    layout = new VBox(20);
    menu = new MenuElement(primaryStage).getElement();
    initialiserCommandes();
    commandesBox = new VBox(20);
    commandesBox.setPadding(new Insets(50));
    commandesBox.getChildren().addAll(commandes);
    layout.getChildren().addAll(menu, commandesBox);
    scene = new Scene(layout, 1000, 800);
  }

  @Override
  protected void gererElements() {
    boutonsCommandes.forEach(b -> b.setOnAction(this::gererBoutonCommande));
  }

  @Override
  protected void nettoyerContexte() {
  }

  private void initialiserCommandes() {
    try {
      IMagasin serviceMagasin = (IMagasin) Naming.lookup(App.URL_API_MAGASIN);
      List<CommandeDTO> commandes = serviceMagasin.recupererListeCommande(Session.getInstance().getUserId());
      formaterCommandes(commandes);
    } catch (Exception ex) {
      ex.printStackTrace();
      Session.getInstance().setErreur("Erreur serveur");
    }
  }

  private void formaterCommandes(List<CommandeDTO> commandes) {
    commandes.forEach(c -> {
      HBox commande = new HBox(120);
      commande.setStyle("-fx-border-color : black; -fx-border-width : 1 1 1 1");
      commande.setAlignment(Pos.CENTER);
      commande.setPadding(new Insets(20));
      this.commandes.add(commande);
      Text date = new Text(new SimpleDateFormat("dd-MM-yyyy").format(c.getDate()));
      date.setFont(Font.font(20));
      dates.add(date);
      adresses.add(new Text(c.getAdresse()));
      livrees.add(new Text(c.isLivree() ? "Livrée" : "Non livrée"));
      Button btn = new Button("Voir");
      btn.setUserData(c.getId());
      boutonsCommandes.add(btn);
    });
    for (int i = 0 ; i < this.commandes.size() ; i++) {
      this.commandes.get(i).getChildren().addAll(dates.get(i), adresses.get(i), livrees.get(i), boutonsCommandes.get(i));
    }
  }

  private void gererBoutonCommande(ActionEvent e) {
    Session.getInstance().getContexte().put(COMMANDE_CTX, ((Button) e.getSource()).getUserData());
    primaryStage.setScene(new CommandeControleur(primaryStage).getScene());
  }
}
