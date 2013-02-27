package ca.ulaval.glo4002.adt.domain;

public class EntiteInvalidException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    public EntiteInvalidException(String message) {
        super(message);
    }

}
