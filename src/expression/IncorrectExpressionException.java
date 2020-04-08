package expression;

/**
 * Exception thrown by JShellApi in case of error during expression execution
 */
public class IncorrectExpressionException extends Exception {
    public IncorrectExpressionException(String e) {
        super(e);
    }
}