

-- =========================
-- COMPAGNIE
-- =========================
INSERT INTO compagnie_aerienne (nom, pays, code_iata, code_icao, actif)
VALUES ('Madagascar Airlines', 'Madagascar', 'MD', 'MDG', true);

-- =========================
-- AÉROPORTS
-- =========================
INSERT INTO aeroport (nom, ville, pays, code_iata, code_icao, actif) VALUES
('Ivato International Airport', 'Antananarivo', 'Madagascar', 'TNR', 'FMMI', true),
('Fascene Airport', 'Nosy Be', 'Madagascar', 'NOS', 'FMNN', true);

-- =========================
-- AVION
-- =========================
INSERT INTO avion (modele, capacite_totale, immatriculation, etat, id_compagnie)
VALUES ('Boeing 737-800', 160, '5R-MBA', 'EN_SERVICE', 1); -- EtatAvion.EN_SERVICE

-- =========================
-- CLASSES DE VOYAGE
-- =========================
INSERT INTO classe_voyage (libelle) VALUES
('Premiere classe'),
('Economique');

-- =========================
-- VOL : TANA -> NOSY BE
-- =========================
INSERT INTO vol (
  numero_vol,
  date_heure_depart,
  date_heure_arrivee,
  statut,
  id_compagnie,
  id_avion,
  id_aeroport_depart,
  id_aeroport_arrivee
) VALUES (
  'MD101',
  '2026-01-20 08:00',
  '2026-01-20 09:45',
  'PROGRAMME', -- StatutVol.PROGRAMME
  1,
  1,
  1,
  2
);

-- =========================
-- TARIFS PAR CLASSE
-- =========================
-- Première classe : 10 sièges à 1 200 000 Ar
INSERT INTO tarif_vol_classe (
  prix,
  places_disponibles,
  id_vol,
  id_classe
) VALUES (
  1200000,
  10,
  1,
  1
);

-- Économique : 150 sièges à 700 000 Ar
INSERT INTO tarif_vol_classe (
  prix,
  places_disponibles,
  id_vol,
  id_classe
) VALUES (
  700000,
  150,
  1,
  2
);

-- =========================
-- PASSAGERS
-- =========================
INSERT INTO passager (nom, prenom, numero_passeport, email, telephone) VALUES
('Rakoto', 'Jean', 'MG123456', 'jean.rakoto@email.mg', '0340000001'),
('Rabe', 'Marie', 'MG654321', 'marie.rabe@email.mg', '0340000002');

-- =========================
-- RÉSERVATIONS
-- =========================
INSERT INTO reservation (
  numero_siege,
  prix_paye,
  date_reservation,
  statut,
  id_passager,
  id_vol,
  id_classe
) VALUES
('1A', 1200000, NOW(), 'CONFIRMEE', 1, 1, 1), -- StatutReservation.CONFIRMEE
('22C', 700000, NOW(), 'CONFIRMEE', 2, 1, 2);

-- =========================
-- PAIEMENTS
-- =========================
INSERT INTO paiement (
  montant,
  mode_paiement,
  date_paiement,
  statut,
  id_reservation
) VALUES
(1200000, 'ESPECES', NOW(), 'VALIDE', 1), -- ModePaiement.ESPECES, StatutPaiement.VALIDE
(700000, 'ESPECES', NOW(), 'VALIDE', 2); -- ModePaiement.ESPECES, StatutPaiement.VALIDE

-- =========================
-- TEST : VALEUR MAXIMALE DU VOL
-- =========================
-- Résultat attendu :
-- (10 × 1 200 000) + (150 × 700 000) = 117 000 000 Ar

SELECT
  v.numero_vol,
  SUM(t.prix_base * t.nombre_sieges) AS valeur_maximale
FROM vol v
JOIN tarif_vol_classe t ON t.id_vol = v.id
WHERE v.id = 1
GROUP BY v.numero_vol;
