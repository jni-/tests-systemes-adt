package com.elapsetech.adt.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DepartementTest {

    private static final String CODE = "code";
    private static final String MAUVAIS_CODE = "asdf";

    private Departement departement;

    @Before
    public void setUp() {
        departement = new Departement(CODE);
    }

    @Test
    public void possedeCodeAvecBonCodeRetourneTrue() {
        assertTrue(departement.possedeCode(CODE));
    }

    @Test
    public void possedeCodeMauvaisBonCodeRetourneFalse() {
        assertFalse(departement.possedeCode(MAUVAIS_CODE));
    }

    @Test
    public void peutDonnerUnNomAuDepartement() {
        String nom = "nom";

        departement.setNom(nom);

        assertEquals(nom, departement.getNom());
    }

}
