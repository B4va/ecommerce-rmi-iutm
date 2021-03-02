package controleurs;

import app.App;
import dtos.ArticleMagasinDTO;
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
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static modeles.Session.ARTICLE_CTX;
import static modeles.Session.MAGASIN_CTX;

/**
 * Controleur de la vue "liste d'articles".
 */
public class ListeArticlesControleur extends Controleur {

  private VBox layout;
  private HBox menu;
  private VBox articlesBox;
  private List<HBox> articles = new ArrayList<>();
  private List<Text> libelles = new ArrayList<>();
  private List<Text> prix = new ArrayList<>();
  private List<Text> stocks = new ArrayList<>();
  private List<Button> boutonsArticle = new ArrayList<>();
  private Text aucunArticle;

  public ListeArticlesControleur(Stage primaryStage) {
    super(primaryStage);
  }

  @Override
  protected void init() {
    if (isNull(Session.getInstance().getContexte().get(MAGASIN_CTX))) {
      Session.getInstance().setErreur("L'identifiant du magasin n'a pas pu être retrouvé.");
      primaryStage.setScene(new ErreurControleur(primaryStage).getScene());
    }
    layout = new VBox(20);
    menu = new MenuElement(primaryStage).getElement();
    articlesBox = new VBox(20);
    if (initialiserArticles()) {
      articlesBox.setPadding(new Insets(50));
      articlesBox.getChildren().addAll(articles);
    } else {
      articlesBox.setPadding(new Insets(0, 0, 0, 20));
      aucunArticle = new Text("Aucune commande en cours");
      articlesBox.getChildren().add(aucunArticle);
    }
    layout.getChildren().addAll(menu, articlesBox);
    scene = new Scene(layout, 1000, 800);
  }

  private boolean initialiserArticles() {
    try {
      IMagasin serviceMagasin = (IMagasin) Naming.lookup(App.URL_API_MAGASIN);
      List<ArticleMagasinDTO> articles = serviceMagasin.recupererArticlesMagasin((int) Session.getInstance().getContexte().get(MAGASIN_CTX));
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

  private void formaterArticles(List<ArticleMagasinDTO> articles) {
    articles.forEach(a -> {
      HBox article = new HBox(120);
      article.setStyle("-fx-border-color : black; -fx-border-width : 1 1 1 1");
      article.setAlignment(Pos.CENTER);
      article.setPadding(new Insets(20));
      this.articles.add(article);
      Text libelle = new Text(a.getLibelle());
      libelle.setFont(Font.font(20));
      this.libelles.add(libelle);
      this.prix.add(new Text(a.getPrix() + " €"));
      this.stocks.add(new Text(a.isDispo() ? "Disponible" : "Indisponible"));
      Button button = new Button("Voir");
      button.setUserData(a.getId());
      this.boutonsArticle.add(button);
    });
    for (int i = 0 ; i < this.articles.size() ; i++) {
      this.articles.get(i).getChildren().addAll(libelles.get(i), prix.get(i), stocks.get(i), boutonsArticle.get(i));
    }
  }

  @Override
  protected void gererElements() {
    boutonsArticle.forEach(b -> b.setOnAction(this::gererConsulterArticle));
  }

  private void gererConsulterArticle(ActionEvent e) {
    Session.getInstance().getContexte().put(ARTICLE_CTX, ((Button) e.getSource()).getUserData());
    primaryStage.setScene(new ArticleControleur(primaryStage).getScene());
  }

  @Override
  protected void nettoyerContexte() {
    Session.getInstance().getContexte().remove(MAGASIN_CTX);
  }
}
