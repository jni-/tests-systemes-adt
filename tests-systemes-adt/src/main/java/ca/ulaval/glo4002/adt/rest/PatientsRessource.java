package ca.ulaval.glo4002.adt.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ca.ulaval.glo4002.adt.domain.EntiteInvalidException;
import ca.ulaval.glo4002.adt.domain.Patient;
import ca.ulaval.glo4002.adt.domain.Referentiel;
import ca.ulaval.glo4002.adt.rest.convertisseurs.ConvertisseurDemandeCreationPatient;
import ca.ulaval.glo4002.adt.rest.convertisseurs.ConvertisseurPatient;
import ca.ulaval.glo4002.adt.rest.dto.requests.DemandeCreationPatient;
import ca.ulaval.glo4002.adt.rest.dto.responses.CreationEntite;
import ca.ulaval.glo4002.adt.rest.dto.responses.Erreur;
import ca.ulaval.glo4002.adt.services.LocalisateurReferentiel;

@Path("/patients")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class PatientsRessource {

    private final Referentiel<Patient> referentiel;
    private final ConvertisseurDemandeCreationPatient convertisseurDemande;
    private final ConvertisseurPatient convertisseurPatient;

    public PatientsRessource() {
        referentiel = LocalisateurReferentiel.obtenir(Patient.class);
        convertisseurDemande = new ConvertisseurDemandeCreationPatient();
        convertisseurPatient = new ConvertisseurPatient();
    }

    @POST
    @Path("/")
    public Response ajouterPatient(DemandeCreationPatient demande) {
        try {
            CreationEntite creation = new CreationEntite(referentiel.ajouter(convertisseurDemande.convertir(demande)));
            return Response.ok(creation).build();
        } catch (EntiteInvalidException e) {
            return Response.status(Status.BAD_REQUEST).entity(new Erreur(e.getMessage())).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response obtenirPatient(@PathParam(value = "id") int id) {
        Patient patient = referentiel.obtenir(id);
        if (patient != null) {
            return Response.ok(convertisseurPatient.convertir(patient)).build();
        } else {
            return Response.status(Status.NOT_FOUND).entity(new Erreur("Patient avec l'id '" + id + "' introuvable")).build();
        }
    }

    PatientsRessource(Referentiel<Patient> referentiel, ConvertisseurDemandeCreationPatient convertisseurDemande,
            ConvertisseurPatient convertisseurPatient) {
        this.referentiel = referentiel;
        this.convertisseurDemande = convertisseurDemande;
        this.convertisseurPatient = convertisseurPatient;

    }
}
