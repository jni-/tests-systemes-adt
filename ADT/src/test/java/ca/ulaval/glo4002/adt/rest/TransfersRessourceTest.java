package ca.ulaval.glo4002.adt.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.*;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.adt.domain.Departement;
import ca.ulaval.glo4002.adt.domain.Patient;
import ca.ulaval.glo4002.adt.domain.Referentiel;
import ca.ulaval.glo4002.adt.domain.Venue;
import ca.ulaval.glo4002.adt.domain.VenueInexistanteException;
import ca.ulaval.glo4002.adt.rest.TransfersRessource;
import ca.ulaval.glo4002.adt.rest.dto.requests.DemandeTransfers;
import ca.ulaval.glo4002.adt.services.DepartementIntrouvableException;
import ca.ulaval.glo4002.adt.services.RechercheDepartement;

import com.sun.jersey.api.client.ClientResponse.Status;

@RunWith(MockitoJUnitRunner.class)
public class TransfersRessourceTest {

    private static final int ID_PATIENT = 10;
    private static final int NUMERO_VENUE = 44;
    private static final int ID_DEPARTEMENT = 32;
    private static final String CODE_DEPARTEMENT = "code";

    private static final int ID_PATIENT_INEXISTANT = 11;
    private static final int NUMERO_VENUE_INEXISTANTE = 45;
    private static final int ID_DEPARTEMENT_INEXISTANT = 35;
    private static final String CODE_DEPARTEMENT_INEXISTANT = "mauvais code";

    @Mock
    private Referentiel<Patient> referenciel;

    @Mock
    private RechercheDepartement recherche;

    @Mock
    private Patient patient;

    @Mock
    private Venue venue;

    @Mock
    private Departement departement;

    private DemandeTransfers demande;
    private DemandeTransfers demandeDepartementIntrouvable;
    private TransfersRessource ressource;

    @Before
    public void setUp() throws DepartementIntrouvableException {
        creerDemande();
        configurerMocks();
        ressource = new TransfersRessource(referenciel, recherche);
    }

    @Test
    public void retourneNotFoundSiPatientIntrouvable() {
        assertNotFound(ressource.transferPatient(demande, ID_PATIENT_INEXISTANT, NUMERO_VENUE));
    }

    @Test
    public void retourneNotFoundSiVenueIntrouvable() {
        assertNotFound(ressource.transferPatient(demande, ID_PATIENT, NUMERO_VENUE_INEXISTANTE));
    }

    @Test
    public void retourneNotFoundSiDepartementIntrouvable() {
        assertNotFound(ressource.transferPatient(demandeDepartementIntrouvable, ID_PATIENT, NUMERO_VENUE));
    }

    @Test
    public void retourneOkSiToutEstExistant() {
        assertEquals(Status.OK.getStatusCode(), ressource.transferPatient(demande, ID_PATIENT, NUMERO_VENUE).getStatus());
    }

    @Test
    public void transfereAEteDemanderALaVenueSiTouTEstExistant() {
        ressource.transferPatient(demande, ID_PATIENT, NUMERO_VENUE);

        verify(venue).transfererMaintenantVers(departement);
    }

    private void assertNotFound(Response reponse) {
        assertEquals(Status.NOT_FOUND.getStatusCode(), reponse.getStatus());
    }

    private void creerDemande() {
        demande = new DemandeTransfers();
        demande.departementId = ID_DEPARTEMENT;
        demande.departementCode = CODE_DEPARTEMENT;

        demandeDepartementIntrouvable = new DemandeTransfers();
        demandeDepartementIntrouvable.departementId = ID_DEPARTEMENT_INEXISTANT;
        demandeDepartementIntrouvable.departementCode = CODE_DEPARTEMENT_INEXISTANT;
    }

    private void configurerMocks() throws DepartementIntrouvableException {
        willReturn(patient).given(referenciel).obtenir(ID_PATIENT);
        willReturn(venue).given(patient).obtenirVenue(NUMERO_VENUE);
        willReturn(departement).given(recherche).rechercher(ID_DEPARTEMENT, CODE_DEPARTEMENT);

        willReturn(null).given(referenciel).obtenir(ID_PATIENT_INEXISTANT);
        willThrow(VenueInexistanteException.class).given(patient).obtenirVenue(NUMERO_VENUE_INEXISTANTE);
        willThrow(DepartementIntrouvableException.class).given(recherche)
                .rechercher(ID_DEPARTEMENT_INEXISTANT, CODE_DEPARTEMENT_INEXISTANT);
    }
}
