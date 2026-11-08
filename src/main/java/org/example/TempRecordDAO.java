
package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TempRecordDAO {

    public void save(TempRecord record) {

        String sql = """
                INSERT INTO travel_record
                (distance, speed, time,
                 distance_unit_id, speed_unit_id, time_unit_id)
                VALUES (
                    ?, ?, ?,
                    (SELECT id FROM unit WHERE unit_key = 'kilometer'),
                    (SELECT id FROM unit WHERE unit_key = 'km_per_hour'),
                    (SELECT id FROM unit WHERE unit_key = 'hour')
                )
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, record.getDistance());
            statement.setDouble(2, record.getSpeed());
            statement.setDouble(3, record.getTime());

            statement.executeUpdate();

            System.out.println("Travel record saved.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
