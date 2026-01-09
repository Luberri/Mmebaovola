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
('Ivato International', 'Antananarivo', 'Madagascar', 'TNR'),
('Sir Seewoosagur Ramgoolam', 'Mauritius', 'Mauritius', 'MRU');

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
('BUSINESS', 'Classe affaires'),
('PREMIERE', 'Première classe');

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

-- =========================
-- PASSAGER
-- =========================
INSERT INTO passager (nom, prenom, email, telephone)
VALUES
('Rabe', 'Jean', 'jean.rabe@gmail.com', '0340000001'),
('Rakoto', 'Marie', 'marie.rakoto@gmail.com', '0340000002');

-- =========================
-- RESERVATION
-- =========================
INSERT INTO reservation (
    id_passager,
    id_vol,
    id_classe,
    numero_siege,
    prix_paye,
    date_reservation,
    statut
)
VALUES
(1, 1, 1, '12A', 300.00, '2026-01-20 09:00:00', 'CONFIRMEE'),
(2, 1, 2, '02B', 450.00, '2026-01-20 09:10:00', 'CONFIRMEE');

-- =========================
-- PAIEMENT
-- =========================
INSERT INTO paiement (
    id_reservation,
    montant,
    mode_paiement,
    date_paiement,
    statut
)
VALUES
(1, 300.00, 'CARTE_BANCAIRE', '2026-01-20 09:05:00', 'VALIDE'),
(2, 450.00, 'VIREMENT', '2026-01-20 09:15:00', 'VALIDE');

-- =========================
-- EMPLOYE
-- =========================
INSERT INTO employe (
    id_compagnie,
    nom,
    prenom,
    email,
    telephone,
    date_embauche,
    statut
)
VALUES
(1, 'Andriamanitra', 'Paul', 'paul.andria@airalpha.com', '0320000001', '2020-01-10', 'ACTIF'),
(1, 'Razafy', 'Luc', 'luc.razafy@airalpha.com', '0320000002', '2021-03-15', 'ACTIF');

-- =========================
-- AFFECTATION VOL
-- =========================
INSERT INTO affectation_vol (id_vol, id_employe, role_vol)
VALUES
(1, 1, 'COMMANDANT'),
(1, 2, 'CHEF_CABINE');

-- =========================
-- UTILISATEUR (ADMIN / AGENT)
-- =========================
INSERT INTO utilisateur (
    nom,
    mot_de_passe,
    role_systeme,
    id_employe
)
VALUES
('admin', 'test', 'ADMIN', 1),
('agent', 'agent123', 'AGENT', 2);