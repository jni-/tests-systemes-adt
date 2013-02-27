package com.elapsetech.adt.services;

import java.util.List;

import com.elapsetech.adt.domain.Departement;
import com.elapsetech.adt.domain.Referentiel;
import com.elapsetech.adt.referentiels.EntreeReferentiel;

public class RechercheDepartement {

    private Referentiel<Departement> referentielDepartement;

    public RechercheDepartement() {
        referentielDepartement = LocalisateurReferentiel.obtenir(Departement.class);
    }

    public Departement rechercher(int id, String code) throws DepartementIntrouvableException {
        Departement departement;
        if (id >= 0) {
            departement = referentielDepartement.obtenir(id);
        } else {
            departement = obtenirDepartementAvecCode(code);
        }

        if (departement == null) {
            throw new DepartementIntrouvableException(String.format("Le departement avec id=%d OU code=%s n'existe pas", id, code));
        }

        return departement;
    }

    private Departement obtenirDepartementAvecCode(String code) throws DepartementIntrouvableException {
        List<EntreeReferentiel<Integer, Departement>> entrees = referentielDepartement.filtrer(creerFiltrePourCode(code));

        if (entrees.isEmpty()) {
            return null;
        }

        if (entrees.size() > 1) {
            throw new DepartementIntrouvableException("Il existe plusieurs départements avec le code " + code);
        }

        return entrees.get(0).getEntite();
    }

    Filtre<Departement> creerFiltrePourCode(final String code) {
        return new Filtre<Departement>() {
            @Override
            public boolean doitGarder(Departement entite) {
                return entite.possedeCode(code);
            }
        };
    }

    RechercheDepartement(Referentiel<Departement> referentielDepartement2) {
        referentielDepartement = referentielDepartement2;
    }
}
