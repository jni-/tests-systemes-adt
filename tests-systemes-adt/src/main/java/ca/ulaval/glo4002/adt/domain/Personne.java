package ca.ulaval.glo4002.adt.domain;

import java.util.Date;

public class Personne {

    protected String prenom;
    protected String nom;
    private Date dateNaissance;

    public Personne(String prenom, String nom) {
        this.prenom = prenom;
        this.nom = nom;

    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

}
