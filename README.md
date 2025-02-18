# ImpTool WebApp

## Overview
ImpTool is a Maven-based Java project designed for web applications. This repository contains the `webapp` module, which includes the main application code, configuration files, and dependencies required to run the web application.

## Directory Structure
The `webapp` directory contains the following key files and subdirectories:
- `.vscode/`: Contains Visual Studio Code settings and configurations.
- `dependency-reduced-pom.xml`: A Maven POM file created by the Maven Shade Plugin to list reduced dependencies included in the final build.
- `nb-configuration.xml`: NetBeans IDE configuration settings.
- `nbactions.xml`: NetBeans custom actions/commands configuration.
- `pom.xml`: The main Maven Project Object Model (POM) file for the `webapp` module.
- `src/`: The source directory containing the Java source code and resources for the `webapp` module.

## Key Files and Their Purpose
### `pom.xml`
This is the main POM file that defines the structure and dependencies of the `webapp` module. It includes:
- Group ID: `com.example`
- Artifact ID: `webapp`
- Version: `1.0-SNAPSHOT`
- Packaging: JAR
- Jetty Version: `9.4.57.v20241219`
- Dependencies: JSON library, Jetty web server and web application libraries, Oracle JDBC driver
- Build Plugins: Maven Compiler Plugin, Maven Resources Plugin, Maven Shade Plugin

### `src/main/java/com/example/webapp/Main.java`
This is the main entry point of the application. It initializes and starts the Jetty server.

## How to Run the WebApp
1. **Prerequisites**: Ensure you have Maven and JDK 1.8 or higher installed on your system.
2. **Clone the Repository**:
   ```bash
   git clone https://github.com/hamdyKouta1/ImpTool.git
   cd ImpTool/webapp
   ```
3. **Build the Project**:
   ```bash
   mvn clean install
   ```
4. **Run the Application**:
   ```bash
   mvn exec:java -Dexec.mainClass="com.example.webapp.Main"
   ```

## Integrating with a Python Project
To integrate the `ImpTool` web application with a Python project, follow these steps:

1. **Start the WebApp**: Ensure the web application is running by following the steps in the "How to Run the WebApp" section.
2. **Make HTTP Requests**: Use Python's `requests` library to interact with the web application's API endpoints.

### Example Python Integration
```python
import requests

# Define the base URL of the running web application
base_url = 'http://localhost:8080'

# Example API endpoint
endpoint = '/api/resource'

# Make a GET request
response = requests.get(base_url + endpoint)

# Check the response status
if response.status_code == 200:
    print('Success:', response.json())
else:
    print('Error:', response.status_code)
```

## Main Functions
### `Main.java`
This file contains the main function which initializes and starts the Jetty server:
```java
package com.example.webapp;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        // Add servlets here
        context.addServlet(new ServletHolder(new MyServlet()), "/api/resource");

        server.start();
        server.join();
    }
}
```

## Important Files
- `pom.xml`: Defines the project's dependencies and build configuration.
- `src/main/java/com/example/webapp/Main.java`: The main entry point of the application.
- `src/main/webapp/`: Contains web resources such as HTML, CSS, and JavaScript files.

## Contributing
Contributions are welcome! Please open an issue or submit a pull request for any improvements or bug fixes.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
