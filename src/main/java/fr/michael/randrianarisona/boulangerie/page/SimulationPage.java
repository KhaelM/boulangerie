/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.michael.randrianarisona.boulangerie.page;

import fr.michael.randrianarisona.boulangerie.exception.DoitEtreRenseigneException;
import fr.michael.randrianarisona.boulangerie.model.ComposanteSimulation;
import fr.michael.randrianarisona.boulangerie.model.ProduitFini;
import fr.michael.randrianarisona.boulangerie.model.exception.NegatifException;
import fr.michael.randrianarisona.boulangerie.service.exception.ConversionException;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author miker
 */
public class SimulationPage extends BasePage {

    private List<ComposanteSimulation> composantes;
    private boolean boutonPossibleVisible = false;
    private Date dateFabrication;
    public int quantiteVoulu;
    ProduitFini produitFini;

    public SimulationPage() {
        super();
        this.composantes = new ArrayList<ComposanteSimulation>();
    }

    public SimulationPage(List<ComposanteSimulation> composantes) {
        super();
        this.composantes = composantes;
        for (ComposanteSimulation tempComposante : composantes) {
            if (tempComposante.getCouleurDeFond().equals("red")) {
                boutonPossibleVisible = true;
                break;
            }
        }
    }

    public ProduitFini getProduitFini() {
        return produitFini;
    }

    public void setProduitFini(ProduitFini produitFini) {
        this.produitFini = produitFini;
    }

    public int getQuantiteVoulu() {
        return quantiteVoulu;
    }

    public void setQuantiteVoulu(String quantiteVoulu) throws DoitEtreRenseigneException, NegatifException, ConversionException {
        quantiteVoulu = quantiteVoulu.trim();
        try {
            if (quantiteVoulu.isEmpty()) {
                throw new DoitEtreRenseigneException("La quantité voulu");
            }
            int temp = Integer.parseInt(quantiteVoulu);
            if (temp < 0) {
                throw new NegatifException("Quantité voulu");
            }
            this.quantiteVoulu = temp;
        } catch (NumberFormatException e) {
            throw new ConversionException("la quantité voulu");
        } catch (NegatifException e) {
            this.quantiteVoulu = 0;
            throw e;
        }
    }

    public Date getDateFabrication() {
        return dateFabrication;
    }

    public void setDateFabrication(String dateFabrication) throws DoitEtreRenseigneException, ConversionException {
        try {
            if(dateFabrication.isEmpty()) {
                throw new DoitEtreRenseigneException("La date");
            }
            this.dateFabrication = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dateFabrication).getTime());
        } catch (ParseException e) {
            this.dateFabrication = new Date(0);
            throw new ConversionException("la date");
        }
    }

    public void setComposantes(List<ComposanteSimulation> composantes) {
        this.composantes = composantes;
    }

    public List<ComposanteSimulation> getComposantes() {
        return composantes;
    }

    public boolean isBoutonPossibleVisible() {
        return boutonPossibleVisible;
    }

}
