Table CompagnieAerienne {
  id_compagnie int [pk, increment]
  nom varchar
  pays varchar
  code_iata char(2)
  code_icao char(3)
  actif boolean
}

Table Aeroport {
  id_aeroport int [pk, increment]
  nom varchar
  ville varchar
  pays varchar
  code_iata char(3)
  code_icao char(4)
  actif boolean
}

Table Avion {
  id_avion int [pk, increment]
  modele varchar
  capacite_totale int
  immatriculation varchar
  etat varchar
  id_compagnie int [ref: > CompagnieAerienne.id_compagnie]
}

Table ClasseVoyage {
  id_classe int [pk, increment]
  libelle varchar
  ordre_affichage int
}

Table Vol {
  id_vol int [pk, increment]
  numero_vol varchar
  date_heure_depart datetime
  date_heure_arrivee datetime
  statut varchar
  id_compagnie int [ref: > CompagnieAerienne.id_compagnie]
  id_avion int [ref: > Avion.id_avion]
  id_aeroport_depart int [ref: > Aeroport.id_aeroport]
  id_aeroport_arrivee int [ref: > Aeroport.id_aeroport]
}

Table TarifVolClasse {
  id_tarif int [pk, increment]
  prix_base decimal
  nombre_sieges int
  sieges_restants int
  id_vol int [ref: > Vol.id_vol]
  id_classe int [ref: > ClasseVoyage.id_classe]
}

Table Passager {
  id_passager int [pk, increment]
  nom varchar
  prenom varchar
  numero_passeport varchar
  email varchar
  telephone varchar
}

Table Reservation {
  id_reservation int [pk, increment]
  numero_siege varchar
  prix_paye decimal
  date_reservation datetime
  statut varchar
  id_passager int [ref: > Passager.id_passager]
  id_vol int [ref: > Vol.id_vol]
  id_classe int [ref: > ClasseVoyage.id_classe]
}

Table Paiement {
  id_paiement int [pk, increment]
  montant decimal
  mode_paiement varchar
  date_paiement datetime
  statut varchar
  id_reservation int [ref: > Reservation.id_reservation]
}

Table Employe {
  id_employe int [pk, increment]
  nom varchar
  prenom varchar
  email varchar
  telephone varchar
  date_embauche date
  statut varchar
  id_compagnie int [ref: > CompagnieAerienne.id_compagnie]
}

Table Poste {
  id_poste int [pk, increment]
  libelle varchar
}

Table EmployePoste {
  id int [pk, increment]
  id_employe int [ref: > Employe.id_employe]
  id_poste int [ref: > Poste.id_poste]
}

Table AffectationVol {
  id_affectation int [pk, increment]
  role_vol varchar
  id_vol int [ref: > Vol.id_vol]
  id_employe int [ref: > Employe.id_employe]
}

Table Utilisateur {
  id_utilisateur int [pk, increment]
  email varchar
  mot_de_passe varchar
  role_systeme varchar
  actif boolean
  id_employe int [ref: > Employe.id_employe]
}
