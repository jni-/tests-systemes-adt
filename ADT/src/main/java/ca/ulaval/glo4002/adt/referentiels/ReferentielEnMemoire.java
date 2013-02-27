package ca.ulaval.glo4002.adt.referentiels;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import ca.ulaval.glo4002.adt.domain.Entite;
import ca.ulaval.glo4002.adt.domain.EntiteInvalidException;
import ca.ulaval.glo4002.adt.domain.Referentiel;
import ca.ulaval.glo4002.adt.services.Filtre;

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
        if (entite == null) {
            throw new EntiteInvalidException("L'entité ne peut pas être null");
        }
    }

    @Override
    public List<EntreeReferentiel<Integer, T>> filtrer(Filtre<T> filtre) {
        List<EntreeReferentiel<Integer, T>> entrees = new LinkedList<>();

        for (Entry<Integer, T> entite : donnees.entrySet()) {
            if (filtre.doitGarder(entite.getValue())) {
                entrees.add(new EntreeReferentiel<Integer, T>(entite.getKey(), entite.getValue()));
            }
        }

        return entrees;
    }
}
