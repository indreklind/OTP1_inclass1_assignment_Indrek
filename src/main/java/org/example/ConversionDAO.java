package org.example;

import java.sql.*;
import java.util.*;

public class ConversionDAO {

    public void save(double value, Unit from, Unit to,
                     double result, String language) throws SQLException {

        String sql = """
            INSERT INTO temperature_conversion
            (value, from_unit_id, to_unit_id, result, lang)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, value);
            ps.setInt(2, from.getId());
            ps.setInt(3, to.getId());
            ps.setDouble(4, result);
            ps.setString(5, language);
            ps.executeUpdate();
        }
    }

    public List<String> lastFive(String language) throws SQLException {
        String sql = """
            SELECT c.value, c.result, c.created_at,
                   COALESCE(f.name, ef.name) AS from_name,
                   COALESCE(t.name, et.name) AS to_name
            FROM temperature_conversion c
            JOIN unit f ON c.from_unit_id = f.id
            JOIN unit t ON c.to_unit_id = t.id
            LEFT JOIN unit_translation ef
                ON f.id = ef.unit_id AND ef.lang = 'en'
            LEFT JOIN unit_translation et
                ON t.id = et.unit_id AND et.lang = 'en'
            LEFT JOIN unit_translation f
                ON 1 = 0
            ORDER BY c.id DESC
            LIMIT 5
            """;

        // Use the selected language and English fallback.
        sql = """
            SELECT c.value, c.result, c.created_at,
                   COALESCE(ft.name, fe.name) AS from_name,
                   COALESCE(tt.name, te.name) AS to_name
            FROM temperature_conversion c
            JOIN unit fu ON c.from_unit_id = fu.id
            JOIN unit tu ON c.to_unit_id = tu.id
            LEFT JOIN unit_translation ft
                ON fu.id = ft.unit_id AND ft.lang = ?
            LEFT JOIN unit_translation fe
                ON fu.id = fe.unit_id AND fe.lang = 'en'
            LEFT JOIN unit_translation tt
                ON tu.id = tt.unit_id AND tt.lang = ?
            LEFT JOIN unit_translation te
                ON tu.id = te.unit_id AND te.lang = 'en'
            ORDER BY c.id DESC
            LIMIT 5
            """;

        List<String> result = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, language);
            ps.setString(2, language);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(String.format(
                            "%.2f %s → %.2f %s (%s)",
                            rs.getDouble("value"),
                            rs.getString("from_name"),
                            rs.getDouble("result"),
                            rs.getString("to_name"),
                            rs.getString("created_at")
                    ));
                }
            }
        }
        return result;
    }
}
