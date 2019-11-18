/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.michael.randrianarisona.boulangerie.model.exception;

/**
 *
 * @author miker
 */
public class PasDeRecetteException extends Exception {
    private static final long serialVersionUID = 1;

    public PasDeRecetteException(String produitFini) {
        super("Désolé, il n'y a pas encore de recette disponible pour le produit " + produitFini + ".");
    }
}
