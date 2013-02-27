package ca.ulaval.glo4002.adt.services;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.ulaval.glo4002.adt.domain.Entite;
import ca.ulaval.glo4002.adt.services.LocalisateurReferentiel;

public class LocalisateurReferentielTest {

    @Test
    public void deuxReferentielsPourLaMemeEntiteSontIdentiques() {
        assertSame(LocalisateurReferentiel.obtenir(Entite.class), LocalisateurReferentiel.obtenir(Entite.class));
    }

    @Test
    public void deuxReferentielsPourLaDesEntiteDifferentsSontDifferents() {
        assertNotSame(LocalisateurReferentiel.obtenir(Entite.class), LocalisateurReferentiel.obtenir(AutreEntite.class));
    }

    private interface AutreEntite extends Entite {
    }
}
