package ca.ulaval.glo4002.adt.domain;

import java.util.Date;

public class Transfers implements Entite {

    private Departement departement;
    private Date date;

    public Transfers(Date date, Departement departement) {
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
