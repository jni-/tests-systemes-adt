package ca.ulaval.glo4002.adt.rest.dto.requests;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DemandeCreationPatient {

    public String prenom;
    public String nom;
    public String NAM;
    public Date DDN;

}
