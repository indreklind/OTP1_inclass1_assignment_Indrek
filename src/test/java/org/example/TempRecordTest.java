package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TempRecordTest {

    @Test
    void testTempRecordValues() {
        TempRecord record = new TempRecord(
                100.0,
                50.0,
                2.0
        );

        assertEquals(100.0, record.getDistance());
        assertEquals(50.0, record.getSpeed());
        assertEquals(2.0, record.getTime());
    }
}