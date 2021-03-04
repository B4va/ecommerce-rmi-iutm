package services;

import app.App;
import dtos.*;
import modeles.*;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Implémentation des services fournis par l'application magasin via protocole RMI.
 */
public class ServiceMagasin extends UnicastRemoteObject implements IMagasin {

  public ServiceMagasin() throws RemoteException {
    super();
  }


  @Override
  public int validerUtilisateur(String email, String motDePasse) throws RemoteException {
    Client client = Client.chargerTous(Client.class)
      .stream()
      .filter(c -> c.getMail().equals(email))
      .findFirst()
      .orElse(null);
    if (isNull(client)) return -1;
    return client.getMotDePasse().equals(motDePasse) ? client.getId() : -1;
  }

  @Override
  public List<CommandeDTO> recupererListeCommande(int idClient) throws RemoteException {
    return Commande.chargerTous(Commande.class)
      .stream()
      .filter(c -> c.getClient().getId() == idClient)
      .map(c -> new CommandeDTO(
        c.getId(),
        c.getAdresse(),
        c.getDate(),
        c.isLivree(),
        c.getArticlesCommande().stream().map(ArticleCommande::getId).collect(Collectors.toSet())
      ))
      .collect(Collectors.toList());
  }

  @Override
  public List<ArticlePanierDTO> recupererArticlesPanier(int userId) throws RemoteException {
    return ArticlePanier.chargerTous(ArticlePanier.class)
      .stream()
      .filter(ap -> ap.getPanier().getClient().getId() == userId)
      .map(ap -> new ArticlePanierDTO(
        ap.getArticle().getId(),
        ap.getId(),
        ap.getArticle().getLibelle(),
        ap.getQte(),
        ap.getQte() <= ap.getArticle().getStock(),
        Math.round(ap.getQte() * ap.getArticle().getPrix() * 100) / 100.0))
      .collect(Collectors.toList());
  }

  @Override
  public void supprimerArticlePanier(int idArticlePanier) throws RemoteException {
    ArticlePanier ap = ArticlePanier.charger(idArticlePanier, ArticlePanier.class);
    if (nonNull(ap)) ap.supprimer();
  }

  @Override
  public void viderPanierClient(int idClient) throws RemoteException {
    Panier.chargerTous(Panier.class)
      .stream()
      .filter(p -> p.getClient().getId() == idClient)
      .forEach(p -> {
        p.getArticlePaniers().forEach(Modele::supprimer);
        p.supprimer();
      });
  }

  @Override
  public void modifierQuantiteArticlePanier(int idArticlePanier, int qte) throws RemoteException {
    ArticlePanier ap = ArticlePanier.charger(idArticlePanier, ArticlePanier.class);
    if (nonNull(ap)) {
      ap.setQte(qte);
      ap.mettreAjour();
    }
  }

  @Override
  public List<BoutiqueDTO> recupererListeMagasins() throws RemoteException {
    return Boutique.chargerTous(Boutique.class)
      .stream()
      .map(b -> new BoutiqueDTO(b.getId(), b.getNom()))
      .collect(Collectors.toList());
  }

  @Override
  public List<ArticleMagasinDTO> recupererArticlesMagasin(int idMagasin) throws RemoteException {
    return Article.chargerTous(Article.class)
      .stream()
      .filter(a -> a.getBoutique().getId() == idMagasin)
      .map(a -> new ArticleMagasinDTO(a.getId(), a.getLibelle(), Math.round(a.getPrix() * 100) / 100.0, a.getStock() > 0))
      .collect(Collectors.toList());
  }

  @Override
  public ArticleDTO recupererArticle(int idArticle) throws RemoteException {
    Article article = Article.charger(idArticle, Article.class);
    if (isNull(article)) return null;
    boolean dispo = article.getStock() > 0;
    double prix = Math.round(article.getPrix() * 100) / 100.0;
    return new ArticleDTO(article.getId(), article.getLibelle(), prix, article.getDescription(), dispo, article.getBoutique().getId());
  }

  @Override
  public boolean isArticleDansPanier(int idArticle, int idUser) throws RemoteException {
    Panier panier = Panier.chargerTous(Panier.class)
      .stream()
      .filter(p -> p.getClient().getId() == idUser)
      .findFirst()
      .orElse(null);
    if (nonNull(panier)) {
      return panier.getArticlePaniers()
        .stream()
        .anyMatch(ap -> ap.getArticle().getId() == idArticle);
    } else {
      return false;
    }
  }

  @Override
  public void ajouterAuPanier(int idArticle, int idUser) throws RemoteException {
    Client client = Client.charger(idUser, Client.class);
    Panier panier = Panier.chargerTous(Panier.class)
      .stream()
      .filter(p -> p.getClient().getId() == idUser)
      .findFirst()
      .orElse(new Panier(client, new HashSet<>()));
    if (panier.getId() == 0) panier.setId(panier.creer());
    Article article = Article.charger(idArticle, Article.class);
    ArticlePanier articlePanier = new ArticlePanier(article, panier, 1);
    articlePanier.setId(articlePanier.creer());
    panier.getArticlePaniers().add(articlePanier);
    panier.mettreAjour();
  }

  @Override
  public String creerCommande(int userId, String refCompte, String adresse) throws RemoteException {
    Panier panier = Panier.chargerTous(Panier.class)
      .stream()
      .filter(p -> p.getClient().getId() == userId)
      .findFirst()
      .orElse(null);
    if (nonNull(panier)) {
      double montant = panier.getArticlePaniers()
        .stream()
        .map(ap -> ap.getQte() * Math.round(ap.getArticle().getPrix() * 100) / 100.0)
        .reduce(Double::sum)
        .orElse(0.0);
      if (!verifierDispos(panier)) return "Certains articles ne sont pas disponibles avec la quantité souhaitée";
      String verifCompte = verifierCompte(refCompte, montant);
      if (nonNull(verifCompte)) return verifCompte;
      Client client = Client.charger(userId, Client.class);
      doCreerCommande(panier, client, adresse);
      return null;
    } else {
      return "Erreur";
    }
  }

  @Override
  public CommandeDetailsDTO recupererCommande(int idCommande) throws RemoteException {
    Commande commande = Commande.charger(idCommande, Commande.class);
    List<ArticleCommandeDTO> articles = commande.getArticlesCommande()
      .stream()
      .map(ac -> new ArticleCommandeDTO(ac.getArticle().getLibelle(), ac.getQte(), ac.getQte() * Math.round(ac.getArticle().getPrix() * 100) / 100.0))
      .collect(Collectors.toList());
    double prix = articles
      .stream()
      .map(ArticleCommandeDTO::getPrix)
      .reduce(Double::sum)
      .orElse(0.0);
    return new CommandeDetailsDTO(commande.getDate(), prix, commande.isLivree(), articles);
  }

  private void doCreerCommande(Panier panier, Client client, String adresse) throws RemoteException {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_MONTH, 3);
    Commande commande = new Commande(adresse, calendar.getTime(), client);
    commande.setId(commande.creer());
    panier.getArticlePaniers()
      .forEach(ap -> {
        ap.getArticle().setStock(ap.getArticle().getStock() - ap.getQte());
        ap.mettreAjour();
      });
    panier.getArticlePaniers().forEach(ap -> new ArticleCommande(ap.getArticle(), commande, ap.getQte()).creer());
    viderPanierClient(client.getId());
  }

  private boolean verifierDispos(Panier panier) {
    return panier.getArticlePaniers()
      .stream()
      .anyMatch(ap -> ap.getQte() <= ap.getArticle().getStock());
  }

  private String verifierCompte(String refCompte, double montant) throws RemoteException {
    try {
      IBanque serviceBanque = (IBanque) Naming.lookup(App.URL_API_BANQUE);
      return serviceBanque.payer(refCompte, montant);
    } catch (Exception e) {
      throw new RemoteException();
    }
  }
}
