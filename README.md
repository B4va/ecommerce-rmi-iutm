# Projet eCommerce RMI - IUTM

**Participants au projet :**

- BEER Alexis
- PYTHOUD Arnaud
- STEINMETZ Loïc

## Exécution

### Initialisation des bases de données

- Première exécution :

```
$ docker-compose up --build -d
```

- Relancer l'application : 

```
$ docker-compose up
```

- Supprimer les conteneurs installés :

```
$ docker container rm db-magasin db-banque
```

- Supprimer les images installées :

```
$ docker image rm ecommerce-rmi-iutm_client ecommerce-rmi-iutm_magasin ecommerce-rmi-iutm_banque
```

### Démarrage des programmes

1. Lancer l'application Banque via la classe exécutable `app.App`, dans `\banque`.
2. Lancer l'application Magasin via la classe exécutable `app.App`, dans `\magasin`.
3. Lancer l'application GUI via la classe exécutable `app.App`, dans `\client`.

## Conception

### Cas d'utilisation

![cas d'utilisation](img-readme/conception-projet-cas-dutilisation.png)

### Architecture générale

![architecture](img-readme/conception-projet-archi.png)

### Classes

![classes-1](img-readme/conception-projet-apps-1.png)
![classes-1](img-readme/conception-projet-apps-2.png)
![classes-3](img-readme/conception-projet-apps-3.png)

### Données

![données](img-readme/conception-projet-donnees.png)