package org.example;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TemperatureConverterTest {
    TemperatureConverter converter = new TemperatureConverter();

    @Test
    void testFahrenheitToCelsius() {
        assertEquals(0, converter.fahrenheitToCelsius(32));
        assertEquals(37, converter.fahrenheitToCelsius(98.6));
    }

    @Test
    void testCelsiusToFahrenheit() {
        assertEquals(-40, converter.celsiusToFahrenheit(-40));
        assertEquals(32, converter.celsiusToFahrenheit(0));
    }


    @Test
    void testIsExtremeTemperature() {
        assertTrue(converter.isExtremeTemperature(-41));
        assertTrue(converter.isExtremeTemperature(51));
        assertFalse(converter.isExtremeTemperature(-40));
        assertFalse(converter.isExtremeTemperature(50));

    }
}