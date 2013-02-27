package com.elapsetech.adt.referentiels;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.elapsetech.adt.domain.Entite;

public class EntreeReferentielTest {

    private Integer cle;
    private Entite entite;
    private EntreeReferentiel<Integer, Entite> entree;

    @Before
    public void setUp() {
        cle = 10;
        entite = mock(Entite.class);
        entree = new EntreeReferentiel<Integer, Entite>(cle, entite);
    }

    @Test
    public void peutObtenirCle() {
        assertSame(cle, entree.getCle());
    }

    @Test
    public void peutObtenirEntite() {
        assertSame(entite, entree.getEntite());
    }

}
