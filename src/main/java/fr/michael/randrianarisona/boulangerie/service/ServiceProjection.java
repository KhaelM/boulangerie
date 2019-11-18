/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.michael.randrianarisona.boulangerie.service;

import fr.michael.randrianarisona.boulangerie.dao.DAOProduitFini;
import fr.michael.randrianarisona.boulangerie.model.ComposanteSimulation;
import fr.michael.randrianarisona.boulangerie.model.ProduitFini;
import fr.michael.randrianarisona.boulangerie.model.exception.PasDeRecetteException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author miker
 */
public class ServiceProjection {
    private DAOProduitFini daoProduitFini;

    public ServiceProjection(DAOProduitFini daoProduitFini) {
        this.daoProduitFini = daoProduitFini;
    }
    
    public List<ProduitFini> getAllProduitFini() {
        return daoProduitFini.getAllProduitFini();
    }
    
    public int getPossibilite(List<ComposanteSimulation> composantes, String produitFini) throws Exception {
        if(composantes.isEmpty()) {
            throw new PasDeRecetteException(produitFini);
        } 
        
        boolean isQuantityEnough = true;
        
        List<ComposanteSimulation> composantesWithNotEnoughQuantity = new ArrayList<ComposanteSimulation>();
        
        for(ComposanteSimulation composante: composantes) {
            if(composante.getQuantiteDisponible() < composante.getQuantiteRequise()) {
                if(isQuantityEnough)
                    isQuantityEnough = false;
                composantesWithNotEnoughQuantity.add(composante);
            }
        }
        
        if(isQuantityEnough) {
            throw new Exception("Toutes les quantités des composantes sont déjà suffisantes.");
        }
        
        composantesWithNotEnoughQuantity = composantesWithNotEnoughQuantity.stream()
                    .sorted(Comparator.comparing(ComposanteSimulation::getProduitMinimumPossible))
                    .collect(Collectors.toList());
        
        return composantesWithNotEnoughQuantity.get(0).getProduitMinimumPossible();
    }
}
