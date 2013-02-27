package ca.ulaval.glo4002.adt.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Venue implements Entite {

    private Date date;
    private String raison;
    private Departement departement;
    private List<Transfers> transfers;

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

    public String getRaisonDeVenue() {
        return raison;
    }

    public void setRaisonDeVenue(String raison) {
        this.raison = raison;
    }

    public void transfererMaintenantVers(Departement departement) {
        if (transfers == null) {
            transfers = new LinkedList<>();
        }
        transfers.add(new Transfers(obtenirDateCourante(), departement));
    }

    Date obtenirDateCourante() {
        return Calendar.getInstance().getTime();
    }

    public List<Transfers> getTransfers() {
        return transfers;
    }

}
