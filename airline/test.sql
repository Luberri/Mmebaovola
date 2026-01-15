-- =========================
-- COMPAGNIES AERIENNES
-- =========================
INSERT INTO compagnie_aerienne (nom, pays, code_iata, code_icao, actif)
VALUES
('Air Madagascar', 'Madagascar', 'MD', 'MDG', true),
('Air France', 'France', 'AF', 'AFR', true);

-- =========================
-- AEROPORTS
-- =========================
INSERT INTO aeroport (nom, ville, pays, code_iata, code_icao, actif)
VALUES
('Ivato International Airport', 'Antananarivo', 'Madagascar', 'TNR', 'FMMI', true),
('Fascene Airport', 'Nosy Be', 'Madagascar', 'NOS', 'FMNN', true),
('Charles de Gaulle Airport', 'Paris', 'France', 'CDG', 'LFPG', true);

-- =========================
-- AVIONS
-- =========================
INSERT INTO avion (modele, capacite_totale, immatriculation, etat, id_compagnie)
VALUES
('Boeing 737-800', 160, '5R-MBA', 'EN_SERVICE', 1),
('Airbus A320', 180, 'F-GKXA', 'EN_SERVICE', 2);

-- =========================
-- CLASSES DE VOYAGE
-- =========================
INSERT INTO classe_voyage (libelle, description)
VALUES
('Economique', 'Classe économique'),
('Business', 'Classe affaires');

-- =========================
-- CAPACITE AVION PAR CLASSE
-- =========================
INSERT INTO capacite_avion_classe (nbr_sieges, id_avion, id_classe)
VALUES
(120, 1, 1), -- 120 sièges économiques pour le Boeing 737
(40, 1, 2),  -- 40 sièges business pour le Boeing 737
(150, 2, 1), -- 150 sièges économiques pour l'Airbus A320
(30, 2, 2);  -- 30 sièges business pour l'Airbus A320

-- =========================
-- VOLS
-- =========================
INSERT INTO vol (numero_vol, date_heure_depart, date_heure_arrivee, statut, id_compagnie, id_avion, id_aeroport_depart, id_aeroport_arrivee)
VALUES
('MD101', '2026-01-20 08:00:00', '2026-01-20 09:45:00', 'PROGRAMME', 1, 1, 1, 2), -- TNR -> NOS
('AF202', '2026-01-21 10:00:00', '2026-01-21 14:00:00', 'PROGRAMME', 2, 2, 3, 1); -- CDG -> TNR

-- =========================
-- TARIFS PAR CLASSE
-- =========================
INSERT INTO tarif_vol_classe (prix, places_disponibles, id_vol, id_classe)
VALUES
(700000, 120, 1, 1), -- 700 000 Ar pour la classe économique sur le vol MD101
(1500000, 40, 1, 2), -- 1 500 000 Ar pour la classe business sur le vol MD101
(500, 150, 2, 1),    -- 500 € pour la classe économique sur le vol AF202
(1200, 30, 2, 2);    -- 1 200 € pour la classe business sur le vol AF202