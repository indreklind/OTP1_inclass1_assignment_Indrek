package org.example;

import org.junit.jupiter.api.Test;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

public class LocalizationTest {

    @Test
    void testEnglish() {
        testLanguage("en-US");
    }

    @Test
    void testFinnish() {
        testLanguage("fi-FI");
    }

    @Test
    void testSwedish() {
        testLanguage("sv-SE");
    }

    @Test
    void testJapanese() {
        testLanguage("ja-JP");
    }

    private void testLanguage(String languageTag) {

        Locale locale = Locale.forLanguageTag(languageTag);

        ResourceBundle bundle =
                ResourceBundle.getBundle("MessagesBundle", locale);

        assertNotNull(bundle);
        assertFalse(bundle.getString("app.title").isBlank());
        assertFalse(bundle.getString("travel.title").isBlank());
        assertFalse(bundle.getString("temperature.title").isBlank());
    }
}