package ca.ulaval.glo4002.adt.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ca.ulaval.glo4002.adt.domain.Patient;
import ca.ulaval.glo4002.adt.domain.Referentiel;
import ca.ulaval.glo4002.adt.domain.Venue;
import ca.ulaval.glo4002.adt.rest.convertisseurs.ConversionImpossibleException;
import ca.ulaval.glo4002.adt.rest.convertisseurs.Convertisseur;
import ca.ulaval.glo4002.adt.rest.convertisseurs.ConvertisseurDemandeCreationVenue;
import ca.ulaval.glo4002.adt.rest.dto.requests.DemandeCreationVenue;
import ca.ulaval.glo4002.adt.rest.dto.responses.CreationEntite;
import ca.ulaval.glo4002.adt.rest.dto.responses.Erreur;
import ca.ulaval.glo4002.adt.services.LocalisateurReferentiel;

@Path("/patients/{id}/venues")
public class VenuesRessource {

    private Convertisseur<DemandeCreationVenue, Venue> convertisseurDemande;
    private Referentiel<Patient> referentielPatient;

    public VenuesRessource() {
        convertisseurDemande = new ConvertisseurDemandeCreationVenue();
        referentielPatient = LocalisateurReferentiel.obtenir(Patient.class);
    }

    @POST
    @Path("/")
    public Response ajouterVenue(DemandeCreationVenue demande, @PathParam("id") int patientId) {
        try {
            Patient patient = referentielPatient.obtenir(patientId);

            if (patient == null) {
                return Response.status(Status.NOT_FOUND).build();
            }

            int numeroVenue = patient.ajouterVenue(convertisseurDemande.convertir(demande));

            return Response.ok(new CreationEntite(numeroVenue)).build();
        } catch (ConversionImpossibleException e) {
            return Response.status(Status.NOT_FOUND).entity(new Erreur(e.getMessage())).build();
        }
    }

    VenuesRessource(Referentiel<Patient> referentielPatient, Convertisseur<DemandeCreationVenue, Venue> convertisseurDemande) {
        this.referentielPatient = referentielPatient;
        this.convertisseurDemande = convertisseurDemande;
    }
}
