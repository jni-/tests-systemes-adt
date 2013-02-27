package com.elapsetech.adt.services;

import static org.mockito.BDDMockito.*;
import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.elapsetech.adt.domain.Departement;
import com.elapsetech.adt.domain.Referentiel;
import com.elapsetech.adt.referentiels.EntreeReferentiel;

@RunWith(MockitoJUnitRunner.class)
public class RechercheDepartementTest {

    private static final int ID_DEPARTEMENT = 10;
    private static final int ID_DEPARTEMENT_VIDE = -1;

    private static final String CODE_DEPARTEMENT = "un code";
    private static final String CODE_DEPARTEMENT_VIDE = null;

    @Mock
    private Referentiel<Departement> referentielDepartement;

    @Mock
    private Filtre<Departement> mockFiltre;

    @Mock
    private EntreeReferentiel<Integer, Departement> entree;

    @Mock
    private Departement departementDuReferentiel;

    private RechercheDepartement recherche;

    @Before
    public void setUp() {
        recherche = new RechercheDepartement(referentielDepartement) {
            @Override
            Filtre<Departement> creerFiltrePourCode(String code) {
                return mockFiltre;
            }
        };
        willReturn(departementDuReferentiel).given(referentielDepartement).obtenir(ID_DEPARTEMENT);
        willReturn(Arrays.asList(entree)).given(referentielDepartement).filtrer(mockFiltre);
        willReturn(departementDuReferentiel).given(entree).getEntite();
    }

    @Test
    public void demandeAvecDepartementIdAlorsLeReferentielEstAppelleAvecId() throws DepartementIntrouvableException {
        recherche.rechercher(ID_DEPARTEMENT, CODE_DEPARTEMENT_VIDE);

        verify(referentielDepartement).obtenir(ID_DEPARTEMENT);
    }

    @Test
    public void demandeAvecDepartementCodeEtPasIdAlorsLeReferentielEstAppelleAvecFiltre() throws DepartementIntrouvableException {
        recherche.rechercher(ID_DEPARTEMENT_VIDE, CODE_DEPARTEMENT);

        verify(referentielDepartement).filtrer(mockFiltre);
    }

    @Test(expected = DepartementIntrouvableException.class)
    public void demandeSansdIdEtAvecCodeDepartementInexistantLanceException() throws DepartementIntrouvableException {
        willReturn(new LinkedList<EntreeReferentiel<Integer, Departement>>()).given(referentielDepartement).filtrer(mockFiltre);

        recherche.rechercher(ID_DEPARTEMENT_VIDE, CODE_DEPARTEMENT);
    }

    @Test(expected = DepartementIntrouvableException.class)
    public void demandeSansdIdEtAvecCodeDepartementDoublonLanceException() throws DepartementIntrouvableException {
        willReturn(Arrays.asList(entree, entree)).given(referentielDepartement).filtrer(mockFiltre);

        recherche.rechercher(ID_DEPARTEMENT_VIDE, CODE_DEPARTEMENT);
    }

    @Test
    public void demandeAvecdIdEtAvecCodeDepartementIdAPriorite() throws DepartementIntrouvableException {
        recherche.rechercher(ID_DEPARTEMENT, CODE_DEPARTEMENT);

        verify(referentielDepartement).obtenir(ID_DEPARTEMENT);
        verify(referentielDepartement, never()).filtrer(mockFiltre);
    }

    @Test
    public void filtreBienCree() {
        Departement departement = mock(Departement.class);
        RechercheDepartement rechercheAvecFiltreOriginal = new RechercheDepartement();

        Filtre<Departement> filtre = rechercheAvecFiltreOriginal.creerFiltrePourCode(CODE_DEPARTEMENT);
        filtre.doitGarder(departement);

        verify(departement).possedeCode(CODE_DEPARTEMENT);
    }
}
