package org.example;

public class TemperatureConverter {

    public double celsiusToFahrenheit(double c) {
        checkFinite(c);
        return c * 9.0 / 5.0 + 32.0;
    }

    public double fahrenheitToCelsius(double f) {
        checkFinite(f);
        return (f - 32.0) * 5.0 / 9.0;
    }

    public double celsiusToKelvin(double c) {
        checkFinite(c);
        if (c < -273.15) throw new IllegalArgumentException("Below absolute zero");
        return c + 273.15;
    }

    public double kelvinToCelsius(double k) {
        checkFinite(k);
        if (k < 0) throw new IllegalArgumentException("Below absolute zero");
        return k - 273.15;
    }

    public double convert(double value, String from, String to) {
        double celsius = switch (from) {
            case "celsius" -> value;
            case "fahrenheit" -> fahrenheitToCelsius(value);
            case "kelvin" -> kelvinToCelsius(value);
            default -> throw new IllegalArgumentException("Unknown source unit");
        };

        return switch (to) {
            case "celsius" -> celsius;
            case "fahrenheit" -> celsiusToFahrenheit(celsius);
            case "kelvin" -> celsiusToKelvin(celsius);
            default -> throw new IllegalArgumentException("Unknown target unit");
        };
    }

    public boolean isExtremeTemperature(double celsius) {
        checkFinite(celsius);
        return celsius < -50 || celsius > 50;
    }

    private void checkFinite(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Temperature must be finite");
        }
    }
}
