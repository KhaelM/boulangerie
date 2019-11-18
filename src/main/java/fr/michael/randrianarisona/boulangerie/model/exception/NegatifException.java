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
public class NegatifException extends Exception {
    private static final long serialVersionUID = 1;

    public NegatifException(String attribut) {
        super(attribut + " ne peut avoir une valeur négative.");
    }
    
}
