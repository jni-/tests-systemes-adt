package com.elapsetech.adt.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.elapsetech.adt.domain.Patient;
import com.elapsetech.adt.domain.Referentiel;
import com.elapsetech.adt.domain.Venue;
import com.elapsetech.adt.rest.convertisseurs.ConversionImpossibleException;
import com.elapsetech.adt.rest.convertisseurs.Convertisseur;
import com.elapsetech.adt.rest.dto.requests.DemandeCreationVenue;
import com.elapsetech.adt.rest.dto.responses.CreationEntite;
import com.elapsetech.adt.rest.dto.responses.Erreur;

@RunWith(MockitoJUnitRunner.class)
public class VenuesRessourceTest {

    private static final int ID_PATIENT = 13;

    @Mock
    private Referentiel<Patient> referentielPatient;

    @Mock
    private Convertisseur<DemandeCreationVenue, Venue> convertisseurDemande;

    @Mock
    private Venue venue;

    @Mock
    private Patient patient;

    private DemandeCreationVenue demande;
    private VenuesRessource ressource;

    @Before
    public void setUp() {
        ressource = new VenuesRessource(referentielPatient, convertisseurDemande);
        willReturn(venue).given(convertisseurDemande).convertir(demande);
        willReturn(patient).given(referentielPatient).obtenir(ID_PATIENT);
    }

    @Test
    public void ajouterConvertieRessourceAvecConvertisseur() {
        ressource.ajouterVenue(demande, ID_PATIENT);

        verify(convertisseurDemande).convertir(demande);
    }

    @Test
    public void ajouterAjouteDansReferentiel() {
        ressource.ajouterVenue(demande, ID_PATIENT);

        verify(patient).ajouterVenue(venue);
    }

    @Test
    public void ajouterRetourneBadRequestAvecErrurSiConversionLanceException() {
        DemandeCreationVenue demandeInvalide = new DemandeCreationVenue();
        String message = "message";
        willThrow(new ConversionImpossibleException(message)).given(convertisseurDemande).convertir(demandeInvalide);

        Response reponse = ressource.ajouterVenue(demandeInvalide, ID_PATIENT);
        Erreur erreur = (Erreur) reponse.getEntity();

        assertEquals(Status.NOT_FOUND.getStatusCode(), reponse.getStatus());
        assertEquals(erreur.raison, message);
    }

    @Test
    public void ajouterRetourneOkAvecNouvelIdSiToutVaBien() {
        int numeroVenue = 20;
        willReturn(numeroVenue).given(patient).ajouterVenue(venue);

        Response reponse = ressource.ajouterVenue(demande, ID_PATIENT);
        CreationEntite creation = (CreationEntite) reponse.getEntity();

        assertEquals(Status.OK.getStatusCode(), reponse.getStatus());
        assertEquals(numeroVenue, creation.id);
    }

    @Test
    public void ajouterRetourneNotFoundSiPatientExistePas() {
        int idPatientInexistant = ID_PATIENT + 2;
        willReturn(null).given(referentielPatient).obtenir(idPatientInexistant);

        Response reponse = ressource.ajouterVenue(demande, idPatientInexistant);

        assertEquals(Status.NOT_FOUND.getStatusCode(), reponse.getStatus());
    }
}
