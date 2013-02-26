package com.elapsetech.adt.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.elapsetech.adt.domain.EntiteInvalidException;
import com.elapsetech.adt.domain.Patient;
import com.elapsetech.adt.domain.Referentiel;
import com.elapsetech.adt.rest.convertisseurs.ConvertisseurDemandeCreationPatient;
import com.elapsetech.adt.rest.dto.requests.DemandeCreationPatient;
import com.elapsetech.adt.rest.dto.responses.CreationEntite;
import com.elapsetech.adt.rest.dto.responses.Erreur;

@RunWith(MockitoJUnitRunner.class)
public class PatientsRessourceTest {

    @Mock
    private Referentiel<Patient> referentiel;

    @Mock
    private ConvertisseurDemandeCreationPatient convertisseur;

    @Mock
    private Patient patient;

    private DemandeCreationPatient demande;
    private PatientsRessource ressource;

    @Before
    public void setUp() {
        willReturn(patient).given(convertisseur).convertir(demande);
        ressource = new PatientsRessource(referentiel, convertisseur);
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
}
