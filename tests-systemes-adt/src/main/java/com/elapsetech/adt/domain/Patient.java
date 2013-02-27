package com.elapsetech.adt.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Patient extends Personne implements Entite {

    private final String nam;
    private HashMap<Integer, Venue> venues = new HashMap<>();
    private AtomicInteger numeroVenueCourant = new AtomicInteger();

    public Patient(String nam, String prenom, String nom) {
        super(prenom, nom);
        this.nam = nam;
    }

    public String getNam() {
        return nam;
    }

    public int ajouterVenue(Venue venue) {
        int numero = numeroVenueCourant.getAndIncrement();
        venues.put(numero, venue);
        return numero;
    }

    public Venue obtenirVenue(int numero) {
        if (!venues.containsKey(numero)) {
            throw new VenueInexistanteException();
        }
        return venues.get(numero);
    }

    public Collection<Venue> getVenues() {
        return venues.values();
    }

}
