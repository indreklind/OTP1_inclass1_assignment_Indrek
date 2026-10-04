package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class TempRecordDAOTest {

    @Test
    void testSaveRecord() {
        DBConnection.createTables();

        TempRecord record = new TempRecord(
                120.0,
                60.0,
                2.0,
                1
        );

        TempRecordDAO dao = new TempRecordDAO();

        assertDoesNotThrow(() -> dao.save(record));
    }
}