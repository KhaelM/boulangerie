/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.michael.randrianarisona.boulangerie.service;

import fr.michael.randrianarisona.boulangerie.dao.DAOValider;
import fr.michael.randrianarisona.boulangerie.model.ComposanteSimulation;
import fr.michael.randrianarisona.boulangerie.model.ProduitFini;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author miker
 */
public class ServiceValider {
    private DAOValider daoValider;

    public void setDaoValider(DAOValider daoValider) {
        this.daoValider = daoValider;
    }
    
    public void Valider(ProduitFini produitFini, List<ComposanteSimulation> composantes, Date dateFabrication, float quantite) throws Exception {
        for(ComposanteSimulation composante: composantes) {
            if(composante.getQuantiteDisponible() < composante.getQuantiteRequise()) {
                throw new Exception("Il n'y a pas assez de quantité de " + composante.getNom() + ".");
            }
        }
        
       daoValider.insererFabrication(produitFini, dateFabrication, quantite);
    }
}
