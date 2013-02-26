package com.elapsetech.adt.referentiels;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.elapsetech.adt.domain.Entite;
import com.elapsetech.adt.domain.EntiteInvalidException;

public class ReferentielEnMemoireTest {

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

    private class EntiteTest implements Entite {
    }

}
