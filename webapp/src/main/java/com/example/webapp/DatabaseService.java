package com.example.webapp;

import java.sql.*;
import java.util.*;


public class DatabaseService {
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
        public static List<Map<String, String>> getSMSForUser(String number) {
            List<Map<String, String>> resultList = new ArrayList<>();
            System.out.println("getSMSForUser() method called"); // Debug statement
        
            String query = "SELECT SMS_ID, RECEIVER_ADDRESS, BODY, SENT_DATE " +
                           "FROM FA_SMS " +
                           "WHERE RECEIVER_ADDRESS = ? " +
                           "ORDER BY SENT_DATE DESC " +
                           "FETCH FIRST 1 ROW ONLY";
        
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

        public static List<Map<String, String>> getNIDForUser(String number) {
            List<Map<String, String>> resultList = new ArrayList<>();
            System.out.println("getSMSForUser() method called"); // Debug statement
        
            String query = "SELECT OFFICIAL_ID, USER_ID, FIRST_NAME " +
                           "FROM SUB_USER " +
                           "WHERE USER_NAME = ? " +
                           "FETCH FIRST 1 ROW ONLY";
        
            String phoneNumber =  number; // Concatenating "2" with number
        
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
        public static List<Map<String, String>> fetchClientRequests(String userName,String selectedValueString) {
            List<Map<String, String>> resultList = new ArrayList<>();
            System.out.println("fetchClientRequests() method called");
    
            // Define SQL Query
            String query = "SELECT req.REQUEST_ID, req.CLIENT_SESSION_ID, " +
            "req.TER_TYPE, req.FRONT_END_DATE, res.FRONT_END_DATE, " +
            "req.TERMINAL_CODE, req.APPLICATION_CODE, req.USER_NAME, " +
            "req.SERVICE, req.CUSTOM_OPERATION_ID, req.APPLICATION_VERSION, " +
            "req.REQUEST_BODY, res.RESPONSE_BODY, res.STATUS_CODE, " +
            "res.USER_MESSAGE, res.EXCEPTIONS " +
            "FROM TRF_CLIENT_REQUEST req " +
            "INNER JOIN TRF_CLIENT_RESPONSE res ON req.REQUEST_ID = res.REQUEST_ID " +
            "WHERE req.FRONT_END_DATE BETWEEN TO_DATE('26-May-2023 09:00:00 AM', 'DD-Mon-YYYY HH:MI:SS AM') " +
            "AND SYSTIMESTAMP " +  // Fetch data up to the current timestamp
           /*  "AND req.CUSTOM_OPERATION_ID LIKE '%' " +*/
            "AND req.USER_NAME = ? " +
            "AND req.CUSTOM_OPERATION_ID = ? " +
            "ORDER BY req.FRONT_END_DATE DESC " +  
            "FETCH FIRST 1 ROW ONLY";  // Ensures only the latest row is returned

    
            System.out.println("Executing query for user: " + userName);
    
            try (Connection conn = getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
    
                stmt.setString(1, userName); // Set userName parameter
                stmt.setString(2, selectedValueString); // Set userName parameter
    
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

    // Runs a query with user input
    public String runQuery2(String input) {
        String query = "SELECT * FROM your_table WHERE column_name = ?"; // Replace with actual table/column name
        StringBuilder result = new StringBuilder();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, input);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.append(rs.getString("column_name")).append("<br>"); // Replace with actual column name
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log error properly in a real application
            return "Error executing query: " + e.getMessage();
        }

        return result.toString();
    }
     /************** */
}
