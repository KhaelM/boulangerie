package fr.michael.randrianarisona.boulangerie.service.exception;

/**
 * ConversionException
 */
public class ConversionException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ConversionException(String attribut) {
        super("Format incorrect de "+ attribut+ ".");
    }
}