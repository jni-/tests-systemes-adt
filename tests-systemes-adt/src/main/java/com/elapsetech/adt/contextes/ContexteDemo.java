package com.elapsetech.adt.contextes;

import com.elapsetech.adt.domain.Departement;
import com.elapsetech.adt.domain.Referentiel;
import com.elapsetech.adt.services.LocalisateurReferentiel;

public class ContexteDemo implements ContexteAdt {

    @Override
    public void creer() {
        creerDepartements();
    }

    private void creerDepartements() {
        Referentiel<Departement> referentiel = LocalisateurReferentiel.obtenir(Departement.class);
        referentiel.ajouter(creerDepartement("URG", "Urgence"));
        referentiel.ajouter(creerDepartement("PSY", "Psychiatrie"));
        referentiel.ajouter(creerDepartement("NEONAT", "NeoNatalite"));
        referentiel.ajouter(creerDepartement("CARD", "Cardiologie"));
    }

    private Departement creerDepartement(String code, String nom) {
        Departement departement = new Departement(code);
        departement.setNom(nom);
        return departement;
    }

}
