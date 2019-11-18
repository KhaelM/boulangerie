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
public class Machine {
    private short id;
    private String nom;
    private float coutHoraire;

    public void setId(short id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCoutHoraire(float coutHoraire) {
        this.coutHoraire = coutHoraire;
    }

    public short getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public float getCoutHoraire() {
        return coutHoraire;
    }
}
