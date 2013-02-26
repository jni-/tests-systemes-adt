package com.elapsetech.adt.rest.convertisseurs;

import com.elapsetech.adt.domain.Patient;
import com.elapsetech.adt.rest.dto.responses.PatientDto;

public class ConvertisseurPatient implements Convertisseur<PatientDto, Patient> {

    @Override
    public PatientDto convertir(Patient entite) {
        PatientDto dto = new PatientDto();
        dto.name = entite.getNam();
        dto.ddn = entite.getDateNaissance();
        dto.nom = entite.getNom();
        dto.prenom = entite.getPrenom();
        return dto;
    }

    @Override
    public Patient convertir(PatientDto entite) {
        Patient patient = new Patient(entite.name, entite.prenom, entite.nom);
        patient.setDateNaissance(entite.ddn);
        return patient;
    }

}
