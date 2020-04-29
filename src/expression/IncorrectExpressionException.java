package expression;

import java.util.Map;

/**
 * Exception thrown by JShellApi in case of error during expression execution
 */
@SuppressWarnings("serial")
public class IncorrectExpressionException extends Exception {
    /**
     * Map storing exception messages to be converted
     */
    static final private Map<String, String> exceptionsLocale = Map.of(
            "/ by zero", "Attempted to divide by zero");

    public IncorrectExpressionException(String e) {
        super(e);
    }

    /**
     * Convert exception text to more user-friendly form
     * @return converted exception message
     */
    @Override
    public String getMessage() {
        String exceptionReason = exceptionsLocale.get(super.getMessage());
        if (exceptionReason != null)
            return exceptionReason;
        else
            return super.getMessage();
    }
}