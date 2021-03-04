package controleurs;

import app.App;
import dtos.ArticlePanierDTO;
import elements.MenuElement;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import modeles.Session;
import services.IMagasin;

import java.rmi.Naming;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * Controleur de la vue "création de commande".
 */
public class CreationCommandeControleur extends Controleur {

  private VBox layout;
  private HBox menu;
  private VBox commandeBox;
  private Text commande;
  private Text recapCommande;
  private Text informations;
  private TextField adresse;
  private TextField refCompte;
  private HBox boutons;
  private Button passerCommande;
  private Button annuler;
  private HBox consoleBox;
  private Text console;

  public CreationCommandeControleur(Stage primaryStage) {
    super(primaryStage);
  }

  @Override
  protected void init() {
    layout = new VBox(20);
    menu = new MenuElement(primaryStage).getElement();
    commandeBox = new VBox(20);
    commandeBox.setPadding(new Insets(0, 100, 100, 100));
    commande = new Text("Récapitulatif de la commande");
    commande.setFont(Font.font(18));
    initialiserRecapCommande();
    informations = new Text("Informations de paiement");
    informations.setFont(Font.font(18));
    adresse = new TextField();
    adresse.setPromptText("Adresse de livraison");
    refCompte = new TextField();
    refCompte.setPromptText("Référence du compte");
    boutons = new HBox(25);
    boutons.setAlignment(Pos.CENTER);
    passerCommande = new Button("Valider");
    annuler = new Button("Annuler");
    boutons.getChildren().addAll(passerCommande, annuler);
    console = new Text();
    consoleBox = new HBox();
    consoleBox.setAlignment(Pos.CENTER);
    consoleBox.getChildren().add(console);
    commandeBox.getChildren().addAll(commande, recapCommande, informations, adresse, refCompte, boutons, consoleBox);
    layout.getChildren().addAll(menu, commandeBox);
    scene = new Scene(layout, 1000, 800);
  }

  private void initialiserRecapCommande() {
    try {
      IMagasin serviceMagasin = (IMagasin) Naming.lookup(App.URL_API_MAGASIN);
      List<ArticlePanierDTO> articles = serviceMagasin.recupererArticlesPanier(Session.getInstance().getUserId());
      formaterRecapCommande(articles);
    } catch (Exception ex) {
      ex.printStackTrace();
      Session.getInstance().setErreur("Erreur");
      primaryStage.setScene(new ErreurControleur(primaryStage).getScene());
    }
  }

  private void formaterRecapCommande(List<ArticlePanierDTO> articles) {
    StringBuilder sb = new StringBuilder();
    articles.forEach(a -> sb.append(" > ")
      .append(a.getLibelle()).append(" - ")
      .append("nb : ").append(a.getQte()).append(" - ")
      .append(a.isDispo() ? "Disponible" : "Indisponible avec la quantité souhaitée")
      .append("\n"));
    sb.append("\n\nPRIX TOTAL : ")
      .append(articles.stream().map(ArticlePanierDTO::getPrix).reduce(Double::sum).orElse(0.0))
      .append(" €");
    recapCommande = new Text(sb.toString());
  }

  @Override
  protected void gererElements() {
    passerCommande.setOnAction(this::gererPasserCommande);
    annuler.setOnAction(this::gererAnnuler);
  }

  private void gererPasserCommande(ActionEvent e) {
    if (adresse.getText().equals("")) {
      console.setText("L'adresse de livraison doit être renseignée");
    } else {
      try {
        IMagasin serviceMagasin = (IMagasin) Naming.lookup(App.URL_API_MAGASIN);
        String error = serviceMagasin
          .creerCommande(Session.getInstance().getUserId(), refCompte.getText(), adresse.getText());
        if (isNull(error)) {
          primaryStage.setScene(new ListeCommandesControleur(primaryStage).getScene());
        } else {
          console.setText(error);
        }
      } catch (Exception ex) {
        ex.printStackTrace();
        Session.getInstance().setErreur("Erreur");
        primaryStage.setScene(new ErreurControleur(primaryStage).getScene());
      }
    }
  }

  private void gererAnnuler(ActionEvent e) {
    primaryStage.setScene(new PanierControleur(primaryStage).getScene());
  }

  @Override
  protected void nettoyerContexte() {
  }
}
