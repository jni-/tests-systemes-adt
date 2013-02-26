package com.elapsetech.adt.domain;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;


public class PatientTest {

    private final static String NAM = "un NAM";
    private final static String NOM = "un nom";
    private final static Date DATE_NAISSANCE = Calendar.getInstance().getTime();
    
    private Patient patient;
    private Patient patientIdentique;
    private Patient patientNAMDifferent;
    private Patient patientNomDifferent;
    private Patient patientDateNaissanceDifferente;
    
    @Before
    public void setUp() {
        patient = creerPatient(NAM, NOM, DATE_NAISSANCE);
        patientIdentique = creerPatient(NAM, NOM, DATE_NAISSANCE);
        patientNAMDifferent = creerPatient(NAM + "different", NOM, DATE_NAISSANCE);
        patientNomDifferent = creerPatient(NAM, NOM + "different", DATE_NAISSANCE);
        patientDateNaissanceDifferente = creerPatient(NAM, NOM, Calendar.getInstance().getTime());
    }
    
    @Test
    public void patientNestPasEqualANull() {
        assertFalse(patient.equals(null));
    }
    
    @Test
    public void patientEstEqualALuiMeme() {
        assertTrue(patient.equals(patient));
    }
    
    @Test
    public void patientEstEqualAUneAutreInstanceAvecLesMemesValeurs() {
        assertTrue(patient.equals(patientIdentique));
    }
    
    @Test
    public void patientNestPasEqualSiNAMDifferent() {
        assertFalse(patient.equals(patientNAMDifferent));
    }
    
    @Test
    public void patientNestPasEqualSiNomDifferent() {
        assertFalse(patient.equals(patientNomDifferent));
    }
    
    @Test
    public void patientNestPasEqualSiDateNaissanceDifferente() {
        assertFalse(patient.equals(patientDateNaissanceDifferente));
    }
    
    @Test
    public void hashCodePareilPourDeuxPatientsIdentiques() {
        assertEquals(patient.hashCode(), patientIdentique.hashCode());
    }
    
    @Test
    public void hashCodeDifferentPourDeuxPatientsDifferents() {
        // On utilise pas assertNotEquals, car Infinitest plante dans ce cas.
        // assertNotEquals(patient.hashCode(), patientNAMDifferent.hashCode());
        int hashCode1 = patient.hashCode();
        int hashCode2 = patientNAMDifferent.hashCode();
        assertTrue("Les 2 hashcodes sont differents car les patients sont differents", hashCode1 != hashCode2);
    }

    private Patient creerPatient(String Nam, String nom, Date dateNaissance) {
        Patient patient = new Patient(Nam, nom);
        patient.setDateNiassance(dateNaissance);
        return patient;
    }
    
}
