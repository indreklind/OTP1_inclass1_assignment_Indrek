package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class TemperatureUnitDAOTest {

    @Test
    void testSaveTemperatureUnit() {
        DBConnection.createTables();

        TemperatureUnit unit = new TemperatureUnit(
                2,
                "Fahrenheit",
                "°F"
        );

        TemperatureUnitDAO dao = new TemperatureUnitDAO();

        assertDoesNotThrow(() -> dao.save(unit));
    }
}