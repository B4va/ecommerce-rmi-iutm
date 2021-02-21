create table boutique (
    id SERIAL PRIMARY KEY,
    nom VARCHAR
);

create table client (
    id SERIAL PRIMARY KEY,
    nom VARCHAR,
    prenom VARCHAR,
    mail VARCHAR,
    mot_de_passe VARCHAR
);

create table article (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR,
    prix FLOAT,
    stock INT,
    description VARCHAR,
    imageBlob OID,
    boutique_id INT,
    CONSTRAINT fk_boutique FOREIGN KEY (boutique_id) REFERENCES Boutiques(id)
);

create table commande (
    id SERIAL PRIMARY KEY,
    adresse VARCHAR,
    date DATE,
    livree BOOLEAN,
    client_id INT,
    CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES Clients(id)
);

create table article_commande (
    commande_id INT,
    article_id INT,
    quantite INT,
    CONSTRAINT fk_commande FOREIGN KEY (commande_id) REFERENCES Commandes(id),
    CONSTRAINT fk_article FOREIGN KEY (article_id) REFERENCES Articles(id)
);





