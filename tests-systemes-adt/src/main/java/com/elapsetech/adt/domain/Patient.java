package com.elapsetech.adt.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Patient implements Entite {

    private String nomComplet;
    private Date dateNaissance;
    private String NAM;

    public Patient(String NAM, String nomComplet) {
        this.NAM = NAM;
        this.nomComplet = nomComplet;
    }

    public void setDateNiassance(Date date) {
        dateNaissance = date;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Patient == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        
        Patient patient = (Patient) obj;        
        return new EqualsBuilder().append(nomComplet, patient.nomComplet)
                .append(dateNaissance, patient.dateNaissance)
                .append(NAM, patient.NAM)
                .isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder().append(nomComplet)
                .append(dateNaissance)
                .append(NAM)
                .toHashCode();
    }

}
