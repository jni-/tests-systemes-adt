package com.elapsetech.adt.rest.dto.requests;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DemandeCreationVenue {

    public Date date;
    public String raison;
    public int departementId;
    public String departementCode;

}
