package ca.ulaval.glo4002.adt.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import ca.ulaval.glo4002.adt.domain.Entite;
import ca.ulaval.glo4002.adt.domain.Referentiel;
import ca.ulaval.glo4002.adt.referentiels.ReferentielEnMemoire;

public class LocalisateurReferentiel {

    private static Map<Class<?>, Referentiel<?>> referentiels = Collections.synchronizedMap(new HashMap<Class<?>, Referentiel<?>>());

    @SuppressWarnings("unchecked")
    public static <T extends Entite> Referentiel<T> obtenir(Class<T> clazz) {
        if (referentiels.get(clazz) == null) {
            referentiels.put(clazz, new ReferentielEnMemoire<T>());
        }

        return (Referentiel<T>) referentiels.get(clazz);
    }
}
