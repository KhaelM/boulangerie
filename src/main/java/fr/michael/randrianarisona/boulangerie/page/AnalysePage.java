/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.michael.randrianarisona.boulangerie.page;

import fr.michael.randrianarisona.boulangerie.model.ComposanteSimulation;
import fr.michael.randrianarisona.boulangerie.model.Machine;
import fr.michael.randrianarisona.boulangerie.model.ProduitFini;
import fr.michael.randrianarisona.boulangerie.model.exception.NegatifException;
import fr.michael.randrianarisona.boulangerie.service.ServiceAnalyse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author miker
 */
public class AnalysePage extends BasePage {
    private List<ComposanteSimulation> composantes;
    private List<Machine> machines;
    private ProduitFini produitFini;
    private float quantite;
    private Date dateFabrication;
    
    public AnalysePage() {
        super();
        this.composantes = new ArrayList<ComposanteSimulation>();
        this.machines = new ArrayList<Machine>();
    }
    
    public BigDecimal getCoutDeProductionMachine(Machine machine, float tempsFabrication) {
        return new BigDecimal(machine.getCoutHoraire() * tempsFabrication).setScale(2, RoundingMode.HALF_DOWN);
    }

    public Date getDateFabrication() {
        return dateFabrication;
    }

    public void setDateFabrication(String dateFabrication) throws Exception {
        try {
            if(dateFabrication.isEmpty()) {
                throw new Exception("La date doit être indiquée.");
            }
            this.dateFabrication = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dateFabrication).getTime());
        } catch (ParseException e) {
            this.dateFabrication = new Date(0);
            throw new Exception("Le format de la date est incorrect.");
        }
    }
    
    public BigDecimal getChargesMachines() {
        return new ServiceAnalyse().getChargesMachines(machines, produitFini);
    }

    public void setQuantite(String quantite) throws Exception {
        quantite = quantite.trim();
        try {
            if(quantite.isEmpty()) {
                throw new Exception("La quantité voulu doit être indiquée.");
            }
            float temp = Float.parseFloat(quantite);
            if(temp < 0) {
                throw new NegatifException("Quantité voulu");
            }
            this.quantite = temp;
        } catch(NumberFormatException e) {
            throw new Exception("Format de quantité voulu incorrect.");
        }
    }

    public float getQuantite() {
        return quantite;
    }
    
    public BigDecimal getCoutDeFabrication() {
        return getCoutHoraires().add(new BigDecimal(getMontantTotal()));
    }
    
    public float getMontantTotal() {
        return getMontantTotalComposantes() * quantite;
    }

    public ProduitFini getProduitFini() {
        return produitFini;
    }

    public void setProduitFini(ProduitFini produitFini) {
        this.produitFini = produitFini;
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public List<ComposanteSimulation> getComposantes() {
        return composantes;
    }

    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }

    public void setComposantes(List<ComposanteSimulation> composantes) {
        this.composantes = composantes;
    }

    public float getMontantTotalComposantes() {
        return new ServiceAnalyse().getMontantComposanteTotal(composantes);
    }

    public BigDecimal getCoutHoraires() {
        return new ServiceAnalyse().getCoutHoraires(machines, produitFini);
    }
}
