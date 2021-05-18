-- RACUN PODACI
INSERT INTO "racun"("id", "datum", "nacin_placanja")
VALUES (NEXTVAL('racun_seq'), to_date('01.03.2021.', 'dd.mm.yyyy.'), 'Gotovina');
INSERT INTO "racun"("id", "datum", "nacin_placanja")
VALUES (NEXTVAL('racun_seq'), to_date('25.02.2020.', 'dd.mm.yyyy.'), 'Kartica');
INSERT INTO "racun"("id", "datum", "nacin_placanja")
VALUES (NEXTVAL('racun_seq'), to_date('05.05.2019.', 'dd.mm.yyyy.'), 'Kartica');
INSERT INTO "racun"("id", "datum", "nacin_placanja")
VALUES (NEXTVAL('racun_seq'), to_date('22.02.2021.', 'dd.mm.yyyy.'), 'Gotovina');
INSERT INTO "racun"("id", "datum", "nacin_placanja")
VALUES (NEXTVAL('racun_seq'), to_date('19.01.2020.', 'dd.mm.yyyy.'), 'Gotovina');

--TEST PODACI
INSERT INTO "racun"("id", "datum", "nacin_placanja")
VALUES (-100, to_date('07.08.2077.', 'dd.mm.yyyy.'), 'TestNacinPlacanja');


-- PROIZVODJAC PODACI
INSERT INTO "proizvodjac"("id", "naziv", "adresa", "kontakt")
VALUES (NEXTVAL('proizvodjac_seq'), 'Ludwig Schokolade', 'Lebacher Str. 1-3, Nemacka', '+492133344');
INSERT INTO "proizvodjac"("id", "naziv", "adresa", "kontakt")
VALUES (NEXTVAL('proizvodjac_seq'), 'Bambi', 'Djure Djakovica, Pozarevac', '+38121777111');
INSERT INTO "proizvodjac"("id", "naziv", "adresa", "kontakt")
VALUES (NEXTVAL('proizvodjac_seq'), 'Swisslion Takovo', 'Partizanska 42, Novi Sad', '+38121999222');
INSERT INTO "proizvodjac"("id", "naziv", "adresa", "kontakt")
VALUES (NEXTVAL('proizvodjac_seq'), 'Mondelez International', 'Schonbrunner Str. 297/307, Austrija', '+4321222111');
INSERT INTO "proizvodjac"("id", "naziv", "adresa", "kontakt")
VALUES (NEXTVAL('proizvodjac_seq'), 'Stark', 'Milisava Djurovica 249, Beograd', '+381885749');

--TEST PODACI
INSERT INTO "proizvodjac"("id", "naziv", "adresa", "kontakt")
VALUES (-100, 'TestNaziv', 'TestAdresa', 'TestKontakt');

-- PROIZVOD PODACI
INSERT INTO "proizvod"("id", "naziv", "proizvodjac")
VALUES (NEXTVAL('proizvod_seq'), 'Schogetten 100g Oreo', 1);
INSERT INTO "proizvod"("id", "naziv", "proizvodjac")
VALUES (NEXTVAL('proizvod_seq'), 'Plazma Mlevena 350g', 2);
INSERT INTO "proizvod"("id", "naziv", "proizvodjac")
VALUES (NEXTVAL('proizvod_seq'), 'Eurocrem 1kg', 3);
INSERT INTO "proizvod"("id", "naziv", "proizvodjac")
VALUES (NEXTVAL('proizvod_seq'), 'Milka Almond Caramel 250g', 4);
INSERT INTO "proizvod"("id", "naziv", "proizvodjac")
VALUES (NEXTVAL('proizvod_seq'), 'Coko Keksici 250g', 5);

--TEST PODACI
INSERT INTO "proizvod"("id", "naziv", "proizvodjac")
VALUES (-100, 'TestNaziv', 5);


-- STAVKA RACUNA PODACI

INSERT INTO "stavka_racuna"("id", "racun", "redni_broj", "proizvod", "kolicina", "jedinica_mere", "cena")
VALUES (NEXTVAL('stavka_racuna_seq'), 1, 1, 1, 20, 'komad', 100);
INSERT INTO "stavka_racuna"("id", "racun", "redni_broj", "proizvod", "kolicina", "jedinica_mere", "cena")
VALUES (NEXTVAL('stavka_racuna_seq'), 1, 2, 2, 10, 'komad', 300);
INSERT INTO "stavka_racuna"("id", "racun", "redni_broj", "proizvod", "kolicina", "jedinica_mere", "cena")
VALUES (NEXTVAL('stavka_racuna_seq'), 1, 3, 3, 15, 'komad', 120);

INSERT INTO "stavka_racuna"("id", "racun", "redni_broj", "proizvod", "kolicina", "jedinica_mere", "cena")
VALUES (NEXTVAL('stavka_racuna_seq'), 2, 1, 4, 100, 'komad', 400);
INSERT INTO "stavka_racuna"("id", "racun", "redni_broj", "proizvod", "kolicina", "jedinica_mere", "cena")
VALUES (NEXTVAL('stavka_racuna_seq'), 2, 2, 5, 60, 'komad', 350);
INSERT INTO "stavka_racuna"("id", "racun", "redni_broj", "proizvod", "kolicina", "jedinica_mere", "cena")
VALUES (NEXTVAL('stavka_racuna_seq'), 2, 3, 2, 30, 'komad', 300);

INSERT INTO "stavka_racuna"("id", "racun", "redni_broj", "proizvod", "kolicina", "jedinica_mere", "cena")
VALUES (NEXTVAL('stavka_racuna_seq'), 3, 1, 3, 50, 'komad', 80);
INSERT INTO "stavka_racuna"("id", "racun", "redni_broj", "proizvod", "kolicina", "jedinica_mere", "cena")
VALUES (NEXTVAL('stavka_racuna_seq'), 3, 2, 1, 30, 'komad', 120);
INSERT INTO "stavka_racuna"("id", "racun", "redni_broj", "proizvod", "kolicina", "jedinica_mere", "cena")
VALUES (NEXTVAL('stavka_racuna_seq'), 3, 3, 5, 100, 'komad', 150);

--TEST PODACI
INSERT INTO "stavka_racuna"("id", "racun", "redni_broj", "proizvod", "kolicina", "jedinica_mere", "cena")
VALUES (-100, 1, 1, 1, 1, 'komad', 100);
