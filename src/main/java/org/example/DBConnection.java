package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    private static final String URL = "jdbc:sqlite:temperature.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void createTables() {
        String createTemperatureUnitTable = """
                CREATE TABLE IF NOT EXISTS temperature_unit (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    symbol TEXT NOT NULL
                );
                """;

        String createTempRecordTable = """
                CREATE TABLE IF NOT EXISTS temp_record (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    distance REAL NOT NULL,
                    speed REAL NOT NULL,
                    time REAL NOT NULL,
                    unit_id INTEGER,
                    FOREIGN KEY (unit_id) REFERENCES temperature_unit(id)
                );
                """;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(createTemperatureUnitTable);
            statement.execute(createTempRecordTable);

            statement.execute("""
                INSERT OR IGNORE INTO temperature_unit (id, name, symbol)
                VALUES (1, 'Celsius', '°C')
                """);

            System.out.println("Database tables created.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}