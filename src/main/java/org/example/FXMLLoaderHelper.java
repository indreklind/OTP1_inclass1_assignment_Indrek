package org.example;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.geometry.NodeOrientation;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class FXMLLoaderHelper {

    public static void reload(Stage stage, Locale locale) throws Exception {
        ResourceBundle bundle =
                ResourceBundle.getBundle("MessagesBundle", locale);

        FXMLLoader loader = new FXMLLoader(
                FXMLLoaderHelper.class.getResource("/org/example/MainView.fxml"),
                bundle
        );

        Parent root = loader.load();

        MainController controller = loader.getController();
        String language = locale.getLanguage();
        controller.setLocale(locale, bundle);
        controller.setLanguage(language);

        root.setNodeOrientation(
                language.equals("ar")
                        ? NodeOrientation.RIGHT_TO_LEFT
                        : NodeOrientation.LEFT_TO_RIGHT
        );

        stage.setTitle(bundle.getString("app.title"));
        stage.setScene(new Scene(root, 500, 650));
    }
}
