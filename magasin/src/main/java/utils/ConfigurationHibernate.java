package utils;

import modeles.*;
import org.hibernate.cfg.Configuration;

/**
 * Configuration d'Hibernate permettant d'ouvrir une session. {@link BaseDeDonneesUtils}
 * TODO : Gérer pool de connexion - voir c3p0
 */
public class ConfigurationHibernate {

  public static final String URL = "jdbc:postgresql://localhost:5500/db";
  public static final String USER = "user";
  public static final String PASSWORD = "pass";

  private ConfigurationHibernate() {
  }

  /**
   * Paramètre la configuration Hibernate.
   *
   * @return configuration
   */
  public static Configuration getConfiguration() {
    Configuration configuration = new Configuration();
    setDatabaseConfiguration(configuration);
    registerEntities(configuration);
    return configuration;
  }

  /**
   * Référence les modèles mappés par Hibernate.
   */
  private static void registerEntities(Configuration configuration) {
    configuration.addAnnotatedClass(Article.class);
    configuration.addAnnotatedClass(Commande.class);
    configuration.addAnnotatedClass(ArticleCommande.class);
    configuration.addAnnotatedClass(Boutique.class);
    configuration.addAnnotatedClass(Client.class);
    configuration.addAnnotatedClass(Panier.class);
    configuration.addAnnotatedClass(ArticlePanier.class);
  }

  private static void setDatabaseConfiguration(Configuration configuration) {
    configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
    configuration.setProperty("hibernate.connection.url", URL);
    configuration.setProperty("hibernate.connection.username", USER);
    configuration.setProperty("hibernate.connection.password", PASSWORD);
    configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
    configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");
  }
}
