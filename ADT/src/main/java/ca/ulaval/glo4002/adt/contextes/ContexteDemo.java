package ca.ulaval.glo4002.adt.contextes;

import ca.ulaval.glo4002.adt.domain.Departement;
import ca.ulaval.glo4002.adt.domain.Referentiel;
import ca.ulaval.glo4002.adt.services.LocalisateurReferentiel;

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
