
package com.example.webapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/GetBillInfoServlet")
public class GetBillInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set response content type
        resp.setContentType("text/html");

        // Retrieve the 'code' parameter from request
        String codeParam = req.getParameter("inputGetBillInfoServlet");
        String returnedData = "No data found.";
        
        if (codeParam != null && !codeParam.trim().isEmpty()) {
            try {
                int code = Integer.parseInt(codeParam);
                
                // Fetch bill type details
                List<Map<String, Object>> resultList = DatabaseServiceGW.getBillTypeByCode(code);

                // Process results and get first row if exists
                if (!resultList.isEmpty()) {
                    Map<String, Object> row = resultList.get(0);
                    returnedData = "<ul>";
                    for (Map.Entry<String, Object> entry : row.entrySet()) {
                        returnedData += "<li><strong>" + entry.getKey() + ":</strong> " + entry.getValue() + "</li>";
                    }
                    returnedData += "</ul>";
                }
            } catch (NumberFormatException e) {
                returnedData = "Invalid code format. Please enter a numeric value.";
            }
        } else {
            returnedData = "Please provide a valid code.";
        }

        // Generate HTML response
        resp.getWriter().write(
            "<html>" +
            "<head>" +
            "<title>Bill Type Query Result</title>" +
            "<style>" +
            "body { font-family: Arial, sans-serif; background-color: #f4f4f9; text-align: center; padding: 20px; }" +
            "#container { background: white; padding: 20px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); max-width: 600px; margin: auto; }" +
            "h1 { color: #333; }" +
            "p, li { font-size: 18px; color: #555; text-align: left; }" +
            "button { padding: 10px 20px; font-size: 16px; margin: 10px; border: none; cursor: pointer; border-radius: 5px; }" +
            "#copyBtn { background-color: #4CAF50; color: white; }" +
            "#backBtn { background-color: #008CBA; color: white; }" +
            "button:hover { opacity: 0.8; }" +
            "</style>" +
            "</head>" +
            "<body>" +
            "<div id='container'>" +
            "<h1>Bill Type Query Result</h1>" +
            "<p><strong>Executed with code:</strong> " + codeParam + "</p>" +
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