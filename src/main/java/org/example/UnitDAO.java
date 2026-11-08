package org.example;

import java.sql.*;
import java.util.*;

public class UnitDAO {

    public List<Unit> findByCategory(String category, String language)
            throws SQLException {

        String sql = """
            SELECT u.id, u.unit_key, u.category, u.symbol,
                   COALESCE(t.name, en.name) AS localized_name,
                   COALESCE(t.description, en.description) AS localized_description
            FROM unit u
            LEFT JOIN unit_translation t
                ON u.id = t.unit_id AND t.lang = ?
            LEFT JOIN unit_translation en
                ON u.id = en.unit_id AND en.lang = 'en'
            WHERE u.category = ?
            ORDER BY u.id
            """;

        List<Unit> units = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, language);
            ps.setString(2, category);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    units.add(new Unit(
                            rs.getInt("id"),
                            rs.getString("unit_key"),
                            rs.getString("category"),
                            rs.getString("symbol"),
                            rs.getString("localized_name"),
                            rs.getString("localized_description")
                    ));
                }
            }
        }

        return units;
    }
}
