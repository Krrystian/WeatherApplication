module weatherapp.weatherapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.google.gson;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    opens weatherapp.weatherapp to javafx.fxml;
    exports weatherapp.weatherapp;
}