package com.elapsetech.adt.rest.convertisseurs;

import static org.mockito.BDDMockito.*;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.elapsetech.adt.domain.Departement;
import com.elapsetech.adt.domain.Referentiel;
import com.elapsetech.adt.domain.Venue;
import com.elapsetech.adt.referentiels.EntreeReferentiel;
import com.elapsetech.adt.rest.dto.requests.DemandeCreationVenue;
import com.elapsetech.adt.services.Filtre;

@RunWith(MockitoJUnitRunner.class)
public class ConvertisseurDemandeCreationVenueTest {

    private static final Date DATE_VENUE = Calendar.getInstance().getTime();
    private static final int ID_DEPARTEMENT = 10;
    private static final int ID_DEPARTEMENT_INEXISTANT = -1;
    private static final String CODE_DEPARTEMENT = "code";

    @Mock
    private Referentiel<Departement> referentielDepartement;

    @Mock
    private Departement departementDuReferentiel;

    @Mock
    private Filtre<Departement> mockFiltre;

    private ConvertisseurDemandeCreationVenue convertisseur;
    private DemandeCreationVenue demande;
    private EntreeReferentiel<Integer, Departement> entree;

    @Before
    public void setUp() {
        creerDemandeBase();
        willReturn(departementDuReferentiel).given(referentielDepartement).obtenir(ID_DEPARTEMENT);

        entree = new EntreeReferentiel<>(ID_DEPARTEMENT, departementDuReferentiel);
        willReturn(Arrays.asList(entree)).given(referentielDepartement).filtrer(mockFiltre);

        creerConvertisseurAvecFiltre();
    }

    @Test
    public void demandeAvecDepartementIdAlorsLeReferentielEstAppelleAvecId() {
        demande.departementId = ID_DEPARTEMENT;

        convertisseur.convertir(demande);

        verify(referentielDepartement).obtenir(ID_DEPARTEMENT);
    }

    @Test
    public void demandeAvecDepartementCodeEtPasIdAlorsLeReferentielEstAppelleAvecFiltre() {
        demande.departementCode = CODE_DEPARTEMENT;

        convertisseur.convertir(demande);

        verify(referentielDepartement).filtrer(mockFiltre);
    }

    @Test(expected = ConversionImpossibleException.class)
    public void demandeSansdIdEtAvecCodeDepartementInexistantLanceException() {
        willReturn(new LinkedList<EntreeReferentiel<Integer, Departement>>()).given(referentielDepartement).filtrer(mockFiltre);

        convertisseur.convertir(demande);
    }

    @Test(expected = ConversionImpossibleException.class)
    public void demandeSansdIdEtAvecCodeDepartementDoublonLanceException() {
        willReturn(Arrays.asList(entree, entree)).given(referentielDepartement).filtrer(mockFiltre);

        convertisseur.convertir(demande);
    }

    @Test
    public void demandeAvecdIdEtAvecCodeDepartementIdAPriorite() {
        demande.departementId = ID_DEPARTEMENT;
        demande.departementCode = CODE_DEPARTEMENT;

        convertisseur.convertir(demande);

        verify(referentielDepartement).obtenir(ID_DEPARTEMENT);
        verify(referentielDepartement, never()).filtrer(mockFiltre);
    }

    @Test
    public void filtreBienCree() {
        Departement departement = mock(Departement.class);
        ConvertisseurDemandeCreationVenue convertisseurAvecFiltreOriginal = new ConvertisseurDemandeCreationVenue();

        Filtre<Departement> filtre = convertisseurAvecFiltreOriginal.creerFiltrePourCode(CODE_DEPARTEMENT);
        filtre.doitGarder(departement);

        verify(departement).possedeCode(CODE_DEPARTEMENT);
    }

    @Test(expected = ConversionNonSupporteeException.class)
    public void conversionAPartirDeVenueNonSupportee() {
        convertisseur.convertir(mock(Venue.class));
    }

    private void creerDemandeBase() {
        demande = new DemandeCreationVenue();
        demande.date = DATE_VENUE;
        demande.departementId = ID_DEPARTEMENT_INEXISTANT;
    }

    private void creerConvertisseurAvecFiltre() {
        convertisseur = new ConvertisseurDemandeCreationVenue(referentielDepartement) {
            @Override
            protected Filtre<Departement> creerFiltrePourCode(String code) {
                return mockFiltre;
            }
        };
    }

}
