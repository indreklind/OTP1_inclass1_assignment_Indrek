package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // Step 1: Ask the user to choose a language, now including Arabic
        List<String> languages = Arrays.asList(
                "English", "Finnish", "Swedish", "Japanese", "Arabic"
        );

        ChoiceDialog<String> dialog = new ChoiceDialog<>("English", languages);
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
            case "Arabic":
                locale = Locale.forLanguageTag("ar");
                break;
            default:
                locale = Locale.forLanguageTag("en-US");
                break;
        }

        // Step 2: Load the correct translation file
        ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", locale);

        // Create database tables
        DBConnection.createTables();

        // Step 3: Load FXML and pass the localized ResourceBundle
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/MainView.fxml"));
        loader.setResources(messages);
        Parent root = loader.load();

        Scene scene = new Scene(root, 450, 470);
        stage.setTitle(messages.getString("app.title"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}