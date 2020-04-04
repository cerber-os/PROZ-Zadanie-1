package expression;

@FunctionalInterface
public interface ExpressionErrorHandler {
    void handle(String header, String content);
}
