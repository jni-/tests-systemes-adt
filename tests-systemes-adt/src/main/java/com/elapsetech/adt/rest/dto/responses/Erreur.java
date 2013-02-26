package com.elapsetech.adt.rest.dto.responses;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Erreur {

    public String raison;

    public Erreur() {
        // Pour JAXB
    }

    public Erreur(String raison) {
        this.raison = raison;
    }
}
