package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.geometry.NodeOrientation;

import java.sql.SQLException;
import java.util.*;

public class MainController {

    @FXML private ComboBox<String> languageBox;
    @FXML private ComboBox<Unit> fromUnitBox;
    @FXML private ComboBox<Unit> toUnitBox;
    @FXML private TextField tempInput;
    @FXML private Label tempResult;
    @FXML private ListView<String> historyList;
    @FXML private TextField distanceField;
    @FXML private TextField speedField;
    @FXML private Label travelResult;
    @FXML private Label titleLabel;

    private ResourceBundle resources;
    private Locale locale;
    private String language = "en";

    private final UnitDAO unitDAO = new UnitDAO();
    private final ConversionDAO conversionDAO = new ConversionDAO();
    private final TravelDAO travelDAO = new TravelDAO();
    private final TemperatureConverter converter = new TemperatureConverter();

    public void setLocale(Locale locale, ResourceBundle resources) {
        this.locale = locale;
        this.resources = resources;
    }

    @FXML
    public void initialize() {
        languageBox.getItems().addAll(
                "English", "Finnish", "Swedish", "Japanese", "Arabic"
        );
        languageBox.setValue("English");
        loadUnits();
    }

    public void setLanguage(String language) {
        this.language = language;
        loadUnits();
        loadHistory();
    }

    @FXML
    public void changeLanguage() {
        String selected = languageBox.getValue();
        if (selected == null) return;

        String tag = switch (selected) {
            case "Finnish" -> "fi-FI";
            case "Swedish" -> "sv-SE";
            case "Japanese" -> "ja-JP";
            case "Arabic" -> "ar";
            default -> "en-US";
        };

        Locale newLocale = Locale.forLanguageTag(tag);

        try {
            FXMLLoaderHelper.reload((Stage) languageBox.getScene()
                    .getWindow(), newLocale);
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void loadUnits() {
        try {
            List<Unit> units = unitDAO.findByCategory("temperature", language);
            fromUnitBox.getItems().setAll(units);
            toUnitBox.getItems().setAll(units);

            if (units.size() >= 3) {
                fromUnitBox.setValue(units.get(0));
                toUnitBox.setValue(units.get(1));
            }
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void convertTemperature() {
        try {
            double value = Double.parseDouble(tempInput.getText().trim());
            Unit from = fromUnitBox.getValue();
            Unit to = toUnitBox.getValue();

            if (from == null || to == null) {
                throw new IllegalArgumentException("Select both units");
            }

            double result = converter.convert(
                    value, from.getKey(), to.getKey()
            );

            tempResult.setText(String.format(
                    locale, "%.2f %s = %.2f %s",
                    value, from.getName(), result, to.getName()
            ));

            conversionDAO.save(value, from, to, result, language);
            loadHistory();

        } catch (IllegalArgumentException e) {
            showError(resources.getString("temperature.error"));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    private void loadHistory() {
        try {
            historyList.getItems().setAll(
                    conversionDAO.lastFive(language)
            );
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void calculateTravel() {
        try {
            double distance = Double.parseDouble(distanceField.getText().trim());
            double speed = Double.parseDouble(speedField.getText().trim());

            if (!Double.isFinite(distance) || !Double.isFinite(speed)
                    || distance < 0 || speed <= 0) {
                throw new IllegalArgumentException();
            }

            double time = distance / speed;

            UnitDAO dao = unitDAO;
            Unit km = findUnit(dao, "kilometer");
            Unit kmh = findUnit(dao, "km_per_hour");
            Unit hour = findUnit(dao, "hour");

            travelDAO.save(distance, speed, time, km, kmh, hour);

            travelResult.setText(String.format(
                    locale, "%s %.2f h",
                    resources.getString("travel.time"), time
            ));

        } catch (IllegalArgumentException e) {
            travelResult.setText(resources.getString("travel.error"));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    private Unit findUnit(UnitDAO dao, String key) throws SQLException {
        return dao.findByCategory(
                        key.equals("kilometer") ? "distance" :
                                key.equals("km_per_hour") ? "speed" : "time",
                        language
                ).stream()
                .filter(u -> u.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Unit not found: " + key));
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void applyDirection(Node root) {
        root.setNodeOrientation(
                language.equals("ar")
                        ? NodeOrientation.RIGHT_TO_LEFT
                        : NodeOrientation.LEFT_TO_RIGHT
        );
    }
}
