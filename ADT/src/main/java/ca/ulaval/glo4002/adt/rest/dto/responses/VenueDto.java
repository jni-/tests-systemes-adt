package ca.ulaval.glo4002.adt.rest.dto.responses;

import java.util.Date;
import java.util.List;

public class VenueDto {

    public Date date;

    public String raison;

    public DepartementDto departement;

    public List<TransfersDto> transfers;

}
