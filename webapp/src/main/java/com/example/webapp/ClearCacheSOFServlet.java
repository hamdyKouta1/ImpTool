/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.example.webapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hamdy
 */
@WebServlet("/ClearCacheSOFServlet")

public class ClearCacheSOFServlet extends HttpServlet {
    
 
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Handle GET request
        resp.setContentType("text/html");
 String myurl = "http:/xxx.xxx.xxx.xxx:xxxx/xxxxx/xxxxxxxxx";

        // Construct SOAP request dynamically using StringBuilder
        StringBuilder myRq = new StringBuilder();
        myRq.append("xxxx append your rq here xxxx");
        String input = req.getParameter("inputText");
        String returnedData = ClearCacheFunction.sendSOAPRequest(myurl,myRq);
        
        

        // Generate HTML response
        resp.getWriter().write(
            "<html>" +
            "<head>" +
            "<title>Query 4 Result</title>" +
            "<style>" +
            "body { font-family: Arial, sans-serif; background-color: #f4f4f9; text-align: center; padding: 20px; }" +
            "#container { background: white; padding: 20px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); max-width: 600px; margin: auto; }" +
            "h1 { color: #333; }" +
            "p { font-size: 18px; color: #555; }" +
            "button { padding: 10px 20px; font-size: 16px; margin: 10px; border: none; cursor: pointer; border-radius: 5px; }" +
            "#copyBtn { background-color: #4CAF50; color: white; }" +
            "#backBtn { background-color: #008CBA; color: white; }" +
            "button:hover { opacity: 0.8; }" +
            "</style>" +
            "</head>" +
            "<body>" +
            "<div id='container'>" +
            "<h1>Query 4 Result</h1>" +
            "<p><strong>Executed with input:</strong> " + input + "</p>" +
            "<p><strong>Data:</strong> <span id='resultData'>" + returnedData + "</span></p>" +
            "<button id='copyBtn' onclick='copyToClipboard()'>Copy Result</button>" +
            "<button id='backBtn' onclick='goBack()'>Back</button>" +
            "</div>" +
            "<script>" +
            "function copyToClipboard() {" +
            "    var resultElements = document.querySelectorAll('.resultData');" +
            "    var text = Array.from(resultElements).map(el => el.textContent).join('\\n');" +
            "    if (navigator.clipboard) {" +
            "        navigator.clipboard.writeText(text).then(() => {" +
            "            showFeedback('Result copied to clipboard!');" +
            "        }).catch(err => {" +
            "            console.error('Clipboard API failed:', err);" +
            "            copyUsingExecCommand(text);" +
            "        });" +
            "    } else {" +
            "        copyUsingExecCommand(text);" +
            "    }" +
            "}" +
            "function copyUsingExecCommand(text) {" +
            "    var textarea = document.createElement('textarea');" +
            "    textarea.value = text;" +
            "    textarea.style.position = 'fixed';" +
            "    document.body.appendChild(textarea);" +
            "    textarea.select();" +
            "    try {" +
            "        var successful = document.execCommand('copy');" +
            "        if (successful) {" +
            "            showFeedback('Result copied to clipboard!');" +
            "        } else {" +
            "            showFeedback('Press Ctrl+C to copy');" +
            "        }" +
            "    } catch (err) {" +
            "        showFeedback('Failed to copy text');" +
            "    } finally {" +
            "        document.body.removeChild(textarea);" +
            "    }" +
            "}" +
            "function showFeedback(message) {" +
            "    var feedback = document.getElementById('copyFeedback') || document.createElement('div');" +
            "    feedback.id = 'copyFeedback';" +
            "    feedback.textContent = message;" +
            "    feedback.style = 'position: fixed; top: 20px; left: 50%; transform: translateX(-50%); background: #4CAF50; color: white; padding: 10px 20px; border-radius: 5px; z-index: 1000;';" +
            "    if (!document.getElementById('copyFeedback')) {" +
            "        document.body.appendChild(feedback);" +
            "    }" +
            "    setTimeout(() => {" +
            "        if (document.body.contains(feedback)) {" +
            "            document.body.removeChild(feedback);" +
            "        }" +
            "    }, 2000);" +
            "}" +
            "function goBack() {" +
            "    window.history.back();" +
            "}" +
            "</script>" +
            "</body>" +
            "</html>"
        );
        
    }
}
