/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.michael.randrianarisona.boulangerie.controller;

import fr.michael.randrianarisona.boulangerie.dao.DAOComposanteSimulation;
import fr.michael.randrianarisona.boulangerie.dao.DAOFactory;
import fr.michael.randrianarisona.boulangerie.dao.DAOProduitFini;
import fr.michael.randrianarisona.boulangerie.exception.DoitEtreRenseigneException;
import fr.michael.randrianarisona.boulangerie.init.DAOInit;
import fr.michael.randrianarisona.boulangerie.model.ComposanteSimulation;
import fr.michael.randrianarisona.boulangerie.model.ProduitFini;
import fr.michael.randrianarisona.boulangerie.model.exception.NegatifException;
import fr.michael.randrianarisona.boulangerie.model.exception.PasDeRecetteException;
import fr.michael.randrianarisona.boulangerie.page.SimulationPage;
import fr.michael.randrianarisona.boulangerie.service.ServiceSimulation;
import fr.michael.randrianarisona.boulangerie.service.exception.ConversionException;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author miker
 */
public class Simulation extends BaseServlet {
    private final static long serialVersionUID = 1;

    private DAOComposanteSimulation daoComposanteSimulation;
    private DAOProduitFini daoProduitFini;

    private static final String ATTRIBUT_SIMULATION = "simulation";

    @Override
    public void init() throws ServletException {
        this.daoComposanteSimulation = ((DAOFactory) this.getServletContext().getAttribute(DAOInit.ATT_DAO_FACTORY))
                .getDAOComposanteSimulation();
        this.daoProduitFini = ((DAOFactory) this.getServletContext().getAttribute(DAOInit.ATT_DAO_FACTORY))
                .getDAOProduitFini();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String quantiteVoulu = request.getParameter("quantite");
        SimulationPage page = new SimulationPage();
        ServiceSimulation serviceSimulation = new ServiceSimulation(daoComposanteSimulation, daoProduitFini);

        ProduitFini produitFini = serviceSimulation.getProduitFini(request.getParameter("produitFini"));
        page.setProduitFini(produitFini);
        try {
            page.setQuantiteVoulu(quantiteVoulu);
            List<ComposanteSimulation> composantes = serviceSimulation.getComposantes(produitFini.getNom(),
                    quantiteVoulu);
            page.setComposantes(composantes);
            if (composantes.isEmpty()) {
                throw new PasDeRecetteException(produitFini.getNom());
            }
        } catch(NegatifException | ConversionException | PasDeRecetteException | DoitEtreRenseigneException e) {
            page.getErrorMessages().add(e.getMessage());
        }

        try {
            page.setDateFabrication(request.getParameter("date_fabrication"));
        } catch (DoitEtreRenseigneException | ConversionException e) {
            page.getErrorMessages().add(e.getMessage());
        }

        request.setAttribute(ATTRIBUT_SIMULATION, page);
        request.setAttribute(ATTRIBUT_ERREUR, page.getErrorMessages());
        request.setAttribute(ATTRIBUT_CONTENU, "WEB-INF/view/simulation.jsp");
        request.setAttribute(ATTRIBUT_TITRE, "Simulation");
        this.getServletContext().getRequestDispatcher(vue).forward(request, response);
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
