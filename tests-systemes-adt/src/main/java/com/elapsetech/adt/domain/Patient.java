package com.elapsetech.adt.domain;

public class Patient extends Personne implements Entite {

    private final String nam;

    public Patient(String nam, String prenom, String nom) {
        super(prenom, nom);
        this.nam = nam;
    }

    public String getNam() {
        return nam;
    }

}
