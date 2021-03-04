package controleurs;

import app.App;
import dtos.ArticleDTO;
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

import static java.util.Objects.isNull;
import static modeles.Session.ARTICLE_CTX;
import static modeles.Session.MAGASIN_CTX;

/**
 * Controleur de la vue "article".
 */
public class ArticleControleur extends Controleur {

  private VBox layout;
  private HBox menu;
  private VBox article;
  private Text libelle;
  private Text prix;
  private Text description;
  private Text dispo;
  private HBox boutons;
  private Button ajouterPanier;
  private Button retourMagasin;

  public ArticleControleur(Stage primaryStage) {
    super(primaryStage);
  }

  @Override
  protected void init() {
    if (isNull(Session.getInstance().getContexte().get(MAGASIN_CTX))) {
      Session.getInstance().setErreur("L'identifiant de l'article n'a pas pu être retrouvé.");
      primaryStage.setScene(new ErreurControleur(primaryStage).getScene());
    }
    layout = new VBox(20);
    menu = new MenuElement(primaryStage).getElement();
    initialiserArticle();
    layout.getChildren().addAll(menu, article);
    scene = new Scene(layout, 1000, 800);
  }

  private void initialiserArticle() {
    try {
      IMagasin serviceMagasin = (IMagasin) Naming.lookup(App.URL_API_MAGASIN);
      ArticleDTO article = serviceMagasin.recupererArticle((int) Session.getInstance().getContexte().get(ARTICLE_CTX));
      formaterArticle(article);
    } catch (Exception ex) {
      ex.printStackTrace();
      Session.getInstance().setErreur("Erreur");
      primaryStage.setScene(new ErreurControleur(primaryStage).getScene());
    }
  }

  private void formaterArticle(ArticleDTO article) {
    this.article = new VBox(20);
    this.article.setAlignment(Pos.CENTER);
    libelle = new Text(article.getLibelle());
    libelle.setFont(Font.font(22));
    prix = new Text(article.getPrix() + " €");
    prix.setFont(Font.font(16));
    description = new Text(article.getDescription());
    dispo = new Text(article.isDispo() ? "Disponible" : "Indisponible");
    boutons = new HBox(20);
    boutons.setAlignment(Pos.CENTER);
    initialiserBoutonAjouterPanier(article.getId(), article.isDispo());
    retourMagasin = new Button("Retour au magasin");
    retourMagasin.setUserData(article.getBoutiqueId());
    boutons.getChildren().addAll(ajouterPanier, retourMagasin);
    boutons.setPadding(new Insets(20, 0, 0, 0));
    this.article.getChildren().addAll(libelle, prix, description, dispo, boutons);
  }

  private void initialiserBoutonAjouterPanier(int idArticle, boolean dispo) {
    try {
      IMagasin serviceMagasin = (IMagasin) Naming.lookup(App.URL_API_MAGASIN);
      boolean estDansPanier = serviceMagasin.isArticleDansPanier(
        (int) Session.getInstance().getContexte().get(ARTICLE_CTX), Session.getInstance().getUserId());
      formaterBoutonAjouterPanier(idArticle, estDansPanier, dispo);
    } catch (Exception ex) {
      ex.printStackTrace();
      Session.getInstance().setErreur("Erreur");
      primaryStage.setScene(new ErreurControleur(primaryStage).getScene());
    }
  }

  private void formaterBoutonAjouterPanier(int idArticle, boolean estDansPanier, boolean dispo) {
    if (estDansPanier) {
      ajouterPanier = new Button("Ajouté au panier");
      ajouterPanier.setDisable(true);
    } else {
      ajouterPanier = new Button("Ajouter au panier");
      ajouterPanier.setUserData(idArticle);
      if (!dispo) ajouterPanier.setDisable(true);
    }
  }

  @Override
  protected void gererElements() {
    if (!ajouterPanier.isDisable()) ajouterPanier.setOnAction(this::gererAjouterPanier);
    retourMagasin.setOnAction(this::gererRetourMagasin);
  }

  private void gererAjouterPanier(ActionEvent e) {
    try {
      IMagasin serviceMagasin = (IMagasin) Naming.lookup(App.URL_API_MAGASIN);
      serviceMagasin.ajouterAuPanier(
        (int) ((Button) e.getSource()).getUserData(), Session.getInstance().getUserId());
      ajouterPanier.setDisable(true);
      ajouterPanier.setText("Ajouté au panier");
    } catch (Exception ex) {
      ex.printStackTrace();
      Session.getInstance().setErreur("Erreur");
      primaryStage.setScene(new ErreurControleur(primaryStage).getScene());
    }
  }

  private void gererRetourMagasin(ActionEvent e) {
    Session.getInstance().getContexte().put(MAGASIN_CTX, ((Button) e.getSource()).getUserData());
    primaryStage.setScene(new ListeArticlesControleur(primaryStage).getScene());
  }

  @Override
  protected void nettoyerContexte() {
    Session.getInstance().getContexte().remove(ARTICLE_CTX);
  }
}
