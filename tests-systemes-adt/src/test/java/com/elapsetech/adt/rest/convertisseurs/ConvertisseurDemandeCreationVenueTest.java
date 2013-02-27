package com.elapsetech.adt.rest.convertisseurs;

import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.elapsetech.adt.domain.Departement;
import com.elapsetech.adt.domain.Venue;
import com.elapsetech.adt.rest.dto.requests.DemandeCreationVenue;
import com.elapsetech.adt.services.DepartementIntrouvableException;
import com.elapsetech.adt.services.RechercheDepartement;

@RunWith(MockitoJUnitRunner.class)
public class ConvertisseurDemandeCreationVenueTest {

    private static final int ID_DEPARTEMENT = 10;
    private static final Date DATE_VENUE = Calendar.getInstance().getTime();
    private static final String CODE_DEPARTEMENT = "code";
    private static final String RAISON_VENUE = "raison";

    @Mock
    private Departement departement;

    @Mock
    private RechercheDepartement recherche;

    private ConvertisseurDemandeCreationVenue convertisseur;
    private DemandeCreationVenue demande;

    @Before
    public void setUp() throws DepartementIntrouvableException {
        creerDemandeBase();
        willReturn(departement).given(recherche).rechercher(ID_DEPARTEMENT, CODE_DEPARTEMENT);

        convertisseur = new ConvertisseurDemandeCreationVenue(recherche);
    }

    @Test
    public void peutMapperLaDate() {
        assertEquals(DATE_VENUE, convertisseur.convertir(demande).getDate());
    }

    @Test
    public void peutMapperLaRaison() {
        assertEquals(RAISON_VENUE, convertisseur.convertir(demande).getRaisonDeVenue());
    }

    @Test
    public void convertisseurUtiliseLaRechercheDeDepartement() {
        assertSame(departement, convertisseur.convertir(demande).getDepartement());
    }

    @Test(expected = ConversionImpossibleException.class)
    public void lanceExceptionSiDepartementIntrouvable() throws DepartementIntrouvableException {
        willThrow(DepartementIntrouvableException.class).given(recherche).rechercher(ID_DEPARTEMENT, CODE_DEPARTEMENT);

        convertisseur.convertir(demande);
    }

    @Test(expected = ConversionNonSupporteeException.class)
    public void conversionAPartirDeVenueNonSupportee() {
        convertisseur.convertir(mock(Venue.class));
    }

    private void creerDemandeBase() {
        demande = new DemandeCreationVenue();
        demande.date = DATE_VENUE;
        demande.raison = RAISON_VENUE;
        demande.departementId = ID_DEPARTEMENT;
        demande.departementCode = CODE_DEPARTEMENT;
    }

}
