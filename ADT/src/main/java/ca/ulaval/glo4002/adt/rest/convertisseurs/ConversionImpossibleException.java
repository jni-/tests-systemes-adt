package ca.ulaval.glo4002.adt.rest.convertisseurs;

public class ConversionImpossibleException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ConversionImpossibleException(String message) {
        super(message);
    }

}
