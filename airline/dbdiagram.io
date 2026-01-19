Table compagnie_aerienne {
  id bigint [pk, increment]
  nom varchar
  code_iata varchar
  pays varchar
}

Table aeroport {
  id bigint [pk, increment]
  nom varchar
  ville varchar
  pays varchar
  code_iata varchar
}

Table avion {
  id bigint [pk, increment]
  modele varchar
  capacite_totale int
  immatriculation varchar
  etat varchar
  id_compagnie bigint [ref: > compagnie_aerienne.id]
}

Table classe_voyage {
  id bigint [pk, increment]
  libelle varchar
  description varchar
}

Table vol {
  id bigint [pk, increment]
  numero_vol varchar
  date_heure_depart datetime
  date_heure_arrivee datetime
  statut varchar
  id_compagnie bigint [ref: > compagnie_aerienne.id]
  id_avion bigint [ref: > avion.id]
  id_aeroport_depart bigint [ref: > aeroport.id]
  id_aeroport_arrivee bigint [ref: > aeroport.id]
}

Table capacite_avion_classe_vol {
  id bigint [pk, increment]
  nbr_place int
  id_vol bigint [ref: > vol.id]
  id_classe bigint [ref: > classe_voyage.id]
}

Table tarif_vol_classe_type {
  id bigint [pk, increment]
  prix decimal
  type varchar
  id_vol bigint [ref: > vol.id]
  id_classe bigint [ref: > classe_voyage.id]
}

Table passager {
  id bigint [pk, increment]
  nom varchar
  prenom varchar
  numero_passeport varchar
  email varchar
  telephone varchar
}

Table reservation {
  id bigint [pk, increment]
  numero_siege varchar
  date_reservation datetime
  statut varchar
  id_passager bigint [ref: > passager.id]
  id_vol bigint [ref: > vol.id]
  id_classe bigint [ref: > classe_voyage.id]
  id_type_vol_classe bigint [ref: > tarif_vol_classe_type.id]
}

Table paiement {
  id bigint [pk, increment]
  montant decimal
  mode_paiement varchar
  date_paiement datetime
  statut varchar
  id_reservation bigint [ref: > reservation.id]
}
