package com.elapsetech.adt.referentiels;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.elapsetech.adt.domain.Entite;
import com.elapsetech.adt.domain.EntiteInvalidException;
import com.elapsetech.adt.domain.Referentiel;

public class ReferentielEnMemoire<T extends Entite> implements Referentiel<T> {

    private Map<Integer, T> donnees = new HashMap<>();
    private AtomicInteger idCourant = new AtomicInteger(0);
    
    @Override
    public T obtenir(int id) {
        return donnees.get(id);
    }

    @Override
    public int ajouter(T entite) {
        validerEntiteNonNull(entite);
        
        int id = obtenirIdSuivant();
        donnees.put(id, entite);
        return id;
    }

    private Integer obtenirIdSuivant() {
        return idCourant.getAndIncrement();
    }

    @Override
    public void retirer(int id) {
        donnees.remove(id);
    }

    private void validerEntiteNonNull(T entite) {
        if(entite == null) {
            throw new EntiteInvalidException("L'entité ne peut pas être null");
        }
    }
}
