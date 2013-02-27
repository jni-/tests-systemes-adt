package ca.ulaval.glo4002.adt.rest.convertisseurs;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.adt.domain.Departement;
import ca.ulaval.glo4002.adt.domain.Patient;
import ca.ulaval.glo4002.adt.domain.Venue;
import ca.ulaval.glo4002.adt.rest.convertisseurs.ConvertisseurPatient;
import ca.ulaval.glo4002.adt.rest.dto.responses.DepartementDto;
import ca.ulaval.glo4002.adt.rest.dto.responses.PatientDto;
import ca.ulaval.glo4002.adt.rest.dto.responses.TransfersDto;
import ca.ulaval.glo4002.adt.rest.dto.responses.VenueDto;

public class ConvertisseurPatientTest {

    private static final int ID = 10;
    private static final String NAM = "un NAM";
    private static final String PRENOM = "un prenom";
    private static final String NOM = "un nom";
    private static final String CODE_DEPARTEMENT = "un code";
    private static final String CODE_DEPARTEMENT_TRANSFERE = "un code transfers";
    private static final String NOM_DEPARTEMENT = "un nom departement";
    private static final Date DDN = Calendar.getInstance().getTime();
    private static final Date DATE_VENUE = Calendar.getInstance().getTime();

    private static final int NOMBRE_VENUES = 1;
    private static final int NOMBRE_TRANSFERS = 1;

    private Patient patient;
    private PatientDto patientDto;
    private ConvertisseurPatient convertisseur;

    @Before
    public void setUp() {
        convertisseur = new ConvertisseurPatient();
        creerPatient();
        creerPatientDtoEquivalent();
    }

    @Test
    public void peutConvertirDePatientAuDto() {
        PatientDto dtoCree = convertisseur.convertir(patient);

        assertEquals(patientDto.ddn, dtoCree.ddn);
        assertEquals(patientDto.name, dtoCree.name);
        assertEquals(patientDto.prenom, dtoCree.prenom);
        assertEquals(patientDto.nom, dtoCree.nom);
    }

    @Test
    public void peutConvertirDePatientAuDtoEtLeDtoContientLaVenue() {
        PatientDto dtoCree = convertisseur.convertir(patient);

        assertEquals(NOMBRE_VENUES, dtoCree.venues.size());
    }

    @Test
    public void peutConvertirDePatientAuDtoEtLeDtoContientLaVenueBienRemplie() {
        PatientDto dtoCree = convertisseur.convertir(patient);
        VenueDto venueDto = dtoCree.venues.get(0);

        assertEquals(DATE_VENUE, venueDto.date);
    }

    @Test
    public void peutConvertirDePatientAuDtoEtLeDtoContientLaVenueQuiContientLeDepartement() {
        PatientDto dtoCree = convertisseur.convertir(patient);
        VenueDto venueDto = dtoCree.venues.get(0);
        DepartementDto departementDto = venueDto.departement;

        assertEquals(CODE_DEPARTEMENT, departementDto.code);
        assertEquals(NOM_DEPARTEMENT, departementDto.nom);
    }

    @Test
    public void peutConvertirDePatientAuDtoEtleDtoContientLesTransfersDeLaVenue() {
        PatientDto dtoCree = convertisseur.convertir(patient);
        VenueDto venueDto = dtoCree.venues.get(0);

        assertEquals(NOMBRE_TRANSFERS, venueDto.transfers.size());
    }

    @Test
    public void peutConvertirDePatientAuDtoEtleDtoContientLesTransfersBienRemplis() {
        PatientDto dtoCree = convertisseur.convertir(patient);
        VenueDto venueDto = dtoCree.venues.get(0);
        TransfersDto transfersDto = venueDto.transfers.get(0);

        assertEquals(CODE_DEPARTEMENT_TRANSFERE, transfersDto.departement.code);
    }

    @Test
    public void peutConvertirDePatientDtoAuPatient() {
        Patient patientCree = convertisseur.convertir(patientDto);

        assertEquals(patient.getDateNaissance(), patientCree.getDateNaissance());
        assertEquals(patient.getNom(), patientCree.getNom());
        assertEquals(patient.getPrenom(), patientCree.getPrenom());
        assertEquals(patient.getNam(), patientCree.getNam());
    }

    private void creerPatient() {
        Departement departement = new Departement(CODE_DEPARTEMENT);
        departement.setNom(NOM_DEPARTEMENT);

        Departement departementTransfere = new Departement(CODE_DEPARTEMENT_TRANSFERE);

        Venue venue = new Venue(DATE_VENUE, departement);
        venue.transfererMaintenantVers(departementTransfere);

        patient = new Patient(NAM, PRENOM, NOM);
        patient.setDateNaissance(DDN);
        patient.ajouterVenue(venue);
    }

    private void creerPatientDtoEquivalent() {
        patientDto = new PatientDto();
        patientDto.nom = NOM;
        patientDto.prenom = PRENOM;
        patientDto.ddn = DDN;
        patientDto.name = NAM;
        patientDto.id = ID;
    }
}
