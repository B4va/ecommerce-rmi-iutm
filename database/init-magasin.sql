CREATE TABLE boutique (
    id SERIAL PRIMARY KEY,
    nom VARCHAR
);

CREATE TABLE client (
    id SERIAL PRIMARY KEY,
    nom VARCHAR,
    prenom VARCHAR,
    mail VARCHAR,
    mot_de_passe VARCHAR
);

CREATE TABLE article (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR,
    prix FLOAT,
    stock INT,
    description VARCHAR,
    image_blob OID,
    boutique_id INT,
    CONSTRAINT fk_boutique FOREIGN KEY (boutique_id) REFERENCES boutique(id)
);

CREATE TABLE commande (
    id SERIAL PRIMARY KEY,
    adresse VARCHAR,
    date DATE,
    livree BOOLEAN,
    client_id INT,
    CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES client(id)
);

CREATE TABLE article_commande (
    commande_id INT,
    article_id INT,
    quantite INT,
    CONSTRAINT fk_commande FOREIGN KEY (commande_id) REFERENCES commande(id),
    CONSTRAINT fk_article FOREIGN KEY (article_id) REFERENCES article(id)
);
