package com.elapsetech.adt.domain;

import java.util.List;
import com.elapsetech.adt.referentiels.EntreeReferentiel;
import com.elapsetech.adt.services.Filtre;

public interface Referentiel<T extends Entite> {

    T obtenir(int id);

    int ajouter(T entite);

    void retirer(int id);

    public abstract List<EntreeReferentiel<Integer, T>> filtrer(Filtre<T> filtre);
}
