package com.elapsetech.adt.domain;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;

public class PatientTest {

    private static final String NAM = null;
    private static final String PRENOM = null;
    private static final String NOM = null;

    private Patient patient;
    private Venue venue;

    @Before
    public void setUp() {
        patient = new Patient(NAM, PRENOM, NOM);
        venue = mock(Venue.class);
    }

    @Test
    public void ajouterVenueGenereNumerosVenuesSequentiels() {
        int premierNumero = patient.ajouterVenue(venue);
        int deuxiemeNumero = patient.ajouterVenue(venue);

        assertEquals(deuxiemeNumero, premierNumero + 1);
    }

    @Test
    public void ajouterEtObtenirVenueAvecMemeNumero() {
        int numero = patient.ajouterVenue(venue);

        assertSame(venue, patient.obtenirVenue(numero));

    }

    @Test(expected = VenueInexistanteException.class)
    public void obtenirVenueInexistanteLanceException() {
        int numeroInexistant = 1;
        patient.obtenirVenue(numeroInexistant);
    }
}
