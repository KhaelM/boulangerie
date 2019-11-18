/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.michael.randrianarisona.boulangerie.model;

import fr.michael.randrianarisona.boulangerie.model.exception.NegatifException;

/**
 *
 * @author miker
 */
public class Composante {

    protected long id;
    protected String nom;
    protected float quantiteDisponible;

    public Composante() {
    }

    public Composante(long id, String nom, float quantiteDisponible) throws NegatifException {
        this.id = id;
        this.nom = nom;
        this.setQuantiteDisponible(quantiteDisponible);
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setQuantiteDisponible(float quantiteDisponible) throws NegatifException {
        if (quantiteDisponible < 0) {
            throw new NegatifException("La quantité disponible");
        }
        this.quantiteDisponible = quantiteDisponible;
    }

    public float getQuantiteDisponible() {
        return quantiteDisponible;
    }

    public String getNom() {
        return nom;
    }
}
