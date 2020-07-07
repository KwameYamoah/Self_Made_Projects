module PaintApplication {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.desktop;
    requires junit;
    opens main;
    opens Exceptions;
}