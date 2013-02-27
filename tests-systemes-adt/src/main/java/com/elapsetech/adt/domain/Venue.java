package com.elapsetech.adt.domain;

import java.util.Date;

public class Venue implements Entite {

    private Date date;
    private Departement departement;

    public Venue(Date date, Departement departement) {
        this.date = date;
        this.departement = departement;
    }

    public Date getDate() {
        return date;
    }

    public Departement getDepartement() {
        return departement;
    }

}
