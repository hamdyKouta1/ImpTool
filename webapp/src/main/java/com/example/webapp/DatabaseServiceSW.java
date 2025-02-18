package com.example.webapp;

import java.sql.*;
import java.util.*;

public class DatabaseServiceSW {
    private static final String DB_URL = "jdbc:oracle:thin:@//xxx.xxx.xxx.xxx/xxxx";
    private static final String DB_USER = "xx_user_xx";
    private static final String DB_PASSWORD = "xx_pasword_xx";

    // Establishes a database connection
    private static Connection getConnection() {
        try {
            // Register the Oracle JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Oracle JDBC driver not found", e);
        } catch (SQLException e) {
            throw new RuntimeException("Database connection failed", e);
        }
    }

    // Runs a query to get the account ID
    public static List<Map<String, String>> GetAccID(String number) {
        List<Map<String, String>> resultList = new ArrayList<>();
        System.out.println("GetAccID() method called for number: " + number); // Debug statement

        String query = "SELECT customer_profile_id FROM accounts WHERE code = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, number); // Set the phone number before execution
            System.out.println("Executing query: " + stmt.toString()); // Debug statement

            try (ResultSet rs = stmt.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (rs.next()) {
                    Map<String, String> rowMap = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        rowMap.put(metaData.getColumnName(i), rs.getString(i));
                        System.out.println(metaData.getColumnName(i)+ rs.getString(i));
                    }
                    resultList.add(rowMap);
                }
            }

        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }

        return resultList;
    }
}
