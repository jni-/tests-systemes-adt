package com.elapsetech.adt.referentiels;

import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.elapsetech.adt.domain.Entite;
import com.elapsetech.adt.domain.EntiteInvalidException;
import com.elapsetech.adt.services.Filtre;

@RunWith(MockitoJUnitRunner.class)
public class ReferentielEnMemoireTest {

    @Mock
    private Filtre<EntiteTest> filtre;

    private ReferentielEnMemoire<EntiteTest> referentiel;
    private EntiteTest entite;

    @Before
    public void setUp() {
        referentiel = new ReferentielEnMemoire<>();
        entite = new EntiteTest();
    }

    @Test(expected = EntiteInvalidException.class)
    public void ajouterNullLanceException() {
        referentiel.ajouter(null);
    }

    @Test
    public void ajoutDeDixElementsLesIdSontSequentiels() {
        final int nombreElements = 10;

        for (int idVoulu = 0; idVoulu < nombreElements; idVoulu++) {
            assertEquals(idVoulu, referentiel.ajouter(entite));
        }
    }

    @Test
    public void obtenirRetourneNullSiEntiteNonTrouve() {
        int idInexistant = 999;
        assertNull(referentiel.obtenir(idInexistant));
    }

    @Test
    public void peutObtenirEntiteAjoute() {
        int idAjoute = referentiel.ajouter(entite);

        EntiteTest entiteRetournee = referentiel.obtenir(idAjoute);

        assertSame(entite, entiteRetournee);
    }

    @Test
    public void retirerUnEntiteLenleveDuReferentiel() {
        int idEntite = referentiel.ajouter(entite);

        referentiel.retirer(idEntite);

        assertNull(referentiel.obtenir(idEntite));
    }

    @Test
    public void retirerUnIdInexistantNeFaitRien() {
        int idInexistant = 989;
        referentiel.retirer(idInexistant);
    }

    @Test
    public void filtrerAppelleLeFiltreAvecChaquesEntite() {
        int nombreEntite = 3;
        referentiel.ajouter(entite);
        referentiel.ajouter(entite);
        referentiel.ajouter(entite);

        referentiel.filtrer(filtre);

        verify(filtre, times(nombreEntite)).doitGarder(entite);
    }

    @Test
    public void filtrerRetourneVrai2foisSur3Alors2Resultats() {
        int nombreEntiteGarde = 2;
        referentiel.ajouter(entite);
        referentiel.ajouter(entite);
        referentiel.ajouter(entite);
        willReturn(true).willReturn(true).willReturn(false).given(filtre).doitGarder(entite);

        assertEquals(nombreEntiteGarde, referentiel.filtrer(filtre).size());

    }

    private class EntiteTest implements Entite {
    }

}
