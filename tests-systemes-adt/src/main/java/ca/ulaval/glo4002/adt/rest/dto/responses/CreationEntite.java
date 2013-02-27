package ca.ulaval.glo4002.adt.rest.dto.responses;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreationEntite {

    public int id;

    public CreationEntite() {
        // Pour JAXB
    }

    public CreationEntite(int id) {
        this.id = id;
    }

}
