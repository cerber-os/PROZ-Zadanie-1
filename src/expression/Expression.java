package expression;

public class Expression {
    private final ExpressionErrorHandler handler;
    private String firstArgument = "";
    private String secondArgument = "";
    private String operator = "";
    private boolean clearOnDigit = false;

    public Expression(ExpressionErrorHandler h) {
        handler = h;
    }

    public void compute() {
        try {
            firstArgument = JShellApi.executeTwoArgsExpr(firstArgument, operator, secondArgument);
            operator = secondArgument = "";
        } catch(IncorrectExpressionException e) {
            handler.handle("Expression contains error", e.toString());
            clear();
        }
    }

    private void checkResult(boolean clearIfExists) {
        if(clearOnDigit) {
            clearOnDigit = false;
            if(clearIfExists)
                firstArgument = "";
        }
    }

    public String equals() {
        compute();
        clearOnDigit = true;
        return firstArgument;
    }

    public String clear() {
        firstArgument = secondArgument = operator = "";
        return "0";
    }

    public String digit(String digit) {
        checkResult(true);
        if(operator.equals("")) {
            if(firstArgument.equals("") && digit.equals("0"))
                return "0";
            else
                return firstArgument += digit;
        } else {
            if(secondArgument.equals("") && digit.equals("0"))
                return "0";
            else
                return secondArgument += digit;
        }
    }

    public String operator(String op) {
        checkResult(false);
        if (!secondArgument.equals("")) {
            compute();
        }
        operator = op;
        if(firstArgument.equals(""))
            firstArgument = "0";
        return firstArgument;
    }

    public String comma() {
        checkResult(true);
        if(operator.equals("")) {
            if(firstArgument.equals(""))
                return firstArgument = "0.";
            else if(firstArgument.contains("."))
                return firstArgument;
            else
                return firstArgument += ".";
        } else {
            if(secondArgument.equals(""))
                return secondArgument = "0.";
            else if(secondArgument.contains("."))
                return secondArgument;
            else
                return secondArgument += ".";
        }
    }

    public String exFunc(String funcName) {
        checkResult(false);
        if (!secondArgument.equals("")) {
            compute();
        }
        try {
            firstArgument = JShellApi.executeOneArgExpr(firstArgument, funcName);
            operator = secondArgument = "";
            clearOnDigit = true;
        } catch(IncorrectExpressionException e) {
            handler.handle("Expression contains error", e.toString());
            clear();
            return "0";
        }
        return firstArgument;
    }
}
