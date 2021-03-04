package controleurs;

import app.App;
import dtos.CommandeDetailsDTO;
import elements.MenuElement;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
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

import static java.util.Objects.isNull;
import static modeles.Session.COMMANDE_CTX;

/**
 * Controleur de la vue "commande".
 */
public class CommandeControleur extends Controleur {

  private VBox layout;
  private HBox menu;
  private VBox commandeBox;
  private Text date;
  private Text livraison;
  private Text articles;
  private Text prix;
  private Button retour;

  public CommandeControleur(Stage primaryStage) {
    super(primaryStage);
  }

  @Override
  protected void init() {
    layout = new VBox(20);
    menu = new MenuElement(primaryStage).getElement();
    if (isNull(Session.getInstance().getContexte().get(COMMANDE_CTX))) {
      Session.getInstance().setErreur("L'identifiant de l'article n'a pas pu être retrouvé.");
      primaryStage.setScene(new ErreurControleur(primaryStage).getScene());
    }
    initialiserCommande();
    retour = new Button("Retour");
    commandeBox = new VBox(20);
    commandeBox.setPadding(new Insets(20, 50, 50, 50));
    commandeBox.getChildren().addAll(date, livraison, articles, prix, retour);
    layout.getChildren().addAll(menu, commandeBox);
    scene = new Scene(layout, 1000, 800);
  }

  private void initialiserCommande() {
    try {
      IMagasin serviceMagasin = (IMagasin) Naming.lookup(App.URL_API_MAGASIN);
      CommandeDetailsDTO commande = serviceMagasin.recupererCommande((int) Session.getInstance().getContexte().get(COMMANDE_CTX));
      formaterCommande(commande);
    } catch (Exception ex) {
      ex.printStackTrace();
      Session.getInstance().setErreur("Erreur");
      primaryStage.setScene(new ErreurControleur(primaryStage).getScene());
    }
  }

  private void formaterCommande(CommandeDetailsDTO commande) {
    date = new Text("Date de livraison prévue : " + new SimpleDateFormat("dd-MM-yyyy").format(commande.getDateLivraison()));
    date.setFont(Font.font(18));
    livraison = new Text(commande.isLivree() ? "Livrée" : "En cours de livraison");
    StringBuilder articles = new StringBuilder("Articles commandés :\n");
    commande.getArticles().forEach(a -> {
      articles.append("\n> ")
        .append(a.getLibelle()).append(" - ")
        .append("qté : ").append(a.getQte()).append(" - ")
        .append(a.getPrix()).append(" €");
    });
    this.articles = new Text(articles.toString());
    prix = new Text("Prix total : " + commande.getPrix() + " €");
    prix.setFont(Font.font(16));
  }

  @Override
  protected void gererElements() {
    retour.setOnAction(this::gererRetour);
  }

  private void gererRetour(ActionEvent e) {
    primaryStage.setScene(new ListeCommandesControleur(primaryStage).getScene());
  }

  @Override
  protected void nettoyerContexte() {
    Session.getInstance().getContexte().remove(COMMANDE_CTX);
  }

}
