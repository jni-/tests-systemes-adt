package com.elapsetech.adt.domain;

public class Departement implements Entite {

    private String code;
    private String nom;

    public Departement(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean possedeCode(String code) {
        return this.code.equals(code);
    }

    public String getCode() {
        return code;
    }

}
