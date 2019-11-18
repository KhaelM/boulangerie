/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.michael.randrianarisona.boulangerie.controller;

import fr.michael.randrianarisona.boulangerie.dao.DAOFactory;
import fr.michael.randrianarisona.boulangerie.dao.DAOProduitFini;
import fr.michael.randrianarisona.boulangerie.init.DAOInit;
import fr.michael.randrianarisona.boulangerie.model.ProduitFini;
import fr.michael.randrianarisona.boulangerie.page.ProjectionPage;
import fr.michael.randrianarisona.boulangerie.service.ServiceProjection;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author miker
 */
public class Projection extends BaseServlet {
    private final static long serialVersionUID = 1;

    private DAOProduitFini daoProduitFini;

    private static final String ATTRIBUT_PROJECTION = "projection";

    @Override
    public void init() throws ServletException {
        this.daoProduitFini = ((DAOFactory) this.getServletContext().getAttribute(DAOInit.ATT_DAO_FACTORY)).getDAOProduitFini();
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
        ServiceProjection serviceProjection = new ServiceProjection(this.daoProduitFini);
        List<ProduitFini> allProduitFini = serviceProjection.getAllProduitFini();
        ProjectionPage page = new ProjectionPage(allProduitFini, "");
        request.setAttribute(ATTRIBUT_PROJECTION, page);
        request.setAttribute(ATTRIBUT_TITRE, "Projection");
        request.setAttribute(ATTRIBUT_CONTENU, "WEB-INF/view/projection.jsp");
        this.getServletContext().getRequestDispatcher(vue).forward(request, response);
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
        String action = request.getParameter("action");
        switch (action) {
            case "simuler":
                this.getServletContext().getRequestDispatcher("/simulation").forward(request, response);
                break;
            case "enregistrer":
                this.getServletContext().getRequestDispatcher("/analyse").forward(request, response);
                break;
            case "possible":
                this.getServletContext().getRequestDispatcher("/possible").forward(request, response);
                break;
            case "valider_fabrication":
                this.getServletContext().getRequestDispatcher("/valider").forward(request, response);
                break;
            case "generer":
                this.getServletContext().getRequestDispatcher("/genererBonDeCommande").forward(request, response);
                break;
            default:
                break;
        }
    }
}
