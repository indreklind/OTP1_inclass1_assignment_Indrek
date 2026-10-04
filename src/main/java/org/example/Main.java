package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        DBConnection.createTables();

        // Travel calculator
        TextField distanceField = new TextField();
        distanceField.setPromptText("Distance");

        TextField speedField = new TextField();
        speedField.setPromptText("Speed");

        Label resultLabel = new Label("Time: ");

        Button calculateButton = new Button("Calculate");

        calculateButton.setOnAction(e -> {
            double distance = Double.parseDouble(distanceField.getText());
            double speed = Double.parseDouble(speedField.getText());

            double time = distance / speed;

            resultLabel.setText("Time: " + time);

            TempRecord record = new TempRecord(
                    distance,
                    speed,
                    time,
                    1
            );

            TempRecordDAO dao = new TempRecordDAO();
            dao.save(record);
        });

        // Temperature converter
        Label tempTitle = new Label("Temperature Converter");

        TextField tempInput = new TextField();
        tempInput.setPromptText("Enter temperature");

        Button cToFButton = new Button("Celsius to Fahrenheit");
        Button fToCButton = new Button("Fahrenheit to Celsius");
        Button kToCButton = new Button("Kelvin to Celsius");

        Label tempResult = new Label("Result: ");

        TemperatureConverter converter = new TemperatureConverter();

        cToFButton.setOnAction(e -> {
            double celsius = Double.parseDouble(tempInput.getText());
            double result = converter.celsiusToFahrenheit(celsius);
            tempResult.setText("Result: " + result + " °F");
        });

        fToCButton.setOnAction(e -> {
            double fahrenheit = Double.parseDouble(tempInput.getText());
            double result = converter.fahrenheitToCelsius(fahrenheit);
            tempResult.setText(String.format("Result: %.2f °C", result));
        });

        kToCButton.setOnAction(e -> {
            try {
                double kelvin = Double.parseDouble(tempInput.getText());
                double result = converter.kelvinToCelsius(kelvin);
                tempResult.setText("Result: " + result + " °C");
            } catch (NumberFormatException ex) {
                tempResult.setText("Please enter a valid number.");
            }
        });

        VBox root = new VBox(
                10,
                new Label("Travel Time Calculator"),
                distanceField,
                speedField,
                calculateButton,
                resultLabel,

                tempTitle,
                tempInput,
                cToFButton,
                fToCButton,
                kToCButton,
                tempResult
        );

        root.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(root, 400, 400);

        stage.setTitle("Temperature & Travel Calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}