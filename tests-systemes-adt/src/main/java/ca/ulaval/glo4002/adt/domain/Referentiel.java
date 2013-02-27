package ca.ulaval.glo4002.adt.domain;

import java.util.List;

import ca.ulaval.glo4002.adt.referentiels.EntreeReferentiel;
import ca.ulaval.glo4002.adt.services.Filtre;

public interface Referentiel<T extends Entite> {

    T obtenir(int id);

    int ajouter(T entite);

    void retirer(int id);

    public abstract List<EntreeReferentiel<Integer, T>> filtrer(Filtre<T> filtre);
}
