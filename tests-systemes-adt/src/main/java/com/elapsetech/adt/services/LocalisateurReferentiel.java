package com.elapsetech.adt.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.elapsetech.adt.domain.Entite;
import com.elapsetech.adt.domain.Referentiel;
import com.elapsetech.adt.referentiels.ReferentielEnMemoire;

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
