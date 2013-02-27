package ca.ulaval.glo4002.adt.rest.convertisseurs;

import ca.ulaval.glo4002.adt.domain.Entite;

public interface Convertisseur<TSource, TDestination extends Entite> {

    public TSource convertir(TDestination entite);
    
    public TDestination convertir(TSource entite);
    
}
