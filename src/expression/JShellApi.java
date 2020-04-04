package expression;

import jdk.jshell.JShell;
import jdk.jshell.SnippetEvent;

import java.util.List;
import java.util.Map;

import static jdk.jshell.Snippet.Status.VALID;

public class JShellApi {
    static final private Map<String, String> funcsConversion = Map.of(
            "sqrt", "Math.sqrt",
            "cbrt", "Math.cbrt",
            "exp", "Math.exp");

    static private String executeExpression(String exp) throws IncorrectExpressionException {
        String result = "";

        // Validate input expression
        if(exp.equals(""))
            exp = "0";

        try(JShell jShell = JShell.create()){
            List<SnippetEvent> events = jShell.eval(exp);
            for (SnippetEvent e : events) {
                if (e.causeSnippet() == null) {
                    if (e.status() == VALID && e.value() != null) {
                        result = e.value();
                    } else {
                        if(e.exception() == null)
                            throw new IncorrectExpressionException("Provided expression contains unknown error");
                        else
                            throw new IncorrectExpressionException(e.exception().toString());
                    }
                }
            }
        }

        if(result == null || result.equals(""))
            throw new IncorrectExpressionException("Provided expression contains unknown error");

        if(result.contains("Infinity"))
            throw new IncorrectExpressionException("Result of provided expressions exceeds calculation range");

        return result;
    }

    static private String parseArgument(String arg) {
        if(arg.equals(""))
            arg = "0";
        else if(!arg.equals("0") && !arg.contains("."))
            arg += ".0";
        return arg;
    }

    static private String parseResult(String result) throws IncorrectExpressionException {
        if(result.endsWith(".0"))
            result = result.substring(0, result.length()-2);
        if(result.equals("-0"))
            result = "0";
        if(result.contains("NaN"))
            throw new IncorrectExpressionException("Expression contains invalid argument");
        System.out.println(" > \"" + result + "\"");
        return result;
    }

    static public String executeOneArgExpr(String arg, String func) throws IncorrectExpressionException {
        arg = parseArgument(arg);

        String expr = funcsConversion.get(func) + "(" + arg + ");";
        System.out.println(expr);

        String result = executeExpression(expr);

        return parseResult(result);
    }

    static public String executeTwoArgsExpr(String first, String operator, String second) throws IncorrectExpressionException {
        first = parseArgument(first);
        second = parseArgument(second);

        String expr = first + operator + second;
        System.out.println(expr);

        String result = executeExpression(expr);
        return parseResult(result);
    }
}
