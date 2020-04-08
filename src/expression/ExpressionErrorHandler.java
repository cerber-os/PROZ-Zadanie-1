package expression;

/**
 * Interface specifying format of handler called in case of expression parsing error
 */
@FunctionalInterface
public interface ExpressionErrorHandler {
    void handle(String header, String content);
}
