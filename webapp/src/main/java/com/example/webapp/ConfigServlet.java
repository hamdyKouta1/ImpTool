package com.example.webapp;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ConfigServlet")
public class ConfigServlet extends HttpServlet {
    
    static String path= System.getProperty("user.home");
        private static String DATA_FILE = path+"/jar-lib-imp-tool/db-table.json";
    //    private static String DATA_FILE = path+"\\jar-lib-imp-tool\\db-table.json";
       // private static String DATA_FILE = "config.json";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                System.out.println(DATA_FILE);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            File file = new File(DATA_FILE);
            if (!file.exists()) {
                createNewFile(file);
            }
            
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.getWriter().write(line);
                }
            }
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                "Error reading configuration data");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    jsonBuilder.append(line);
                }
            }
            
            File file = new File(DATA_FILE);
            if (!file.exists()) {
                createNewFile(file);
            }
            
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(jsonBuilder.toString());
            }
            
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Data saved successfully");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                "Error saving configuration data");
        }
    }

    private void createNewFile(File file) throws IOException {
        file.getParentFile().mkdirs();
        file.createNewFile();
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("[]"); // Initialize with empty array
        }
    }
}