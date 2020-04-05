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
}
