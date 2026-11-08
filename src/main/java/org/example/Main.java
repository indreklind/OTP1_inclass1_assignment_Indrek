package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import java.util.Locale;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        DBConnection.createTables();
        FXMLLoaderHelper.reload(stage, Locale.US);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
