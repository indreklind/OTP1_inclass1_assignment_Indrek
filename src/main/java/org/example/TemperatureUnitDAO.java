package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TemperatureUnitDAO {

    public void save(TemperatureUnit unit) {

        String sql = """
            INSERT OR IGNORE INTO temperature_unit (id, name, symbol)
            VALUES (?, ?, ?)
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, unit.getId());
            statement.setString(2, unit.getName());
            statement.setString(3, unit.getSymbol());

            statement.executeUpdate();

            System.out.println("Temperature unit saved.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}