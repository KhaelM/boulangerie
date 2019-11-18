/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.michael.randrianarisona.boulangerie.service;

import fr.michael.randrianarisona.boulangerie.dao.DAOComposanteSimulation;
import fr.michael.randrianarisona.boulangerie.dao.DAOProduitFini;
import fr.michael.randrianarisona.boulangerie.model.ComposanteSimulation;
import fr.michael.randrianarisona.boulangerie.model.ProduitFini;
import fr.michael.randrianarisona.boulangerie.model.exception.NegatifException;
import fr.michael.randrianarisona.boulangerie.service.exception.ConversionException;

import java.util.List;

/**
 *
 * @author miker
 */
public class ServiceSimulation {
    private DAOComposanteSimulation daoComposanteSimulation;
    private DAOProduitFini daoProduitFini;

    public ServiceSimulation(DAOComposanteSimulation daoComposanteSimulation, DAOProduitFini daoProduitFini) {
        this(daoComposanteSimulation);
        this.daoProduitFini = daoProduitFini;
    }
    

    public ServiceSimulation(DAOComposanteSimulation daoComposanteSimulation) {
        this.daoComposanteSimulation = daoComposanteSimulation;
    }

    public void setDaoProduitFini(DAOProduitFini daoProduitFini) {
        this.daoProduitFini = daoProduitFini;
    }
    
    public List<ComposanteSimulation> getComposantes(String produitFini, String quantiteProduitFiniVoulu) throws NegatifException, ConversionException {
        List<ComposanteSimulation> composantes = null;
        try {
            float arg2 = Float.parseFloat(quantiteProduitFiniVoulu);
            if(arg2 < 0) {
                throw new NegatifException("Quantité produit fini");
            }
            composantes = this.daoComposanteSimulation.getComposantes(produitFini, arg2);
        } catch(NumberFormatException e) {
            throw new ConversionException("la quantité voulu");
        }
        return composantes;
    }
    
    public ProduitFini getProduitFini(String nomProduit) {
        return this.daoProduitFini.getProduitFini(nomProduit);
    }
}
