module Calculator {
    requires transitive javafx.base;
    requires transitive javafx.fxml;
    requires transitive javafx.controls;
    requires jdk.jshell;
    exports calculator;
    opens calculator to javafx.fxml;
}