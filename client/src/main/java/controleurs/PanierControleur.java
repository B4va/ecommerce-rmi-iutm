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
import javafx.stage.Stage;
import modeles.Session;
import services.IMagasin;

import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;
import static modeles.Session.ARTICLE_CTX;

/**
 * Controleur de la vue "panier".
 */
public class PanierControleur extends Controleur {

  private VBox layout;
  private HBox menu;
  private Text aucunArticle;
  private VBox articlesBox;
  private List<HBox> articles = new ArrayList<>();
  private List<Text> libelle = new ArrayList<>();
  private List<Text> prix = new ArrayList<>();
  private List<TextField> quantite = new ArrayList<>();
  private List<Button> majQuantite = new ArrayList<>();
  private List<Text> dispo = new ArrayList<>();
  private List<Button> boutonsVoirArticle = new ArrayList<>();
  private List<Button> boutonsSupprimerArticle = new ArrayList<>();
  private Text total;
  private HBox buttons;
  private Button passerCommande;
  private Button viderPanier;

  public PanierControleur(Stage primaryStage) {
    super(primaryStage);
  }

  @Override
  protected void init() {
    layout = new VBox(20);
    menu = new MenuElement(primaryStage).getElement();
    layout.getChildren().add(menu);
    articlesBox = new VBox(20);
    if (initialiserPanier()) {
      articlesBox.setPadding(new Insets(20));
      articlesBox.getChildren().addAll(articles);
      total = new Text(calculerTotal());
      total.setFont(Font.font(20));
      articlesBox.getChildren().add(total);
      passerCommande = new Button("Passer commande");
      viderPanier = new Button("Vider le panier");
      buttons = new HBox(20);
      buttons.getChildren().addAll(passerCommande, viderPanier);
      buttons.setAlignment(Pos.CENTER);
      layout.getChildren().addAll(articlesBox, buttons);
    } else {
      articlesBox.setPadding(new Insets(0, 0, 0, 20));
      aucunArticle = new Text("Aucun article dans le panier");
      articlesBox.getChildren().add(aucunArticle);
      layout.getChildren().add(articlesBox);
    }
    scene = new Scene(layout, 1000, 800);
  }

  private String calculerTotal() {
    return "Total : " + prix.stream().map(q -> Double.parseDouble(q.getText().replace(" €", ""))).reduce(Double::sum).orElse(0.0) + " €";
  }

  private boolean initialiserPanier() {
    try {
      IMagasin serviceMagasin = (IMagasin) Naming.lookup(App.URL_API_MAGASIN);
      List<ArticlePanierDTO> articles = serviceMagasin.recupererArticlesPanier(Session.getInstance().getUserId());
      if (articles.isEmpty()) return false;
      formaterArticles(articles);
      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
      Session.getInstance().setErreur("Erreur");
      primaryStage.setScene(new ErreurControleur(primaryStage).getScene());
      return false;
    }
  }

  private void formaterArticles(List<ArticlePanierDTO> articles) {
    articles.forEach(a -> {
      HBox article = new HBox(40);
      article.setStyle("-fx-border-color : black; -fx-border-width : 1 1 1 1");
      article.setAlignment(Pos.CENTER);
      article.setPadding(new Insets(20));
      this.articles.add(article);
      Text libelle = new Text(a.getLibelle().toUpperCase());
      libelle.setFont(Font.font(20));
      this.libelle.add(libelle);
      this.prix.add(new Text(a.getPrix() + " €"));
      this.quantite.add(new TextField(String.valueOf(a.getQte())));
      this.dispo.add(new Text(a.isDispo() ? "En stock" : "Non disponible"));
      Button btnVoir = new Button("Voir");
      btnVoir.setUserData(a.getIdArticle());
      this.boutonsVoirArticle.add(btnVoir);
      Button btnMaj = new Button("Mettre à jour");
      btnMaj.setUserData(a.getIdArticlePanier());
      this.majQuantite.add(btnMaj);
      Button btnSup = new Button("Supprimer");
      btnSup.setUserData(a.getIdArticlePanier());
      this.boutonsSupprimerArticle.add(btnSup);
    });
    for (int i = 0 ; i < this.articles.size() ; i++) {
      this.articles.get(i).getChildren().addAll(libelle.get(i), dispo.get(i), quantite.get(i), majQuantite.get(i),
        prix.get(i), boutonsSupprimerArticle.get(i), boutonsVoirArticle.get(i));
    }
  }

  @Override
  protected void gererElements() {
    boutonsSupprimerArticle.forEach(btn -> btn.setOnAction(this::gererSupprimerArticle));
    boutonsVoirArticle.forEach(btn -> btn.setOnAction(this::gererVoirArticle));
    majQuantite.forEach(btn -> btn.setOnAction(this::gererMajQte));
    if (nonNull(viderPanier) && nonNull(passerCommande)) {
      viderPanier.setOnAction(this::gererViderPanier);
      passerCommande.setOnAction(this::gererPasserCommande);
    }
  }

  private void gererVoirArticle(ActionEvent e) {
    Session.getInstance().getContexte().put(ARTICLE_CTX, ((Button) e.getSource()).getUserData());
    primaryStage.setScene(new ArticleControleur(primaryStage).getScene());
  }

  private void gererSupprimerArticle(ActionEvent e) {
    try {
      IMagasin serviceMagasin = (IMagasin) Naming.lookup(App.URL_API_MAGASIN);
      serviceMagasin.supprimerArticlePanier((int) ((Button) e.getSource()).getUserData());
    } catch (Exception ex) {
      ex.printStackTrace();
      Session.getInstance().setErreur("Erreur");
      primaryStage.setScene(new ErreurControleur(primaryStage).getScene());
    }
    primaryStage.setScene(new PanierControleur(primaryStage).getScene());
  }

  private void gererMajQte(ActionEvent e) {
    try {
      IMagasin serviceMagasin = (IMagasin) Naming.lookup(App.URL_API_MAGASIN);
      for (int i = 0 ; i < this.articles.size() ; i++) {
        serviceMagasin.modifierQuantiteArticlePanier((int) majQuantite.get(i).getUserData(), Integer.parseInt(quantite.get(i).getText()));
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      Session.getInstance().setErreur("Erreur");
      primaryStage.setScene(new ErreurControleur(primaryStage).getScene());
    }
    primaryStage.setScene(new PanierControleur(primaryStage).getScene());
  }

  private void gererViderPanier(ActionEvent e) {
    try {
      IMagasin serviceMagasin = (IMagasin) Naming.lookup(App.URL_API_MAGASIN);
      serviceMagasin.viderPanierClient(Session.getInstance().getUserId());
    } catch (Exception ex) {
      ex.printStackTrace();
      Session.getInstance().setErreur("Erreur");
      primaryStage.setScene(new ErreurControleur(primaryStage).getScene());
    }
    primaryStage.setScene(new PanierControleur(primaryStage).getScene());
  }

  private void gererPasserCommande(ActionEvent e) {
    primaryStage.setScene(new CreationCommandeControleur(primaryStage).getScene());
  }

  @Override
  protected void nettoyerContexte() {
  }
}
