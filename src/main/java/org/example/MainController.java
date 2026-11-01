package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.ResourceBundle;

public class MainController {

    @FXML private ResourceBundle resources;

    @FXML private TextField distanceField;
    @FXML private TextField speedField;
    @FXML private Label resultLabel;

    @FXML private TextField tempInput;
    @FXML private Label tempResult;

    private TemperatureConverter converter = new TemperatureConverter();

    @FXML
    public void calculateTravel() {
        try {
            double distance = Double.parseDouble(distanceField.getText());
            double speed = Double.parseDouble(speedField.getText());

            if (!Double.isFinite(distance) || !Double.isFinite(speed) || distance < 0 || speed <= 0) {
                throw new IllegalArgumentException();
            }

            double time = distance / speed;
            resultLabel.setText(String.format("%s %.2f", resources.getString("travel.time"), time));

            TempRecord record = new TempRecord(distance, speed, time, 1);
            TempRecordDAO dao = new TempRecordDAO();
            dao.save(record);

        } catch (IllegalArgumentException ex) {
            resultLabel.setText(resources.getString("travel.error"));
        }
    }

    @FXML
    public void convertCToF() {
        try {
            double celsius = Double.parseDouble(tempInput.getText());
            double result = converter.celsiusToFahrenheit(celsius);
            tempResult.setText(String.format("%s %.2f °F", resources.getString("temperature.result"), result));
        } catch (NumberFormatException ex) {
            tempResult.setText(resources.getString("temperature.error"));
        }
    }

    @FXML
    public void convertFToC() {
        try {
            double fahrenheit = Double.parseDouble(tempInput.getText());
            double result = converter.fahrenheitToCelsius(fahrenheit);
            tempResult.setText(String.format("%s %.2f °C", resources.getString("temperature.result"), result));
        } catch (NumberFormatException ex) {
            tempResult.setText(resources.getString("temperature.error"));
        }
    }

    @FXML
    public void convertKToC() {
        try {
            double kelvin = Double.parseDouble(tempInput.getText());
            double result = converter.kelvinToCelsius(kelvin);
            tempResult.setText(String.format("%s %.2f °C", resources.getString("temperature.result"), result));
        } catch (NumberFormatException ex) {
            tempResult.setText(resources.getString("temperature.error"));
        }
    }
}