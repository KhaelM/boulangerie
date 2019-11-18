/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.michael.randrianarisona.boulangerie.page;

import fr.michael.randrianarisona.boulangerie.model.ProduitFini;
import java.util.List;

/**
 *
 * @author miker
 */
public class ProjectionPage extends BasePage {
    private List<ProduitFini> produitsFini;
    private String quantiteVoulu;

    public ProjectionPage() {
        super();
        this.quantiteVoulu = "";
    }

    public ProjectionPage(List<ProduitFini> produitsFini, String quantiteVoulu) {
        super();
        this.produitsFini = produitsFini;
        this.quantiteVoulu = quantiteVoulu;
    }

    public List<ProduitFini> getProduitsFini() {
        return produitsFini;
    }

    public String getQuantiteVoulu() {
        return quantiteVoulu;
    }

    public void setProduitsFini(List<ProduitFini> produitsFini) {
        this.produitsFini = produitsFini;
    }

    public void setQuantiteVoulu(String quantiteVoulu) {
        this.quantiteVoulu = quantiteVoulu;
    }
}
