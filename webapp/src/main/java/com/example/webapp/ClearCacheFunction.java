/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.webapp;


 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
 
public class ClearCacheFunction {
 
   
 
    public static String sendSOAPRequest(String myurl,StringBuilder MyRequst) throws IOException {
        // Generate new UUIDs for every request
                        StringBuilder response = new StringBuilder();

        // Create the connection
        URL url = new URL(myurl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
 
        // Set the request method and headers
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        connection.setDoOutput(true);
 
        // Send the SOAP request
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = MyRequst.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }
 
        // Get the response
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);
 
        if (responseCode == HttpURLConnection.HTTP_OK) { // Success
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("Response Body: " + response.toString());
            }
        } else {
            System.out.println("SOAP request failed.");
        }
        return response.toString();
    }
}