-- =========================
-- COMPAGNIE AERIENNE
-- =========================
INSERT INTO compagnie_aerienne (nom, code_iata, pays)
VALUES
('Air Alpha', 'AA', 'Madagascar');

-- =========================
-- AEROPORT
-- =========================
INSERT INTO aeroport (nom, ville, pays, code_iata)
VALUES
('Ivato International', 'TANA', 'TANA', 'TNR'),
('nosy be', 'NOSY BE', 'NOSY BE', 'MRU');

-- =========================
-- AVION
-- =========================
INSERT INTO avion (modele, capacite_totale, immatriculation, etat, id_compagnie)
VALUES
('Boeing 737', 180, 'AA-737-01', 'EN_SERVICE', 1);

-- =========================
-- CLASSE DE VOYAGE
-- =========================
INSERT INTO classe_voyage (libelle, description)
VALUES
('ECONOMIQUE', 'Classe économique'),
('PREMIERE', 'premiere classe'),
('PRENIUM', 'prenium classe');

-- =========================
-- VOL
-- =========================
INSERT INTO vol (
    numero_vol,
    id_compagnie,
    id_avion,
    id_aeroport_depart,
    id_aeroport_arrivee,
    date_heure_depart,
    date_heure_arrivee,
    statut
)
VALUES
('AA101', 1, 1, 1, 2, '2026-02-01 08:00:00', '2026-02-01 10:30:00', 'PREVU');


-- Exemple d'insertion pour capacite_avion_classe
INSERT INTO capacite_avion_classe (nbr_place, id_classe, id_avion) VALUES
  (30, 3, 1),
  (40, 2, 1),
  (50, 1, 1);

-- =========================
-- TARIF VOL CLASSE
-- =========================
INSERT INTO tarif_vol_classe (id_vol, id_classe, prix, places_disponibles) VALUES
  (1, 1, 700000, 50),      -- Economique
  (1, 2, 1200000, 40),     -- 1ère classe
  (1, 3, 1000000, 30);     -- Prenium