package fr.michael.randrianarisona.boulangerie.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.michael.randrianarisona.boulangerie.dao.DAOBonDeCommande;
import fr.michael.randrianarisona.boulangerie.dao.DAOComposanteSimulation;
import fr.michael.randrianarisona.boulangerie.dao.DAOFactory;
import fr.michael.randrianarisona.boulangerie.exception.DoitEtreRenseigneException;
import fr.michael.randrianarisona.boulangerie.init.DAOInit;
import fr.michael.randrianarisona.boulangerie.model.ComposanteSimulation;
import fr.michael.randrianarisona.boulangerie.model.exception.NegatifException;
import fr.michael.randrianarisona.boulangerie.page.BonsDeCommandePage;
import fr.michael.randrianarisona.boulangerie.service.ServiceBonDeCommande;
import fr.michael.randrianarisona.boulangerie.service.ServiceSimulation;
import fr.michael.randrianarisona.boulangerie.service.exception.ConversionException;

/**
 * GenererBonDeCommande
 */
public class GenererBonDeCommande extends BaseServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private DAOComposanteSimulation daoComposanteSimulation;
    private DAOBonDeCommande daoBonDeCommande;

    @Override
    public void init() throws ServletException {
        this.daoComposanteSimulation = ((DAOFactory) this.getServletContext().getAttribute(DAOInit.ATT_DAO_FACTORY)).getDAOComposanteSimulation();
        this.daoBonDeCommande = ((DAOFactory) this.getServletContext().getAttribute(DAOInit.ATT_DAO_FACTORY)).getDAOBonDeCommande();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceSimulation serviceSimulation = new ServiceSimulation(daoComposanteSimulation);
        ServiceBonDeCommande serviceBonDeCommande = new ServiceBonDeCommande();
        serviceBonDeCommande.setDaoBonDeCommande(daoBonDeCommande);
        
        String quantiteVoulu = request.getParameter("quantite");

        BonsDeCommandePage page = new BonsDeCommandePage();

        try {
            List<ComposanteSimulation> composantes = serviceSimulation.getComposantes(request.getParameter("produitFini"), quantiteVoulu);            
            page.setBonsDeCommande(serviceBonDeCommande.genererBonsDeCommande(composantes));
            page.setDateCommande(request.getParameter("date_fabrication"));
            serviceBonDeCommande.insertBonsDeCommande(page.getBonsDeCommande(), page.getDateCommande());
        } catch (NegatifException | ConversionException | DoitEtreRenseigneException e) {
            page.getErrorMessages().add(e.getMessage());
        } 

        try {
            page.setDateCommande(request.getParameter("date_fabrication"));
        } catch (DoitEtreRenseigneException | ConversionException e) {
            page.getErrorMessages().add(e.getMessage());
        }
        request.setAttribute(ATTRIBUT_ERREUR, page.getErrorMessages());
        request.setAttribute(ATTRIBUT_PAGE, page); 
        request.setAttribute(ATTRIBUT_CONTENU, "WEB-INF/view/bonsDeCommande.jsp");
        request.setAttribute(ATTRIBUT_TITRE, "Bons de commande");
        this.getServletContext().getRequestDispatcher(vue).forward(request, response);
    }
}