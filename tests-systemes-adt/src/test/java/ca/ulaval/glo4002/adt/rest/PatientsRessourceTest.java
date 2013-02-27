package ca.ulaval.glo4002.adt.rest;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.adt.domain.EntiteInvalidException;
import ca.ulaval.glo4002.adt.domain.Patient;
import ca.ulaval.glo4002.adt.domain.Referentiel;
import ca.ulaval.glo4002.adt.rest.PatientsRessource;
import ca.ulaval.glo4002.adt.rest.convertisseurs.ConvertisseurDemandeCreationPatient;
import ca.ulaval.glo4002.adt.rest.convertisseurs.ConvertisseurPatient;
import ca.ulaval.glo4002.adt.rest.dto.requests.DemandeCreationPatient;
import ca.ulaval.glo4002.adt.rest.dto.responses.CreationEntite;
import ca.ulaval.glo4002.adt.rest.dto.responses.Erreur;
import ca.ulaval.glo4002.adt.rest.dto.responses.PatientDto;

@RunWith(MockitoJUnitRunner.class)
public class PatientsRessourceTest {

    @Mock
    private Referentiel<Patient> referentiel;

    @Mock
    private ConvertisseurDemandeCreationPatient convertisseurDemande;

    @Mock
    private ConvertisseurPatient convertisseurPatient;

    @Mock
    private Patient patient;

    private DemandeCreationPatient demande = new DemandeCreationPatient();
    private PatientDto patientDto = new PatientDto();
    private PatientsRessource ressource;

    @Before
    public void setUp() {
        willReturn(patient).given(convertisseurDemande).convertir(demande);
        willReturn(patientDto).given(convertisseurPatient).convertir(patient);
        ressource = new PatientsRessource(referentiel, convertisseurDemande, convertisseurPatient);
    }

    @Test
    public void ajouterUnPatientRetourneLidSelonReferentiel() {
        int id = 10;
        willReturn(id).given(referentiel).ajouter(patient);

        Response reponse = ressource.ajouterPatient(demande);
        CreationEntite creation = (CreationEntite) reponse.getEntity();

        assertEquals(Status.OK.getStatusCode(), reponse.getStatus());
        assertEquals(id, creation.id);
    }

    @Test
    public void uneExceptionEstLanceeALajoutAlorsCodeHttpBadRequest() {
        willThrow(new EntiteInvalidException("")).given(referentiel).ajouter(patient);

        Response reponse = ressource.ajouterPatient(demande);

        assertEquals(Status.BAD_REQUEST.getStatusCode(), reponse.getStatus());
    }

    @Test
    public void uneExceptionEstLanceeALajoutAlorsReponseContientMessageErreurDeException() {
        String message = "un message d'erreur";
        willThrow(new EntiteInvalidException(message)).given(referentiel).ajouter(patient);

        Response reponse = ressource.ajouterPatient(demande);
        Erreur erreur = (Erreur) reponse.getEntity();

        assertEquals(message, erreur.raison);
    }

    @Test
    public void obtenirPatientAvecIdInexistantRetourneErreur404() {
        int id = 10;
        willReturn(null).given(referentiel).obtenir(id);

        Response reponse = ressource.obtenirPatient(id);

        assertEquals(Status.NOT_FOUND.getStatusCode(), reponse.getStatus());
    }

    @Test
    public void obtenirPatientAvecIdInexistantRetourneErreurAvecRaison() {
        int id = 10;
        willReturn(null).given(referentiel).obtenir(id);

        Response reponse = ressource.obtenirPatient(id);
        Erreur erreur = (Erreur) reponse.getEntity();

        assertFalse(erreur.raison.isEmpty());
    }

    @Test
    public void obtenirPatientAvecIdExistantRetourneOkAvecPatientDtoVoulu() {
        int id = 10;
        willReturn(patient).given(referentiel).obtenir(id);

        Response reponse = ressource.obtenirPatient(id);
        PatientDto dtoRecu = (PatientDto) reponse.getEntity();

        assertEquals(Status.OK.getStatusCode(), reponse.getStatus());
        assertSame(patientDto, dtoRecu);
    }

}
