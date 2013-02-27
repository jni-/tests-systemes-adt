package ca.ulaval.glo4002.adt.rest.convertisseurs;

import java.util.LinkedList;
import java.util.List;

import ca.ulaval.glo4002.adt.domain.Departement;
import ca.ulaval.glo4002.adt.domain.Patient;
import ca.ulaval.glo4002.adt.domain.Transfers;
import ca.ulaval.glo4002.adt.domain.Venue;
import ca.ulaval.glo4002.adt.rest.dto.responses.DepartementDto;
import ca.ulaval.glo4002.adt.rest.dto.responses.PatientDto;
import ca.ulaval.glo4002.adt.rest.dto.responses.TransfersDto;
import ca.ulaval.glo4002.adt.rest.dto.responses.VenueDto;

public class ConvertisseurPatient implements Convertisseur<PatientDto, Patient> {

    @Override
    public PatientDto convertir(Patient entite) {
        PatientDto dto = new PatientDto();
        dto.name = entite.getNam();
        dto.ddn = entite.getDateNaissance();
        dto.nom = entite.getNom();
        dto.prenom = entite.getPrenom();
        dto.venues = creerVenues(entite);
        return dto;
    }

    private List<VenueDto> creerVenues(Patient entite) {
        List<VenueDto> venuesDto = new LinkedList<>();

        for (Venue venue : entite.getVenues()) {
            venuesDto.add(creerVenueDto(venue));
        }

        return venuesDto;
    }

    private VenueDto creerVenueDto(Venue venue) {
        VenueDto dto = new VenueDto();
        dto.date = venue.getDate();
        dto.departement = creerDepartementDto(venue.getDepartement());

        if (venue.getTransfers() != null && !venue.getTransfers().isEmpty()) {
            dto.transfers = creerListeTransfersDto(venue.getTransfers());
        }

        return dto;
    }

    private DepartementDto creerDepartementDto(Departement departement) {
        DepartementDto dto = new DepartementDto();
        dto.code = departement.getCode();
        dto.nom = departement.getNom();
        return dto;
    }

    private List<TransfersDto> creerListeTransfersDto(List<Transfers> listeTransfers) {
        List<TransfersDto> listeTransfersDto = new LinkedList<>();

        for (Transfers transfers : listeTransfers) {
            listeTransfersDto.add(creerTransfersDto(transfers));
        }

        return listeTransfersDto;
    }

    private TransfersDto creerTransfersDto(Transfers transfers) {
        TransfersDto transfersDto = new TransfersDto();
        transfersDto.date = transfers.getDate();
        transfersDto.departement = creerDepartementDto(transfers.getDepartement());
        return transfersDto;
    }

    @Override
    public Patient convertir(PatientDto entite) {
        Patient patient = new Patient(entite.name, entite.prenom, entite.nom);
        patient.setDateNaissance(entite.ddn);
        return patient;
    }

}
