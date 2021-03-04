CREATE TABLE compte_bancaire(
	id SERIAL PRIMARY KEY,
	reference VARCHAR,
	solde FLOAT
);

INSERT INTO compte_bancaire (reference, solde) values ('123456789', 1000.00);
