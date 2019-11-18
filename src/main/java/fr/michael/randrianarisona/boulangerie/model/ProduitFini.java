/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.michael.randrianarisona.boulangerie.model;

/**
 *
 * @author miker
 */
public class ProduitFini {
    private short id;
    private String nom;
    private float tempsDeFabrication;

    public ProduitFini(short id, String nom, float tempsDeFabrication) {
        this.id = id;
        this.nom = nom;
        this.tempsDeFabrication = tempsDeFabrication;
    }

    public ProduitFini() {
        
    }

    public float getTempsDeFabrication() {
        return tempsDeFabrication;
    }

    public void setTempsDeFabrication(float tempsDeFabrication) {
        this.tempsDeFabrication = tempsDeFabrication;
    }

    /**
     * @return the id
     */
    public short getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(short id) {
        this.id = id;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
}
