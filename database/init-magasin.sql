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
    id SERIAL PRIMARY KEY,
    commande_id INT,
    article_id INT,
    quantite INT,
    CONSTRAINT fk_commande FOREIGN KEY (commande_id) REFERENCES commande(id),
    CONSTRAINT fk_article FOREIGN KEY (article_id) REFERENCES article(id)
);

CREATE TABLE panier (
    id SERIAL PRIMARY KEY,
    client_id INT,
    CONSTRAINT fk_client_panier FOREIGN KEY (client_id) REFERENCES client(id)
);

CREATE TABLE article_panier (
    id SERIAL PRIMARY KEY,
    panier_id INT,
    article_id INT,
    quantite INT,
    CONSTRAINT fk_panier FOREIGN KEY (panier_id) REFERENCES panier(id),
    CONSTRAINT fk_article_panier FOREIGN KEY (article_id) REFERENCES article(id)
);

INSERT INTO client (nom, prenom, mail, mot_de_passe) values ('Smith', 'John', 'john@mail.com', '1234');

INSERT INTO boutique (nom) VALUES ('Match');
INSERT INTO boutique (nom) VALUES ('Bricomarché');
INSERT INTO boutique (nom) VALUES ('Auchan');

INSERT INTO article (libelle, prix, stock, description, image_blob, boutique_id) VALUES ('pomme', 1.20, 50, 'Pomme rouge bien mure 1.20€/unité', null, 1);
INSERT INTO article (libelle, prix, stock, description, image_blob, boutique_id) VALUES ('banane', 1.00, 25, 'Banane bien mure 1.00€/unité', null, 1);
INSERT INTO article (libelle, prix, stock, description, image_blob, boutique_id) VALUES ('orange', 1.50, 30, 'Orange bien mure 1.50€/unité', null, 1);
INSERT INTO article (libelle, prix, stock, description, image_blob, boutique_id) VALUES ('fraise', 0.10, 100, 'Fraise bien juteuse 0.10€/unité', null, 1);
INSERT INTO article (libelle, prix, stock, description, image_blob, boutique_id) VALUES ('salade', 0.50, 15, 'Salade verte fraiche 0.50€/unité', null, 1);

INSERT INTO article (libelle, prix, stock, description, image_blob, boutique_id) VALUES ('marteau', 5.20, 10, 'Marteau en acier 5.20€/unité', null, 2);
INSERT INTO article (libelle, prix, stock, description, image_blob, boutique_id) VALUES ('clou', 0.05, 100, 'Clou en acier 0.05€/unité', null, 2);
INSERT INTO article (libelle, prix, stock, description, image_blob, boutique_id) VALUES ('perceuse', 75.99, 5, 'Peceuse hight-tech 75.99€/unité', null, 2);
INSERT INTO article (libelle, prix, stock, description, image_blob, boutique_id) VALUES ('Tournevis', 12.30, 15, 'Tournevis en acier 12.30€/unité', null, 2);

INSERT INTO article (libelle, prix, stock, description, image_blob, boutique_id) VALUES ('Ordinateur portable HP', 399.20, 7, 'Ordinateur HP Intel Core I3  399.20€/unité', null, 3);
INSERT INTO article (libelle, prix, stock, description, image_blob, boutique_id) VALUES ('Télévision 32 pouce', 299.99, 3, 'Ecran LG 32 pouce 299.99€/unité', null, 3);
INSERT INTO article (libelle, prix, stock, description, image_blob, boutique_id) VALUES ('Tablette Samsung', 199.00, 5, 'Tablette Samsung 32 Go, 3Go RAM  199.00€/unité', null, 3);
INSERT INTO article (libelle, prix, stock, description, image_blob, boutique_id) VALUES ('Portable', 5.20, 10, 'Marteau en acier 5.20€/unité', null, 2);
