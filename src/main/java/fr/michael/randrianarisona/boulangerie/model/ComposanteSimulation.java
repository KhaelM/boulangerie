/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.michael.randrianarisona.boulangerie.model;

import java.util.ArrayList;
import java.util.List;

import fr.michael.randrianarisona.boulangerie.model.exception.NegatifException;

/**
 *
 * @author miker
 */
public class ComposanteSimulation extends Composante {
    private float quantiteRequise;
    private float quantiteUnitaire;
    private String couleurDeFond;
    private String uniteDeMesure;
    private String abreviationUniteDeMesure;
    private List<AchatComposante> achats = new ArrayList<AchatComposante>();
    
    public ComposanteSimulation(long id, String nom, float quantiteDisponible, float quantiteUnitaire, float quantiteProduitFiniVoulu, String uniteDeMesure, String abreviationUniteDeMesure) throws NegatifException {
        super(id, nom, quantiteDisponible);
        this.setQuantiteUnitaire(quantiteUnitaire);
        this.setQuantiteRequise(quantiteProduitFiniVoulu);
        this.setCouleurDeFond();
        this.setUniteDeMesure(uniteDeMesure);
        this.setAbreviationUniteDeMesure(abreviationUniteDeMesure);
    }

    public ComposanteSimulation() {
    }
    
    public float getQuantiteCommande() {
        return getQuantiteRequise() - getQuantiteDisponible();
    }

	/**
     * @return the achats
     */
    public List<AchatComposante> getAchats() {
        return achats;
    }

    /**
     * @param achats the achats to set
     */
    public void setAchats(List<AchatComposante> achats) {
        this.achats = achats;
    }
    
    public float getMontantParComposante() {
        float montant = 0;

        for (int i = 0; i < achats.size(); i++) {
            montant += achats.get(i).getCoutAchat();
        }

        return montant;
    }
    
    public int getProduitMinimumPossible() {
        return (int) (this.quantiteDisponible / this.quantiteUnitaire);
    }

    public String getAbreviationUniteDeMesure() {
        return abreviationUniteDeMesure;
    }

    public void setAbreviationUniteDeMesure(String abreviationUniteDeMesure) {
        if(abreviationUniteDeMesure == null) {
            this.abreviationUniteDeMesure = "U";
        } else {
            this.abreviationUniteDeMesure = abreviationUniteDeMesure;
        }
    }

    public void setUniteDeMesure(String uniteDeMesure) {
        if(uniteDeMesure == null) {
            this.uniteDeMesure = "par unité";
        } else {
            this.uniteDeMesure = uniteDeMesure;
        }
    }

    public String getUniteDeMesure() {
        return uniteDeMesure;
    }
    
    public void setQuantiteRequise(float quantiteProduitFiniVoulu) throws NegatifException {
        this.quantiteRequise = quantiteUnitaire * quantiteProduitFiniVoulu;
    }

    public float getQuantiteRequise() {
        return quantiteRequise;
    }
    
    public String getCouleurDeFond() {
        return couleurDeFond;
    }
    
    private void setCouleurDeFond() {
        this.couleurDeFond = this.quantiteDisponible < this.quantiteRequise ? "red" : "green";
    }
    
    public float getQuantiteUnitaire() {
        return quantiteUnitaire;
    }

    public void setQuantiteUnitaire(float quantiteUnitaire) throws NegatifException {
        if (quantiteUnitaire < 0) {
            throw new NegatifException("La quantité disponible");
        }
        this.quantiteUnitaire = quantiteUnitaire;
    }
}
