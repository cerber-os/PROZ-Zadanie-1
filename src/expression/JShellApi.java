package expression;

import jdk.jshell.JShell;
import jdk.jshell.SnippetEvent;

import java.util.List;
import java.util.Map;

import static jdk.jshell.Snippet.Status.VALID;

/**
 * JShell API frontend
 */
public class JShellApi {
    /**
     * Map translating functions IDs to their corresponding names in standard Java Math library
     */
    static final private Map<String, String> funcsConversion = Map.of(
            "sqrt", "Math.sqrt",
            "cbrt", "Math.cbrt",
            "exp", "Math.exp");

    /**
     * Execute provided arithmetical expression via JShellApi
     *
     * @param exp valid expression to be executed; empty value is treated as zero
     * @return the numeral result of operation
     * @throws IncorrectExpressionException It is thrown when provided expression either caused exception in JShellApi itself or returned non-numeric result
     */
    static private String executeExpression(String exp) throws IncorrectExpressionException {
        String result = "";

        // Validate input expression
        if (exp.equals(""))
            exp = "0";

        // Execute expression in JShell
        try (JShell jShell = JShell.create()) {
            List<SnippetEvent> events = jShell.eval(exp);
            for (SnippetEvent e : events) {
                if (e.causeSnippet() == null) {
                    if (e.status() == VALID && e.value() != null) {
                        result = e.value();
                    } else {
                        if (e.exception() == null)
                            throw new IncorrectExpressionException("Provided expression contains unknown error");
                        else
                            throw new IncorrectExpressionException(e.exception().toString());
                    }
                }
            }
        }

        // Check if result is not empty
        if (result == null || result.equals(""))
            throw new IncorrectExpressionException("Provided expression contains unknown error");

        if (result.contains("Infinity"))
            throw new IncorrectExpressionException("Result of provided expressions exceeds calculation range");

        if (result.contains("NaN"))
            throw new IncorrectExpressionException("Expression contains invalid argument");

        return result;
    }

    /**
     * Convert input argument to valid form for JShell
     * <br> It is used to make sure that all input number for API are integers - otherwise, result of some operations could be invalid (for instance: (int) 5 / (int) 2 == 2)
     * @param arg string representing input number
     * @return argument suitable to use with executeExpression method
     */
    static private String parseArgument(String arg) {
        if (arg.equals(""))
            arg = "0";
        else if (!arg.equals("0") && !arg.contains("."))
            arg += ".0";
        return arg;
    }

    /**
     * Convert result of JShell expression to user friendly form
     * <br> In particular: remove unnecessary precision, negative zero.
     * @param result string containing JShell API result
     * @return user friendly form of provided argument
     */
    static private String parseResult(String result) {
        if (result.endsWith(".0"))
            result = result.substring(0, result.length() - 2);
        if (result.equals("-0"))
            result = "0";
        System.out.println(" > \"" + result + "\"");
        return result;
    }

    /**
     * Execute expression of form func(arg)
     * @param arg string representing function argument
     * @param func id of function to be invoked
     * @return value of provided expression
     * @throws IncorrectExpressionException It is thrown in case of computation errors
     */
    static public String executeOneArgExpr(String arg, String func) throws IncorrectExpressionException {
        arg = parseArgument(arg);

        String expr = funcsConversion.get(func) + "(" + arg + ");";
        System.out.println(expr);

        String result = executeExpression(expr);
        return parseResult(result);
    }

    /**
     * Execute expression of form 1st_arg operator second_arg
     * @param first
     * @param operator
     * @param second
     * @return value of provided expression
     * @throws IncorrectExpressionException It is thrown in case of computation errors
     */
    static public String executeTwoArgsExpr(String first, String operator, String second) throws IncorrectExpressionException {
        first = parseArgument(first);
        second = parseArgument(second);

        String expr = first + operator + second;
        System.out.println(expr);

        String result = executeExpression(expr);
        return parseResult(result);
    }
}
