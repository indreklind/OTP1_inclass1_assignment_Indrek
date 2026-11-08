package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TemperatureConverterTest {

    private final TemperatureConverter converter = new TemperatureConverter();

    @Test
    void celsiusToFahrenheit() {
        assertEquals(32, converter.celsiusToFahrenheit(0), 0.001);
    }

    @Test
    void fahrenheitToCelsius() {
        assertEquals(0, converter.fahrenheitToCelsius(32), 0.001);
    }

    @Test
    void kelvinToCelsius() {
        assertEquals(0, converter.kelvinToCelsius(273.15), 0.001);
    }

    @Test
    void celsiusToKelvin() {
        assertEquals(273.15, converter.celsiusToKelvin(0), 0.001);
    }

    @Test
    void belowAbsoluteZeroRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> converter.kelvinToCelsius(-1));
    }

    @Test
    void extremeTemperature() {
        assertTrue(converter.isExtremeTemperature(60));
        assertFalse(converter.isExtremeTemperature(20));
    }
}
