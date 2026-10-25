package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class Main extends Application {

    private ResourceBundle messages;

    @Override
    public void start(Stage stage) {

        // Step 1: Ask the user to choose a language
        List<String> languages = Arrays.asList(
                "English", "Finnish", "Swedish", "Japanese"
        );

        ChoiceDialog<String> dialog =
                new ChoiceDialog<>("English", languages);

        dialog.setTitle("Select Language / Valitse kieli");
        dialog.setHeaderText("Choose your preferred language");
        dialog.setContentText("Language:");

        Optional<String> selected = dialog.showAndWait();

        String language = selected.orElse("English");

        Locale locale;

        switch (language) {
            case "Finnish":
                locale = Locale.forLanguageTag("fi-FI");
                break;
            case "Swedish":
                locale = Locale.forLanguageTag("sv-SE");
                break;
            case "Japanese":
                locale = Locale.forLanguageTag("ja-JP");
                break;
            default:
                locale = Locale.forLanguageTag("en-US");
                break;
        }

        // Step 2: Load the correct translation file
        messages = ResourceBundle.getBundle(
                "MessagesBundle", locale
        );

        // Create database tables
        DBConnection.createTables();

        // Travel calculator
        Label travelTitle =
                new Label(messages.getString("travel.title"));

        TextField distanceField = new TextField();
        distanceField.setPromptText(
                messages.getString("travel.distance")
        );

        TextField speedField = new TextField();
        speedField.setPromptText(
                messages.getString("travel.speed")
        );

        Label resultLabel =
                new Label(messages.getString("travel.time"));

        Button calculateButton =
                new Button(messages.getString("travel.calculate"));

        calculateButton.setOnAction(e -> {
            try {
                double distance =
                        Double.parseDouble(distanceField.getText());

                double speed =
                        Double.parseDouble(speedField.getText());

                if (!Double.isFinite(distance)
                        || !Double.isFinite(speed)
                        || distance < 0 || speed <= 0) {
                    throw new IllegalArgumentException();
                }

                double time = distance / speed;

                if (!Double.isFinite(time)) {
                    throw new IllegalArgumentException();
                }

                resultLabel.setText(String.format(
                        "%s %.2f",
                        messages.getString("travel.time"),
                        time
                ));

                TempRecord record = new TempRecord(
                        distance,
                        speed,
                        time,
                        1
                );

                TempRecordDAO dao = new TempRecordDAO();
                dao.save(record);

            } catch (IllegalArgumentException ex) {
                resultLabel.setText(
                        messages.getString("travel.error")
                );
            }
        });

        // Temperature converter
        Label tempTitle =
                new Label(messages.getString("temperature.title"));

        TextField tempInput = new TextField();
        tempInput.setPromptText(
                messages.getString("temperature.prompt")
        );

        Button cToFButton = new Button(
                messages.getString("temperature.cToF")
        );

        Button fToCButton = new Button(
                messages.getString("temperature.fToC")
        );

        Button kToCButton = new Button(
                messages.getString("temperature.kToC")
        );

        Label tempResult =
                new Label(messages.getString("temperature.result"));

        TemperatureConverter converter =
                new TemperatureConverter();

        cToFButton.setOnAction(e -> {
            try {
                double celsius =
                        Double.parseDouble(tempInput.getText());

                double result =
                        converter.celsiusToFahrenheit(celsius);

                tempResult.setText(String.format(
                        "%s %.2f °F",
                        messages.getString("temperature.result"),
                        result
                ));

            } catch (NumberFormatException ex) {
                tempResult.setText(
                        messages.getString("temperature.error")
                );
            }
        });

        fToCButton.setOnAction(e -> {
            try {
                double fahrenheit =
                        Double.parseDouble(tempInput.getText());

                double result =
                        converter.fahrenheitToCelsius(fahrenheit);

                tempResult.setText(String.format(
                        "%s %.2f °C",
                        messages.getString("temperature.result"),
                        result
                ));

            } catch (NumberFormatException ex) {
                tempResult.setText(
                        messages.getString("temperature.error")
                );
            }
        });

        kToCButton.setOnAction(e -> {
            try {
                double kelvin =
                        Double.parseDouble(tempInput.getText());

                double result =
                        converter.kelvinToCelsius(kelvin);

                tempResult.setText(String.format(
                        "%s %.2f °C",
                        messages.getString("temperature.result"),
                        result
                ));

            } catch (NumberFormatException ex) {
                tempResult.setText(
                        messages.getString("temperature.error")
                );
            }
        });

        // Application layout
        VBox root = new VBox(
                10,
                travelTitle,
                distanceField,
                speedField,
                calculateButton,
                resultLabel,
                new Separator(),
                tempTitle,
                tempInput,
                cToFButton,
                fToCButton,
                kToCButton,
                tempResult
        );

        root.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(root, 450, 470);

        stage.setTitle(
                messages.getString("app.title")
        );

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
