# HTTP Reflection-based Calculator Server

This project implements a simple HTTP server that can handle requests to compute mathematical operations and bubble sort via reflection. The server listens for incoming HTTP requests, processes the request, and returns the result either as a JSON or as an HTML page.

## Getting Started

These instructions will help you get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

To run this project, you'll need:

- Java JDK 8 or higher installed on your system
- Maven for managing project dependencies
- A terminal/command line to build and run the project

### Installing

1. **Clone the repository:**

   ```bash
   git clone https://github.com/Erick01081/Bono_Parcial1_Arep.git
    ```
2. **Navigate to the project directory:**

    ```bash
   cd Bono_Parcial1_Arep
    ```
    
3. **Build the project using Maven:**

    ```bash
   mvn clean install
    ```

4. **Run the server:**

    ```bash
   java -cp target/calcreflex-1.0-SNAPSHOT.jar edu.escuelaing.arem.ASE.app.CalcreflexBEServer
    ```

    ```bash
   java -cp target/calcreflex-1.0-SNAPSHOT.jar edu.escuelaing.arem.ASE.app.CalcreflexFacade
    ```

## Project Architecture

This project follows a simple client-server architecture using Java's socket programming to handle HTTP requests. The server leverages reflection to dynamically invoke mathematical functions from the standard Java Math library based on the client's request. The architecture allows flexibility and extendibility by enabling the addition of new mathematical operations without modifying the core logic. The server listens on a specified port for incoming connections and processes requests by extracting the operation and parameters from the URL, invoking the appropriate method, and returning the result as a JSON response. This approach makes it lightweight, easy to deploy, and suitable for educational purposes or simple computational services.


### Running the application

After running the server, you can access it by sending GET requests to http://localhost:35000. It can handle mathematical operations using reflection.

### Example usage

![image](https://github.com/user-attachments/assets/ae290c46-e48e-47ce-932f-416d8d58c983)

### Built With

Maven - Dependency Management
Java Reflection API - Used for invoking mathematical functions dynamically

### Authors

Erick Montero


