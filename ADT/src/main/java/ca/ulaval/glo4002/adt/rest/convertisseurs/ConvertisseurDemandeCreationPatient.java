package ca.ulaval.glo4002.adt.rest.convertisseurs;

import ca.ulaval.glo4002.adt.domain.Patient;
import ca.ulaval.glo4002.adt.rest.dto.requests.DemandeCreationPatient;

public class ConvertisseurDemandeCreationPatient implements Convertisseur<DemandeCreationPatient, Patient> {

    @Override
    public DemandeCreationPatient convertir(Patient entite) {
        throw new ConversionNonSupporteeException();
    }

    @Override
    public Patient convertir(DemandeCreationPatient entite) {
        Patient patient = new Patient(entite.NAM, entite.prenom, entite.nom);
        patient.setDateNaissance(entite.DDN);
        return patient;
    }

}
