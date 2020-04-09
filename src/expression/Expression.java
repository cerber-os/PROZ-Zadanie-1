package expression;

/**
 * Class used for generating arithmetical expression based on user input
 */
public class Expression {
    private final ExpressionErrorHandler handler;
    private String firstArgument = "";
    private String secondArgument = "";
    private String operator = "";
    private boolean clearOnDigit = false;

    public Expression(ExpressionErrorHandler h) {
        handler = h;
    }

    /**
     * Evaluate expresion entered by user
     */
    public void compute() {
        try {
            firstArgument = JShellApi.executeTwoArgsExpr(firstArgument, operator, secondArgument);
            operator = secondArgument = "";
        } catch (IncorrectExpressionException e) {
            handler.handle("Expression contains error", e.getMessage());
            clear();
        }
    }

    /**
     * Check whether result field stores user input or expression value; in case the second, clear result field on digit press
     * <br> This method is used to decide whether the result field should be cleared after user action
     * @param clearIfExists pressed key should cause result field to be cleared
     */
    private void checkResult(boolean clearIfExists) {
        if (clearOnDigit) {
            clearOnDigit = false;
            if (clearIfExists)
                firstArgument = "";
        }
    }

    /**
     * Function called when user expects the result of provided expression
     * @return the value of computed expression
     */
    public String equals() {
        compute();
        clearOnDigit = true;
        return firstArgument;
    }

    /**
     * Reset state of calculator
     * @return value to be shown after resetting calculator
     */
    public String clear() {
        firstArgument = secondArgument = operator = "";
        return "0";
    }

    /**
     * Handle new digit
     * @param digit number pressed by user
     * @return the new value of result box
     */
    public String digit(String digit) {
        // Clear result box if it contains result of the previous expression
        checkResult(true);
        if (operator.equals("")) {
            if (firstArgument.equals("") && digit.equals("0"))
                return "0";
            else
                return firstArgument += digit;
        } else {
            if (secondArgument.equals("") && digit.equals("0"))
                return "0";
            else
                return secondArgument += digit;
        }
    }

    /**
     * Handle operator
     * <br> if operator was already selected, second press caused the previous operations to be computed
     * @param op operator pressed by user
     * @return the new value of result box
     */
    public String operator(String op) {
        checkResult(false);
        if (!secondArgument.equals("")) {
            compute();
        }
        operator = op;
        if (firstArgument.equals(""))
            firstArgument = "0";
        return firstArgument;
    }

    /**
     * Handle comma key
     * <br> Alse, prevent user from entering more than one comma in number
     * @return the new value of result box
     */
    public String comma() {
        checkResult(true);
        if (operator.equals("")) {
            if (firstArgument.equals(""))
                return firstArgument = "0.";
            else if (firstArgument.contains("."))
                return firstArgument;
            else
                return firstArgument += ".";
        } else {
            if (secondArgument.equals(""))
                return secondArgument = "0.";
            else if (secondArgument.contains("."))
                return secondArgument;
            else
                return secondArgument += ".";
        }
    }

    /**
     * Handle extra functions (like: sqrt)
     * @param funcName id of function to be called
     * @return the new value of result box
     */
    public String exFunc(String funcName) {
        checkResult(false);
        if (!secondArgument.equals("")) {
            compute();
        }
        try {
            firstArgument = JShellApi.executeOneArgExpr(firstArgument, funcName);
            operator = secondArgument = "";
            clearOnDigit = true;
        } catch (IncorrectExpressionException e) {
            handler.handle("Expression contains error", e.getMessage());
            clear();
            return "0";
        }
        return firstArgument;
    }
}
