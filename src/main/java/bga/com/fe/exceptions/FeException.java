package bga.com.fe.exceptions;

/**
 *
 * @author bgarita, 10/12/2022
 */
public class FeException extends RuntimeException {

    public FeException(String codeClass, String classMethod, String errorMessage) {
        super("Ocurrió un error en la clase [" + codeClass + "], en el método [" + classMethod + "]"
                + "\nMsg: " + errorMessage);
    }
    
    public FeException(String codeClass, String classMethod, String errorMessage, Throwable ex) {
        super("Ocurrió un error en la clase [" + codeClass + "], en el método [" + classMethod + "]"
                + "\nMsg: " + errorMessage, ex);
    }
} // end class
