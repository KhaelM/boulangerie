/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.michael.randrianarisona.boulangerie.controller;

import fr.michael.randrianarisona.boulangerie.dao.DAOComposanteSimulation;
import fr.michael.randrianarisona.boulangerie.dao.DAOFactory;
import fr.michael.randrianarisona.boulangerie.dao.DAOMachine;
import fr.michael.randrianarisona.boulangerie.dao.DAOProduitFini;
import fr.michael.randrianarisona.boulangerie.init.DAOInit;
import fr.michael.randrianarisona.boulangerie.model.ComposanteSimulation;
import fr.michael.randrianarisona.boulangerie.model.Machine;
import fr.michael.randrianarisona.boulangerie.model.exception.PasDeRecetteException;
import fr.michael.randrianarisona.boulangerie.page.AnalysePage;
import fr.michael.randrianarisona.boulangerie.service.ServiceAnalyse;
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
public class Analyse extends BaseServlet {
    private final static long serialVersionUID = 1L;

    private DAOComposanteSimulation daoComposanteSimulation;
    private DAOProduitFini daoProduitFini;
    private DAOMachine daoMachine;
    
    private static final String ATTRIBUT_ANALYSE = "analyse";

    @Override
    public void init() throws ServletException {
        this.daoComposanteSimulation = ((DAOFactory) this.getServletContext().getAttribute(DAOInit.ATT_DAO_FACTORY)).getDAOComposanteSimulation();
        this.daoProduitFini = ((DAOFactory) this.getServletContext().getAttribute(DAOInit.ATT_DAO_FACTORY)).getDAOProduitFini();
        this.daoMachine = ((DAOFactory) this.getServletContext().getAttribute(DAOInit.ATT_DAO_FACTORY)).getDAOMachine();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
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
        String quantiteVoulu = request.getParameter("quantite");
        AnalysePage page = new AnalysePage();
        ServiceSimulation serviceSimulation = new ServiceSimulation(daoComposanteSimulation, daoProduitFini);
        try {
            try {
                page.setQuantite(quantiteVoulu);
                List<ComposanteSimulation> composantes = serviceSimulation.getComposantes(request.getParameter("produitFini"), quantiteVoulu);
                page.setComposantes(composantes);
                if(composantes.isEmpty()) {
                    throw new PasDeRecetteException(page.getProduitFini().getNom());
                }
            } catch(Exception e) {
                page.getErrorMessages().add(e.getMessage());
            }
            
            try {
                page.setProduitFini(serviceSimulation.getProduitFini(request.getParameter("produitFini")));
                List<Machine> machines = new ServiceAnalyse(daoMachine).getMachines(page.getProduitFini().getNom());
                page.setMachines(machines);
            } catch(Exception e) {
                page.getErrorMessages().add(e.getMessage());
            }
            page.setDateFabrication(request.getParameter("date_fabrication"));
        } catch(Exception e) {
            page.getErrorMessages().add(e.getMessage());
        } finally {
            request.setAttribute(ATTRIBUT_ERREUR, page.getErrorMessages());
            request.setAttribute(ATTRIBUT_ANALYSE, page); 
            request.setAttribute(ATTRIBUT_CONTENU, "WEB-INF/view/analyse.jsp");
            request.setAttribute(ATTRIBUT_TITRE, "Analyse");
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
