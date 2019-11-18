package fr.michael.randrianarisona.boulangerie.exception;

/**
 * DoitEtreRenseigneException
 */
public class DoitEtreRenseigneException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DoitEtreRenseigneException(String attribut) {
        super(attribut + " doit être renseigné.");
    }
}