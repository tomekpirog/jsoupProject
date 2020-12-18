/*DROP TABLE IF EXISTS boardgames;

CREATE TABLE boardgames (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  title VARCHAR(250) NOT NULL,
  link VARCHAR(250) NOT NULL,
  price DOUBLE DEFAULT NULL
);*/
DELETE FROM boardgames;
INSERT INTO boardgames (id, title, link, price) VALUES (1, 'Catan', 'www.gryplanszowe.pl', 99.00);
INSERT INTO boardgames (id, title, link, price) VALUES (2, 'Dixit', 'www.aleplanszowki.pl', 15.00);
INSERT INTO boardgames (id, title, link, price) VALUES (3, 'Bierki', 'www.alto.pl', 44.00);
INSERT INTO boardgames (id, title, link, price) VALUES (4, 'Ligretto', 'www.3trolle.pl', 35.00);
