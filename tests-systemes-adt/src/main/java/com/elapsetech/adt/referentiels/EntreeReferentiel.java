package com.elapsetech.adt.referentiels;

import com.elapsetech.adt.domain.Entite;

public class EntreeReferentiel<TCle, TEntite extends Entite> {

    private TEntite entite;
    private TCle cle;

    public EntreeReferentiel(TCle cle, TEntite entite) {
        this.cle = cle;
        this.entite = entite;
    }

    public TEntite getEntite() {
        return entite;
    }

    public TCle getCle() {
        return cle;
    }

}
