package com.elapsetech.adt.rest.convertisseurs;

import com.elapsetech.adt.domain.Entite;

public interface Convertisseur<TSource, TDestination extends Entite> {

    public TSource convertir(TDestination entite);
    
    public TDestination convertir(TSource entite);
    
}
