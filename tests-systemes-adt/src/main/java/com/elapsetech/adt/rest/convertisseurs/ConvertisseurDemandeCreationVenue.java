package com.elapsetech.adt.rest.convertisseurs;

import java.util.List;
import com.elapsetech.adt.domain.Departement;
import com.elapsetech.adt.domain.Referentiel;
import com.elapsetech.adt.domain.Venue;
import com.elapsetech.adt.referentiels.EntreeReferentiel;
import com.elapsetech.adt.rest.dto.requests.DemandeCreationVenue;
import com.elapsetech.adt.services.Filtre;
import com.elapsetech.adt.services.LocalisateurReferentiel;

public class ConvertisseurDemandeCreationVenue implements Convertisseur<DemandeCreationVenue, Venue> {

    private Referentiel<Departement> referentielDepartement;

    public ConvertisseurDemandeCreationVenue() {
        referentielDepartement = LocalisateurReferentiel.obtenir(Departement.class);
    }

    @Override
    public DemandeCreationVenue convertir(Venue entite) {
        throw new ConversionNonSupporteeException();
    }

    @Override
    public Venue convertir(DemandeCreationVenue demande) {
        Departement departement = resoudreDepartement(demande);
        Venue venue = new Venue(demande.date, departement);
        venue.setRaisonDeVenue(demande.raison);
        return venue;
    }

    private Departement resoudreDepartement(DemandeCreationVenue demande) {
        Departement departement;
        if (demande.departementId >= 0) {
            departement = referentielDepartement.obtenir(demande.departementId);
        } else {
            departement = obtenirDepartementAvecCode(demande.departementCode);
        }

        if (departement == null) {
            throw new ConversionImpossibleException(String.format("Le departement avec id=%d OU code=%s n'existe pas",
                    demande.departementId, demande.departementCode));
        }

        return departement;
    }

    private Departement obtenirDepartementAvecCode(String code) {
        List<EntreeReferentiel<Integer, Departement>> entrees = referentielDepartement.filtrer(creerFiltrePourCode(code));

        if (entrees.isEmpty()) {
            return null;
        }

        if (entrees.size() > 1) {
            throw new ConversionImpossibleException("Il existe plusieurs départements avec le code " + code);
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

    ConvertisseurDemandeCreationVenue(Referentiel<Departement> referentiel) {
        referentielDepartement = referentiel;
    }

}
