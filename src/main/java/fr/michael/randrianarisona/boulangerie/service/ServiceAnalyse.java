/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.michael.randrianarisona.boulangerie.service;

import fr.michael.randrianarisona.boulangerie.dao.DAOMachine;
import fr.michael.randrianarisona.boulangerie.model.ComposanteSimulation;
import fr.michael.randrianarisona.boulangerie.model.Machine;
import fr.michael.randrianarisona.boulangerie.model.ProduitFini;
import fr.michael.randrianarisona.boulangerie.parameters.Parameters;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 *
 * @author miker
 */
public class ServiceAnalyse {
    private DAOMachine daoMachine;

    public ServiceAnalyse() {
    }

    public ServiceAnalyse(DAOMachine daoMachine) {
        this.daoMachine = daoMachine;
    }
    
    public BigDecimal getChargesMachines(List<Machine> machines, ProduitFini produitFini) {
        BigDecimal charges = new BigDecimal("0");
        
        for(Machine machine: machines) {
            charges = charges.add(new BigDecimal(machine.getCoutHoraire() * produitFini.getTempsDeFabrication()));
        }
        
        return charges.setScale(Parameters.FLOATING_POINT_PRECISION, RoundingMode.HALF_UP);
    }
    
    public float getMontantComposanteTotal(List<ComposanteSimulation> composantes) {
        return (float) composantes.stream().mapToDouble((composanteSimulation) -> composanteSimulation.getMontantParComposante()).sum();
    }
    
    public BigDecimal getCoutHoraires(List<Machine> machines, ProduitFini produitFini) {
        BigDecimal coutHoraires = new BigDecimal("0");
        
        coutHoraires = coutHoraires.add(getChargesMachines(machines, produitFini));
        
        return coutHoraires.setScale(Parameters.FLOATING_POINT_PRECISION, RoundingMode.HALF_UP);
    }
    
    public List<Machine> getMachines(String nomProduitFini) {
        return daoMachine.getMachines(nomProduitFini);
    }
}
