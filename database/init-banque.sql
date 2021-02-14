CREATE TABLE compte_bancaire(
	id SERIAL PRIMARY KEY,
	reference VARCHAR,
	solde FLOAT
)

INSERT INTO compte_bancaire values (reference,solde) ('9999 9999 9999 9999', 1000.50)
INSERT INTO compte_bancaire values (reference,solde) ('6666 6666 6666 6666', 666.66)
INSERT INTO compte_bancaire values (reference,solde) ('1010 1001 1011 0101', 10.30)
INSERT INTO compte_bancaire values (reference,solde) ('9876 5432 1987 6543', 0.00)

