-- =========================================================
-- CREATION DES TABLES
-- =========================================================

-- =========================
-- COMPAGNIE AERIENNE
-- =========================
CREATE TABLE compagnie_aerienne (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    code_iata VARCHAR(10) UNIQUE,
    pays VARCHAR(100)
);

-- =========================
-- AEROPORT
-- =========================
CREATE TABLE aeroport (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    ville VARCHAR(100),
    pays VARCHAR(100),
    code_iata VARCHAR(10) UNIQUE
);

-- =========================
-- AVION
-- =========================
CREATE TABLE avion (
    id BIGSERIAL PRIMARY KEY,
    modele VARCHAR(100),
    capacite_totale INT NOT NULL,
    immatriculation VARCHAR(50) UNIQUE,
    etat VARCHAR(50),
    id_compagnie BIGINT REFERENCES compagnie_aerienne(id)
);

-- =========================
-- CLASSE DE VOYAGE
-- =========================
CREATE TABLE classe_voyage (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL,
    description TEXT
);

-- =========================
-- VOL
-- =========================
CREATE TABLE vol (
    id BIGSERIAL PRIMARY KEY,
    numero_vol VARCHAR(20) NOT NULL,
    date_heure_depart TIMESTAMP NOT NULL,
    date_heure_arrivee TIMESTAMP NOT NULL,
    statut VARCHAR(50),
    id_compagnie BIGINT REFERENCES compagnie_aerienne(id),
    id_avion BIGINT REFERENCES avion(id),
    id_aeroport_depart BIGINT REFERENCES aeroport(id),
    id_aeroport_arrivee BIGINT REFERENCES aeroport(id)
);

-- =========================
-- CAPACITE PAR CLASSE ET PAR VOL
-- =========================
CREATE TABLE capacite_avion_classe_vol (
    id BIGSERIAL PRIMARY KEY,
    nbr_place INT NOT NULL,
    id_vol BIGINT REFERENCES vol(id),
    id_classe BIGINT REFERENCES classe_voyage(id),
    UNIQUE (id_vol, id_classe)
);

-- =========================
-- TARIF PAR VOL + CLASSE + TYPE
-- =========================
CREATE TABLE tarif_vol_classe_type (
    id BIGSERIAL PRIMARY KEY,
    prix DECIMAL(12,2) NOT NULL,
    type VARCHAR(50) NOT NULL, -- ADULTE, ENFANT, PROMO...
    id_vol BIGINT REFERENCES vol(id),
    id_classe BIGINT REFERENCES classe_voyage(id),
    UNIQUE (id_vol, id_classe, type)
);

-- =========================
-- PASSAGER
-- =========================
CREATE TABLE passager (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(100),
    prenom VARCHAR(100),
    numero_passeport VARCHAR(50) UNIQUE,
    email VARCHAR(100),
    telephone VARCHAR(30)
);

-- =========================
-- RESERVATION
-- =========================
CREATE TABLE reservation (
    id BIGSERIAL PRIMARY KEY,
    numero_siege VARCHAR(10),
    date_reservation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    statut VARCHAR(50),
    id_passager BIGINT REFERENCES passager(id),
    id_vol BIGINT REFERENCES vol(id),
    id_classe BIGINT REFERENCES classe_voyage(id),
    id_type_vol_classe BIGINT REFERENCES tarif_vol_classe_type(id)
);

-- =========================
-- PAIEMENT
-- =========================
CREATE TABLE paiement (
    id BIGSERIAL PRIMARY KEY,
    montant DECIMAL(12,2),
    mode_paiement VARCHAR(50),
    date_paiement TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    statut VARCHAR(50),
    id_reservation BIGINT REFERENCES reservation(id)
);

-- =========================================================
-- INSERTION DES DONNEES DE TEST
-- =========================================================

-- Compagnie
INSERT INTO compagnie_aerienne (nom, code_iata, pays)
VALUES ('Air Madagascar', 'MD', 'Madagascar');

-- Aéroports
INSERT INTO aeroport (nom, ville, pays, code_iata) VALUES
('Ivato', 'Antananarivo', 'Madagascar', 'TNR'),
('Charles de Gaulle', 'Paris', 'France', 'CDG');

-- Avion
INSERT INTO avion (modele, capacite_totale, immatriculation, etat, id_compagnie)
VALUES ('Boeing 737', 180, '5R-MDA', 'EN_SERVICE', 1);

-- Classes
INSERT INTO classe_voyage (libelle, description) VALUES
('ECONOMIQUE', 'Classe économique'),
('BUSINESS', 'Classe affaires');

-- Vol
INSERT INTO vol (
    numero_vol, date_heure_depart, date_heure_arrivee, statut,
    id_compagnie, id_avion, id_aeroport_depart, id_aeroport_arrivee
) VALUES (
    'MD123',
    '2026-02-01 08:00',
    '2026-02-01 18:00',
    'PROGRAMME',
    1, 1, 1, 2
);

-- Capacité par classe pour ce vol
INSERT INTO capacite_avion_classe_vol (nbr_place, id_vol, id_classe) VALUES
(150, 1, 1),
(30, 1, 2);

-- Tarifs du vol
INSERT INTO tarif_vol_classe_type (prix, type, id_vol, id_classe) VALUES
(700000, 'ADULTE', 1, 1),
(600000, 'ENFANT', 1, 1),
(1500000, 'ADULTE', 1, 2);

-- Passagers
INSERT INTO passager (nom, prenom, numero_passeport, email, telephone) VALUES
('Rabe', 'Jean', 'MG123456', 'jean@mail.com', '0340000001'),
('Rakoto', 'Anna', 'MG654321', 'anna@mail.com', '0340000002');

-- Réservations
INSERT INTO reservation (
    numero_siege, statut, id_passager, id_vol, id_classe, id_type_vol_classe
) VALUES
('12A', 'CONFIRMEE', 1, 1, 1, 1),
('12B', 'CONFIRMEE', 2, 1, 1, 2);

-- Paiements
INSERT INTO paiement (montant, mode_paiement, statut, id_reservation) VALUES
(700000, 'CARTE', 'PAYE', 1),
(600000, 'MOBILE_MONEY', 'PAYE', 2);
