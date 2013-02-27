package com.elapsetech.adt.rest.dto.responses;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "patient")
public class PatientDto {

    public int id;
    public String prenom;
    public String nom;
    public String name;
    public Date ddn;

    public List<VenueDto> venues;

}
