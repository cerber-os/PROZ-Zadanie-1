package calculator;

import expression.Expression;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.util.stream.Stream;

/**
 * Class implementing application controller
 */
public class Controller {
    /**
     * Instance of expression class used to perform all arithmetical operations
     * Provided lambda expression is invoked in case of an error in user provided query
     */
    final private Expression exp = new Expression((header, content) -> {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Expression error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    });

    /**
     * Reference to textfield storing operations results
     */
    @FXML
    private TextField result;

    /**
     * Callback for numeric keys
     */
    public void btnBoard_click(Event event) {
        Button btn = (Button) event.getSource();
        result.setText(exp.digit(btn.getText()));
    }

    /**
     * Callback for AC key
     */
    public void btnClear_click() {
        result.setText(exp.clear());
    }

    /**
     * Callback for '=' key
     */
    public void btnEquals_clear() {
        result.setText(exp.equals());
    }

    /**
     * Callback for ',' key
     */
    public void btnComma_click() {
        result.setText(exp.comma());
    }

    /**
     * Callback for extra functions keys (ex. sqrt)
     */
    public void btnExFunc_click(Event event) {
        Button btn = (Button) event.getSource();
        result.setText(exp.exFunc(btn.getText().toLowerCase()));
    }

    /**
     * Callback for operators keys (ex. +, -)
     */
    public void btnOperator_click(Event event) {
        Button btn = (Button) event.getSource();
        result.setText(exp.operator(btn.getText()));
    }

    /**
     * Callback for keyboard key pressed
     * <br>Used to allow user to enter values directly from keyboard
     */
    public void scene_keyType(Event event) {
        KeyEvent input = (KeyEvent) event;
        String chr = input.getCharacter();

        // Handle operators
        Stream.of('+', '-', '*', '/').filter(e -> e == chr.charAt(0)).forEach(e -> {
            Button btn = new Button();
            btn.setText(e.toString());
            Event evt = new Event(btn, null, EventType.ROOT);
            btnOperator_click(evt);
        });

        // Delete and backspace
        Stream.of(127, 8).filter(e -> e == chr.charAt(0)).forEach(e -> btnClear_click());

        // Different commas
        Stream.of('.', ',').filter(e -> e == chr.charAt(0)).forEach(e -> btnComma_click());

        // Digits
        if (chr.charAt(0) >= '0' && chr.charAt(0) <= '9') {
            Button btn = new Button();
            btn.setText(chr);
            Event evt = new Event(btn, null, EventType.ROOT);
            btnBoard_click(evt);
        }
    }
}
