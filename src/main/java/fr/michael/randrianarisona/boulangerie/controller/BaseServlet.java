/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.michael.randrianarisona.boulangerie.controller;

import javax.servlet.http.HttpServlet;

/**
 *
 * @author miker
 */
public class BaseServlet extends HttpServlet {
    private final static long serialVersionUID = 1;

    protected final String ATTRIBUT_CONTENU = "contenu";
    protected final String ATTRIBUT_TITRE = "title";
    protected final String ATTRIBUT_ERREUR = "erreur";
    protected final String ATTRIBUT_SUCCES = "succes";
    protected final String ATTRIBUT_PAGE = "page";

    protected String vue = "/master.jsp";
}
