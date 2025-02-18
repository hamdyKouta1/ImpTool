package com.example.webapp;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8081);
        System.out.println(System.getProperty("user.home"));
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        
        // Load web resources from the classpath
        URL webDir = Main.class.getClassLoader().getResource("webapp");
        if (webDir == null) {
            throw new RuntimeException("Web resources not found!");
        }
        webapp.setResourceBase(webDir.toExternalForm());
        
        // Set index.html as the welcome file
        webapp.setWelcomeFiles(new String[] { "index.html" });
        
        // Disable directory listings
        webapp.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");
        
        server.setHandler(webapp);
        server.start();
        server.join();
    }
}