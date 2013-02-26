package com.elapsetech.adt.domain;


public interface Referentiel<T extends Entite> {
    
    T obtenir(int id);
    int ajouter(T entite);
    void retirer(int id);
}
