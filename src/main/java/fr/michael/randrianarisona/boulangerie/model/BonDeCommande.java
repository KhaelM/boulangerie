package fr.michael.randrianarisona.boulangerie.model;

import java.util.ArrayList;
import java.util.List;

/**
 * BonDeCommande
 */
public class BonDeCommande {

    private long id;
    private short idFournisseur;
    private String nomFournisseur;
    private List<AchatComposante> commandes = new ArrayList<AchatComposante>();

    /**
     * @return the commandes
     */
    public List<AchatComposante> getCommandes() {
        return commandes;
    }

    /**
     * @param commandes the commandes to set
     */
    public void setCommandes(List<AchatComposante> commandes) {
        this.commandes = commandes;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the idFournisseur
     */
    public short getIdFournisseur() {
        return idFournisseur;
    }
    /**
     * @param idFournisseur the idFournisseur to set
     */
    public void setIdFournisseur(short idFournisseur) {
        this.idFournisseur = idFournisseur;
    }

    /**
     * @return the nomFournisseur
     */
    public String getNomFournisseur() {
        return nomFournisseur;
    }
    /**
     * @param nomFournisseur the nomFournisseur to set
     */
    public void setNomFournisseur(String nomFournisseur) {
        this.nomFournisseur = nomFournisseur;
    }
}