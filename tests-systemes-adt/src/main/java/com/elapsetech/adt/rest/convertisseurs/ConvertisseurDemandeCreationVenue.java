package com.elapsetech.adt.rest.convertisseurs;

import com.elapsetech.adt.domain.Departement;
import com.elapsetech.adt.domain.Venue;
import com.elapsetech.adt.rest.dto.requests.DemandeCreationVenue;
import com.elapsetech.adt.services.DepartementIntrouvableException;
import com.elapsetech.adt.services.RechercheDepartement;

public class ConvertisseurDemandeCreationVenue implements Convertisseur<DemandeCreationVenue, Venue> {

    private RechercheDepartement rechercheDepartement;

    public ConvertisseurDemandeCreationVenue() {
        rechercheDepartement = new RechercheDepartement();
    }

    @Override
    public DemandeCreationVenue convertir(Venue entite) {
        throw new ConversionNonSupporteeException();
    }

    @Override
    public Venue convertir(DemandeCreationVenue demande) {
        Departement departement;
        try {
            departement = rechercheDepartement.rechercher(demande.departementId, demande.departementCode);
        } catch (DepartementIntrouvableException e) {
            throw new ConversionImpossibleException(e.getMessage());
        }
        Venue venue = new Venue(demande.date, departement);
        venue.setRaisonDeVenue(demande.raison);
        return venue;
    }

    ConvertisseurDemandeCreationVenue(RechercheDepartement recherche) {
        rechercheDepartement = recherche;
    }
}
