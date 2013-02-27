package com.elapsetech.adt.rest.convertisseurs;

import java.util.LinkedList;
import java.util.List;

import com.elapsetech.adt.domain.Departement;
import com.elapsetech.adt.domain.Patient;
import com.elapsetech.adt.domain.Venue;
import com.elapsetech.adt.rest.dto.responses.DepartementDto;
import com.elapsetech.adt.rest.dto.responses.PatientDto;
import com.elapsetech.adt.rest.dto.responses.VenueDto;

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
        return dto;
    }

    private DepartementDto creerDepartementDto(Departement departement) {
        DepartementDto dto = new DepartementDto();
        dto.code = departement.getCode();
        dto.nom = departement.getNom();
        return dto;
    }

    @Override
    public Patient convertir(PatientDto entite) {
        Patient patient = new Patient(entite.name, entite.prenom, entite.nom);
        patient.setDateNaissance(entite.ddn);
        return patient;
    }

}
