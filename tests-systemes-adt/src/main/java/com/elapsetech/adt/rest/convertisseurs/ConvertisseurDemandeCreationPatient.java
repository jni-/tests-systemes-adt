package com.elapsetech.adt.rest.convertisseurs;

import com.elapsetech.adt.domain.Patient;
import com.elapsetech.adt.rest.dto.requests.DemandeCreationPatient;

public class ConvertisseurDemandeCreationPatient implements Convertisseur<DemandeCreationPatient, Patient>{

    @Override
    public DemandeCreationPatient convertir(Patient entite) {
        throw new ConversionNonSupporteeException();
    }

    @Override
    public Patient convertir(DemandeCreationPatient entite) {
        Patient patient = new Patient(entite.NAM, entite.prenom + entite.nom);
        patient.setDateNiassance(entite.DDN);
        return patient;
    }

}
