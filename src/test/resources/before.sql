/*DROP TABLE IF EXISTS boardgames;

CREATE TABLE boardgames (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  title VARCHAR(250) NOT NULL,
  link VARCHAR(250) NOT NULL,
  price DOUBLE DEFAULT NULL
);*/

INSERT INTO boardgames VALUES (1, 'Catan', 'www.gryplanszowe.pl', DOUBLE(99.00));
INSERT INTO boardgames VALUES (2, 'Dixit', 'www.aleplanszowki.pl', DOUBLE(15));
INSERT INTO boardgames VALUES (3, 'Bierki', 'www.alto.pl', DOUBLE(44));
INSERT INTO boardgames VALUES (4, 'Ligretto', 'www.3trolle.pl', DOUBLE(35));
