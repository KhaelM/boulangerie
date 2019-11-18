/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.michael.randrianarisona.boulangerie.controller;

import fr.michael.randrianarisona.boulangerie.dao.DAOComposanteSimulation;
import fr.michael.randrianarisona.boulangerie.dao.DAOFactory;
import fr.michael.randrianarisona.boulangerie.dao.DAOProduitFini;
import fr.michael.randrianarisona.boulangerie.init.DAOInit;
import fr.michael.randrianarisona.boulangerie.model.ComposanteSimulation;
import fr.michael.randrianarisona.boulangerie.model.ProduitFini;
import fr.michael.randrianarisona.boulangerie.page.ProjectionPage;
import fr.michael.randrianarisona.boulangerie.service.ServiceProjection;
import fr.michael.randrianarisona.boulangerie.service.ServiceSimulation;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author miker
 */
public class Possible extends BaseServlet {
    private final static long serialVersionUID = 1;

    private DAOComposanteSimulation daoComposanteSimulation;
    private DAOProduitFini daoProduitFini;
    
    private static final String ATTRIBUT_PROJECTION = "projection";

    @Override
    public void init() throws ServletException {
        this.daoComposanteSimulation = ((DAOFactory) this.getServletContext().getAttribute(DAOInit.ATT_DAO_FACTORY)).getDAOComposanteSimulation();
        this.daoProduitFini = ((DAOFactory) this.getServletContext().getAttribute(DAOInit.ATT_DAO_FACTORY)).getDAOProduitFini();
    }

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
        String produitFini = request.getParameter("produitFini");
        String quantiteVoulu = request.getParameter("quantite");
        ServiceProjection serviceProjection = new ServiceProjection(this.daoProduitFini);
        ServiceSimulation serviceSimulation = new ServiceSimulation(daoComposanteSimulation);
        List<ProduitFini> allProduitFini = serviceProjection.getAllProduitFini();
        
        ProjectionPage page = new ProjectionPage();
        page.setQuantiteVoulu(quantiteVoulu);
        page.setProduitsFini(allProduitFini);
        try {
            List<ComposanteSimulation> composantes = serviceSimulation.getComposantes(produitFini, quantiteVoulu);
            int produitsPossible = serviceProjection.getPossibilite(composantes, produitFini);
            page.setQuantiteVoulu(String.valueOf(produitsPossible));
            page.getSuccessMessages().add("Vous pouvez fabriquer au maximum " + produitsPossible + " " + produitFini + " au lieu de " + quantiteVoulu);
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
