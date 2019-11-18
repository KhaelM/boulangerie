package fr.michael.randrianarisona.boulangerie.model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * AchatComposante
 */
public class AchatComposante {
    private ComposanteSimulation composante;
    private short idFournisseur;
    private Date dateAchat;
    private float quantite;
    private float prixUnitaire;
    private float quantiteRestante;
    private float quantiteUtilise;
    private String nomFournisseur;

    /**
     * @return the composante
     */
    public ComposanteSimulation getComposante() {
        return composante;
    }
    /**
     * @param composante the composante to set
     */
    public void setComposante(ComposanteSimulation composante) {
        this.composante = composante;
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

    public String getAffichageDate() {
        Locale locale = new Locale("fr");
        String pattern = "EEEE dd MMMM yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, locale);
        return simpleDateFormat.format(dateAchat);
    }

    public float getCoutAchat() {
        return quantiteUtilise * prixUnitaire;
    }

    /**
     * @return the quantiteUtilise
     */
    public float getQuantiteUtilise() {
        return quantiteUtilise;
    }

    /**
     * @param quantiteUtilise the quantiteUtilise to set
     */
    public void setQuantiteUtilise(float quantiteRequise) {
        if(quantiteRequise > quantiteRestante) {
            this.quantiteUtilise = quantiteRestante;
        } else {
            this.quantiteUtilise = quantiteRequise;
        }
    }

    /**
     * @return the quantiteRestante
     */
    public float getQuantiteRestante() {
        return quantiteRestante;
    }

    /**
     * @param quantiteRestante the quantiteRestante to set
     */
    public void setQuantiteRestante(float quantiteRestante) {
        this.quantiteRestante = quantiteRestante;
    }
    
    /**
     * @return the quantite
     */
    public float getQuantite() {
        return quantite;
    }

    /**
     * @param quantite the quantite to set
     */
    public void setQuantite(float quantite) {
        this.quantite = quantite;
    }
    
    /**
     * @return the prixUnitaire
     */
    public float getPrixUnitaire() {
        return prixUnitaire;
    }
    
    /**
     * @param prixUnitaire the prixUnitaire to set
     */
    public void setPrixUnitaire(float prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
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
     * @return the dateAchat
     */
    public Date getDateAchat() {
        return dateAchat;
    }

    /**
     * @param dateAchat the dateAchat to set
     */
    public void setDateAchat(Date dateAchat) {
        this.dateAchat = dateAchat;
    }


}