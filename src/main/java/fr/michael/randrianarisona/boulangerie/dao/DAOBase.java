/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.michael.randrianarisona.boulangerie.dao;

/**
 *
 * @author miker
 */
public class DAOBase {
    protected DAOFactory daoFactory;

    public DAOBase(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
}
