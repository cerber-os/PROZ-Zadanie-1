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

public class Controller {
    @FXML
    private TextField result;

    final private Expression exp = new Expression((header, content) -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Expression error");
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        });

    public void btnBoard_click(Event event) {
        Button btn = (Button) event.getSource();
        result.setText(exp.digit(btn.getText()));
    }

    public void btnClear_click() {
        result.setText(exp.clear());
    }

    public void btnEquals_clear() {
        result.setText(exp.equals());
    }

    public void btnComma_click() {
        result.setText(exp.comma());
    }

    public void btnExFunc_click(Event event) {
        Button btn = (Button) event.getSource();
        result.setText(exp.exFunc(btn.getText().toLowerCase()));
    }

    public void btnOperator_click(Event event) {
        Button btn = (Button) event.getSource();
        result.setText(exp.operator(btn.getText()));
    }

    public void scene_keyType(Event event) {
        KeyEvent input = (KeyEvent)event;
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
        if(chr.charAt(0) >= '0' && chr.charAt(0) <= '9') {
            Button btn = new Button();
            btn.setText(chr);
            Event evt = new Event(btn, null, EventType.ROOT);
            btnBoard_click(evt);
        }
    }
}
