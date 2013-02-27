package ca.ulaval.glo4002.adt.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ca.ulaval.glo4002.adt.domain.Departement;
import ca.ulaval.glo4002.adt.domain.Patient;
import ca.ulaval.glo4002.adt.domain.Referentiel;
import ca.ulaval.glo4002.adt.domain.Venue;
import ca.ulaval.glo4002.adt.rest.dto.requests.DemandeTransfers;
import ca.ulaval.glo4002.adt.rest.dto.responses.Erreur;
import ca.ulaval.glo4002.adt.services.DepartementIntrouvableException;
import ca.ulaval.glo4002.adt.services.LocalisateurReferentiel;
import ca.ulaval.glo4002.adt.services.RechercheDepartement;

@Path("/patients/{idPatient}/venues/{idVenue}/transfers")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class TransfersRessource {

    private Referentiel<Patient> referenciel;
    private RechercheDepartement recherche;

    public TransfersRessource() {
        referenciel = LocalisateurReferentiel.obtenir(Patient.class);
        recherche = new RechercheDepartement();
    }

    @PUT
    @Path("/")
    public Response transferPatient(DemandeTransfers demande, @PathParam("idPatient") int idPatient, @PathParam("idVenue") int idVenue) {
        try {
            Venue venue = ObtenirVenue(idPatient, idVenue);
            Departement departement = obtenirDepartement(demande);

            venue.transfererMaintenantVers(departement);

            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Status.NOT_FOUND).entity(new Erreur(e.getMessage())).build();
        }
    }

    private Venue ObtenirVenue(int idPatient, int idVenue) {
        Patient patient = obtenirPatient(idPatient);
        if (patient == null) {
            throw new RuntimeException("Le patient avec l'id " + idPatient + " n'existe pas");
        }
        Venue venue = obtenirVenue(patient, idVenue);
        return venue;
    }

    private Patient obtenirPatient(int idPatient) {
        return referenciel.obtenir(idPatient);
    }

    private Venue obtenirVenue(Patient patient, int idVenue) {
        return patient.obtenirVenue(idVenue);
    }

    private Departement obtenirDepartement(DemandeTransfers demande) throws DepartementIntrouvableException {
        return recherche.rechercher(demande.departementId, demande.departementCode);
    }

    TransfersRessource(Referentiel<Patient> referenciel, RechercheDepartement recherche) {
        this.referenciel = referenciel;
        this.recherche = recherche;
    }

}
