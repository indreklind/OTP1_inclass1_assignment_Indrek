package org.example;

import java.sql.*;

public class TravelDAO {
    public void save(double distance, double speed, double time,
                     Unit distanceUnit, Unit speedUnit, Unit timeUnit)
            throws SQLException {

        String sql = """
            INSERT INTO travel_record
            (distance, speed, time, distance_unit_id, speed_unit_id, time_unit_id)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, distance);
            ps.setDouble(2, speed);
            ps.setDouble(3, time);
            ps.setInt(4, distanceUnit.getId());
            ps.setInt(5, speedUnit.getId());
            ps.setInt(6, timeUnit.getId());
            ps.executeUpdate();
        }
    }
}
