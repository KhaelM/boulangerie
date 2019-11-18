package fr.michael.randrianarisona.boulangerie.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import fr.michael.randrianarisona.boulangerie.dao.DAOBonDeCommande;
import fr.michael.randrianarisona.boulangerie.model.AchatComposante;
import fr.michael.randrianarisona.boulangerie.model.BonDeCommande;
import fr.michael.randrianarisona.boulangerie.model.ComposanteSimulation;

/**
 * ServiceBonDeCommande
 */
public class ServiceBonDeCommande {
    private DAOBonDeCommande daoBonDeCommande;

    public void insertBonsDeCommande(List<BonDeCommande> bonsDeCommande, Date date_commande) {
        daoBonDeCommande.insererBonsDeCommande(bonsDeCommande, date_commande);
    }

    /**
     * @param daoBonDeCommande the daoBonDeCommande to set
     */
    public void setDaoBonDeCommande(DAOBonDeCommande daoBonDeCommande) {
        this.daoBonDeCommande = daoBonDeCommande;
    }

    public List<BonDeCommande> genererBonsDeCommande(List<ComposanteSimulation> composantesSimulation) {
        List<ComposanteSimulation> insuffisants = new ArrayList<ComposanteSimulation>();

        for (ComposanteSimulation composante: composantesSimulation) {
            if(composante.getQuantiteDisponible() < composante.getQuantiteRequise()) {
                insuffisants.add(composante);
            }
        }
        
        List<BonDeCommande> bonDeCommandes = new ArrayList<BonDeCommande>();


        for (ComposanteSimulation insuffisant : insuffisants) {
            AchatComposante plusRecent = daoBonDeCommande.getFournisseurLePlusRecent(insuffisant);
            boolean fournisseurDansListe = false;
            for (int i = 0; i < bonDeCommandes.size(); i++) {
                if(bonDeCommandes.get(i).getIdFournisseur() == plusRecent.getIdFournisseur()) {
                    fournisseurDansListe = true;
                    bonDeCommandes.get(i).getCommandes().add(plusRecent);
                    break;
                }
            } 
            if(!fournisseurDansListe) {
                BonDeCommande bonDeCommande = new BonDeCommande();
                bonDeCommande.setIdFournisseur(plusRecent.getIdFournisseur());
                bonDeCommande.setNomFournisseur(plusRecent.getNomFournisseur());

                List<AchatComposante> commandes = new ArrayList<AchatComposante>();
                commandes.add(plusRecent);
                bonDeCommande.setCommandes(commandes);

                bonDeCommandes.add(bonDeCommande);
            }
        }

        return bonDeCommandes;
    }
}