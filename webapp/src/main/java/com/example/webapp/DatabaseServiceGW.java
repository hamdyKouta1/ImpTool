package com.example.webapp;

import java.sql.*;
import java.util.*;


public class DatabaseServiceGW {
    private static final String DB_URL = "jdbc:oracle:thin:@//xxx.xxx.xxx.xxx/xxxx";
    private static final String DB_USER = "xx_user_xx";
    private static final String DB_PASSWORD = "xx_pasword_xx";

    // Establishes a database connection
    private static Connection getConnection() throws SQLException {
        try {
            // Register the Oracle JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Oracle JDBC driver not found", e);
        }
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        }
    
        // Runs a query without parameters
        public static List<Map<String, String>> GetBKID(String number) {
            List<Map<String, String>> resultList = new ArrayList<>();
            System.out.println("GetBKID() method called"); // Debug statement
        
            String query = "Select id from bk_registration where cif=? " ;
                           
        
            String phoneNumber = "2" + number; // Concatenating "2" with number
        
            System.out.println("Executing query for phone number: " + phoneNumber); // Debug statement
        
            try (Connection conn = getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
        
                stmt.setString(1, phoneNumber); // Set the phone number before execution
        
                try (ResultSet rs = stmt.executeQuery()) {
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
        
                    while (rs.next()) {
                        Map<String, String> rowMap = new HashMap<>();
                        for (int i = 1; i <= columnCount; i++) {
                            rowMap.put(metaData.getColumnName(i), rs.getString(i));
                        }
                        resultList.add(rowMap);
                    }
                }
        
            } catch (SQLException e) {
                e.printStackTrace(); // Log error properly in a real application
            }
        
            return resultList;
        }

        public static List<Map<String, String>> GetAccID(String number) {
            List<Map<String, String>> resultList = new ArrayList<>();
            System.out.println("GetAccID() method called"); // Debug statement
        
            String query = "Select id from bk_registration where cif=? " ;
                           
        
            String phoneNumber = "2" + number; // Concatenating "2" with number
        
            System.out.println("Executing query for phone number: " + phoneNumber); // Debug statement
        
            try (Connection conn = getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
        
                stmt.setString(1, phoneNumber); // Set the phone number before execution
        
                try (ResultSet rs = stmt.executeQuery()) {
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
        
                    while (rs.next()) {
                        Map<String, String> rowMap = new HashMap<>();
                        for (int i = 1; i <= columnCount; i++) {
                            rowMap.put(metaData.getColumnName(i), rs.getString(i));
                        }
                        resultList.add(rowMap);
                    }
                }
        
            } catch (SQLException e) {
                e.printStackTrace(); // Log error properly in a real application
            }
        
            return resultList;
        }

        public static String ClearBillerCache() {
            String query = "DELETE FROM biller_services_cache WHERE channel_code = ?";
            int rowsAffected = 0;
    
            try (Connection conn = getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
    
                stmt.setString(1, "MYFAWRYPT_MOB");
                rowsAffected = stmt.executeUpdate(); // Returns the number of rows affected
    
            } catch (SQLException e) {
                e.printStackTrace();
                return "Error executing query: " + e.getMessage();
            }
    
            return "Rows affected: " + rowsAffected;
        }

        public static List<Map<String, Object>> getBillTypeByCode(int code) {
            List<Map<String, Object>> billTypes = new ArrayList<>();
            String query = "SELECT * FROM Bill_types WHERE code = ?";
    
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement(query)) {
    
                stmt.setInt(1, code); // Set parameter
                ResultSet rs = stmt.executeQuery();
    
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
    
                while (rs.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.put(metaData.getColumnName(i), rs.getObject(i));
                    }
                    billTypes.add(row);
                }
    
            } catch (SQLException e) {
                e.printStackTrace();
            }
    
            return billTypes;
        }
}
