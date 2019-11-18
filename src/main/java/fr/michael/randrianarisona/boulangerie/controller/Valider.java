/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.michael.randrianarisona.boulangerie.controller;

import fr.michael.randrianarisona.boulangerie.dao.DAOComposanteSimulation;
import fr.michael.randrianarisona.boulangerie.dao.DAOFactory;
import fr.michael.randrianarisona.boulangerie.dao.DAOProduitFini;
import fr.michael.randrianarisona.boulangerie.dao.DAOValider;
import fr.michael.randrianarisona.boulangerie.init.DAOInit;
import fr.michael.randrianarisona.boulangerie.model.ComposanteSimulation;
import fr.michael.randrianarisona.boulangerie.model.ProduitFini;
import fr.michael.randrianarisona.boulangerie.page.ProjectionPage;
import fr.michael.randrianarisona.boulangerie.service.ServiceProjection;
import fr.michael.randrianarisona.boulangerie.service.ServiceSimulation;
import fr.michael.randrianarisona.boulangerie.service.ServiceValider;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author miker
 */
public class Valider extends BaseServlet {
    private final static long serialVersionUID = 1;

    private DAOProduitFini daoProduitFini;
    private DAOValider daoValider;
    private DAOComposanteSimulation daoComposanteSimulation;

    @Override
    public void init() throws ServletException {
        this.daoComposanteSimulation = ((DAOFactory) this.getServletContext().getAttribute(DAOInit.ATT_DAO_FACTORY)).getDAOComposanteSimulation();
        this.daoProduitFini = ((DAOFactory) this.getServletContext().getAttribute(DAOInit.ATT_DAO_FACTORY)).getDAOProduitFini();
        this.daoValider = ((DAOFactory) this.getServletContext().getAttribute(DAOInit.ATT_DAO_FACTORY)).getDAOValider();
    }
    
    private static final String ATTRIBUT_PROJECTION = "projection";

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String quantite = request.getParameter("quantite");
        String date = request.getParameter("date_fabrication");
        ProjectionPage page = new ProjectionPage();
        
        ServiceProjection serviceProjection = new ServiceProjection(this.daoProduitFini);
        List<ProduitFini> allProduitFini = serviceProjection.getAllProduitFini();
        page.setProduitsFini(allProduitFini);
        
        ServiceValider serviceValider = new ServiceValider();
        serviceValider.setDaoValider(daoValider);
        
        page.setQuantiteVoulu(quantite);
        
        ServiceSimulation serviceSimulation = new ServiceSimulation(daoComposanteSimulation);
        serviceSimulation.setDaoProduitFini(daoProduitFini);
        ProduitFini produitFini = serviceSimulation.getProduitFini(request.getParameter("produitFini"));
        
        try {
            if(date == null || date.isEmpty()) {
                throw new Exception("La date doit être renseigné pour la fabrication.");
            }
            Date dateFabrication = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("date_fabrication")).getTime());
            List<ComposanteSimulation> composantes = serviceSimulation.getComposantes(produitFini.getNom(), quantite);
            
            try {
                float quantiteVoulu = Float.parseFloat(quantite);
                serviceValider.Valider(produitFini, composantes, dateFabrication, quantiteVoulu);
            } catch(NumberFormatException e) {
                throw new Exception("Le format de la quantité voulu est incorrect.");
            }
            page.getSuccessMessages().add("Fabrication enregistrée.");
        } catch(Exception e) {
            page.getErrorMessages().add(e.getMessage());
        } finally {
            request.setAttribute(ATTRIBUT_ERREUR, page.getErrorMessages());
            request.setAttribute(ATTRIBUT_SUCCES, page.getSuccessMessages());
            request.setAttribute(ATTRIBUT_PROJECTION, page);
            request.setAttribute(ATTRIBUT_TITRE, "Projection");
            request.setAttribute(ATTRIBUT_CONTENU, "WEB-INF/view/projection.jsp");
            this.getServletContext().getRequestDispatcher(vue).forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
