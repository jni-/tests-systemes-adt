package com.elapsetech.adt.rest.convertisseurs;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.elapsetech.adt.domain.Entite;
import com.elapsetech.adt.domain.Patient;
import com.elapsetech.adt.rest.dto.requests.DemandeCreationPatient;

public class ConvertisseurDemandeCreationPatientTest {

    private final static String NAM = "un NAM";
    private final static String PRENOM = "un prenom";
    private final static String NOM = "un nom";
    private final static Date DATE_NAISSANCE = Calendar.getInstance().getTime();

    private ConvertisseurDemandeCreationPatient convertisseur;
    private DemandeCreationPatient demande;
    private Patient patient;

    @Before
    public void setUp() {
        convertisseur = new ConvertisseurDemandeCreationPatient();
        creerDemandeDeBase();
        creerPatientPourDemandeDeBase();
    }

    @Test
    public void lesChampsSontBienRemplis() {
        Patient patientConverti = convertisseur.convertir(demande);
        assertEquals(patient.getDateNaissance(), patientConverti.getDateNaissance());
        assertEquals(patient.getNam(), patientConverti.getNam());
        assertEquals(patient.getPrenom(), patientConverti.getPrenom());
        assertEquals(patient.getNom(), patientConverti.getNom());
    }

    @Test(expected = ConversionNonSupporteeException.class)
    public void impossibleDeConvertirUnPatientEnDemande() {
        convertisseur.convertir(patient);
    }

    private DemandeCreationPatient creerDemandeDeBase() {
        demande = new DemandeCreationPatient();
        demande.NAM = NAM;
        demande.prenom = PRENOM;
        demande.nom = NOM;
        demande.DDN = DATE_NAISSANCE;

        return demande;
    }

    private Entite creerPatientPourDemandeDeBase() {
        patient = new Patient(NAM, PRENOM, NOM);
        patient.setDateNaissance(DATE_NAISSANCE);
        return patient;
    }
}
