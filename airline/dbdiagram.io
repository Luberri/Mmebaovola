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
  pourcentage decimal
  id_vol bigint [ref: > vol.id]
  id_classe bigint [ref: > classe_voyage.id]
  id_reference bigint [ref: > tarif_vol_classe_type.id]
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
  date_reservation datetime
  statut varchar
  id_passager bigint [ref: > passager.id]
  id_vol bigint [ref: > vol.id]
  id_classe bigint [ref: > classe_voyage.id]
  id_type_vol_classe bigint [ref: > tarif_vol_classe_type.id]
}

Table reservation_siege {
  id bigint [pk, increment]
  num_siege varchar
  id_reservation bigint [ref: > reservation.id]
}

Table paiement {
  id bigint [pk, increment]
  montant decimal
  mode_paiement varchar
  date_paiement datetime
  statut varchar
  id_reservation bigint [ref: > reservation.id]
}

Table employe {
  id bigint [pk, increment]
  nom varchar
  prenom varchar
  email varchar
  telephone varchar
  date_embauche date
  statut varchar
  id_compagnie bigint [ref: > compagnie_aerienne.id]
}

Table poste {
  id bigint [pk, increment]
  libelle varchar
}

Table employe_poste {
  id bigint [pk, increment]
  id_employe bigint [ref: > employe.id]
  id_poste bigint [ref: > poste.id]
}

Table affectation_vol {
  id bigint [pk, increment]
  role_vol varchar
  id_vol bigint [ref: > vol.id]
  id_employe bigint [ref: > employe.id]
}

Table utilisateur {
  id bigint [pk, increment]
  nom varchar
  mot_de_passe varchar
  role_systeme varchar
  id_employe bigint [ref: > employe.id]
}

Table societe {
  id bigint [pk, increment]
  nom varchar
}

Table configuration_prix {
  id bigint [pk, increment]
  prix decimal
}

Table pub_contrat {
  id bigint [pk, increment]
  date_debut date
  date_fin date
  description varchar
  nbr_diffusion int
  id_societe bigint [ref: > societe.id]
  id_configuration_prix bigint [ref: > configuration_prix.id]
}

Table diffusion_vol {
  id bigint [pk, increment]
  nbr_diffusion int
  id_pub_contrat bigint [ref: > pub_contrat.id]
  id_vol bigint [ref: > vol.id]
}

Table paiement_pub {
  id bigint [pk, increment]
  montant decimal
  date_paiement datetime
  description varchar
  id_diffusion_vol bigint [ref: > diffusion_vol.id]
}
