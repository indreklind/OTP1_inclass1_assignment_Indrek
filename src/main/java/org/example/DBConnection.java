package org.example;

import java.sql.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class DBConnection {
    private static final String URL = "jdbc:sqlite:temperature.db";

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL);
        try (Statement st = connection.createStatement()) {
            st.execute("PRAGMA foreign_keys = ON");
        }
        return connection;
    }

    public static void createTables() {
        try (Connection con = getConnection()) {
            InputStream input = DBConnection.class
                    .getClassLoader().getResourceAsStream("init.sql");

            if (input == null) {
                throw new IllegalStateException("init.sql not found");
            }

            String sql;
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(input, StandardCharsets.UTF_8))) {
                sql = reader.lines().collect(java.util.stream.Collectors.joining("\n"));
            }

            try (Statement st = con.createStatement()) {
                for (String command : sql.split(";")) {
                    if (!command.isBlank()) {
                        st.execute(command);
                    }
                }
            }

            System.out.println("Database initialized.");
        } catch (Exception e) {
            throw new RuntimeException("Database initialization failed", e);
        }
    }
}
