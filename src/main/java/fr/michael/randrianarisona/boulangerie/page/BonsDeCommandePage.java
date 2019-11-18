package fr.michael.randrianarisona.boulangerie.page;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import fr.michael.randrianarisona.boulangerie.exception.DoitEtreRenseigneException;
import fr.michael.randrianarisona.boulangerie.model.BonDeCommande;
import fr.michael.randrianarisona.boulangerie.service.exception.ConversionException;

/**
 * BonsDeCommandePage
 */
public class BonsDeCommandePage extends BasePage {

    private List<BonDeCommande> bonsDeCommande;
    private Date dateCommande;

    /**
     * @return the dateCommande
     */
    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(String dateCommande) throws DoitEtreRenseigneException, ConversionException {
        try {
            if(dateCommande.isEmpty()) {
                throw new DoitEtreRenseigneException("La date");
            }
            this.dateCommande = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dateCommande).getTime());
        } catch (ParseException e) {
            this.dateCommande = new Date(0);
            throw new ConversionException("la date");
        }
    }

    /**
     * @param bonsDeCommande the bonsDeCommande to set
     */
    public void setBonsDeCommande(List<BonDeCommande> bonsDeCommande) {
        this.bonsDeCommande = bonsDeCommande;
    }
    /**
     * @return the bonsDeCommande
     */
    public List<BonDeCommande> getBonsDeCommande() {
        return bonsDeCommande;
    }
}