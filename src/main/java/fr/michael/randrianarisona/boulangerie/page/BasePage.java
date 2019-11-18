/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.michael.randrianarisona.boulangerie.page;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author miker
 */
public class BasePage {
    List<String> errorMessages;
    List<String> successMessages;

    public BasePage() {
        this.errorMessages = new ArrayList<String>();
        this.successMessages = new ArrayList<String>();
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public void setSuccessMessages(List<String> successMessages) {
        this.successMessages = successMessages;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public List<String> getSuccessMessages() {
        return successMessages;
    }
}
