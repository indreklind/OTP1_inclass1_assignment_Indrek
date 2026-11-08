package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

class UnitDAOTest {

    @BeforeAll
    static void setup() {
        DBConnection.createTables();
    }

    @Test
    void findsFinnishCelsius() throws SQLException {
        UnitDAO dao = new UnitDAO();
        var units = dao.findByCategory("temperature", "fi");
        assertTrue(units.stream()
                .anyMatch(u -> u.getKey().equals("celsius")
                        && u.getName().equals("Celsius")));
    }

    @Test
    void returnsEnglishFallbackWhenTranslationMissing() throws Exception {
        UnitDAO dao = new UnitDAO();

        // A language with no translations should use English.
        var units = dao.findByCategory("temperature", "xx");

        assertTrue(units.stream()
                .anyMatch(u -> u.getKey().equals("fahrenheit")
                        && u.getName().equals("Fahrenheit")));
    }
}
