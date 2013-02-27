package ca.ulaval.glo4002.adt.rest.dto.responses;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "patient")
public class PatientDto {

    public String prenom;
    public String nom;
    public String nam;
    public Date ddn;

    public List<VenueDto> venues;

}
