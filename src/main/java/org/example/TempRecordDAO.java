package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TempRecordDAO {

    public void save(TempRecord record) {

        String sql = """
                INSERT INTO temp_record (distance, speed, time, unit_id)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, record.getDistance());
            statement.setDouble(2, record.getSpeed());
            statement.setDouble(3, record.getTime());
            statement.setInt(4, record.getUnitId());

            statement.executeUpdate();

            System.out.println("Record saved.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}