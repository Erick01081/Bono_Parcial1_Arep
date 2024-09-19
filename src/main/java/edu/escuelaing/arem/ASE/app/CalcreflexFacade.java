package edu.escuelaing.arem.ASE.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class CalcreflexFacade
{
    public static void main(String[] args) throws IOException, URISyntaxException {
    ServerSocket serverSocket = null;
    try {
        serverSocket = new ServerSocket(35000);
    } catch (IOException e) {
        System.err.println("Could not listen on port: 35000.");
        System.exit(1);
    }
    boolean runnning = true;
    while (runnning) {
        Socket clientSocket = null;
        try {
            System.out.println("Listo para recibir ...");
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        PrintWriter out = new PrintWriter(
                clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine;
        boolean isFirstLine=true;
        String firstLine = "";
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Recibí: " + inputLine);
            if(isFirstLine){
                firstLine = inputLine;
                isFirstLine = false;
                System.out.println(firstLine);
            }
            if (!in.ready()) {
                break;
            }
        }

        URI reqURI = getReqURI(firstLine);

        if(reqURI.getPath().startsWith("/computar")){
            outputLine = "HTTP/1.1 200 OK\n"
                    + "Content-Type: application/json\r\n"
                    + "\r\n" + HttpConnectionExample.getReponse("/compreflex?" + reqURI.getQuery());
        } else {
            outputLine = htmlClient();
        }
        out.println(outputLine);
        out.close();
        in.close();
        clientSocket.close();
    }
        serverSocket.close();
}

    public static String htmlClient(){
            String htmlcode="HTTP/1.1 200 OK\n"
                    + "Content-Type: text/html\r\n"
                    + "\r\n" +
                    "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "    <head>\n" +
                    "        <title>Form Example</title>\n" +
                    "        <meta charset=\"UTF-8\">\n" +
                    "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    </head>\n" +
                    "    <body>\n" +
                    "        <h1>Form with GET</h1>\n" +
                    "        <form action=\"/hello\">\n" +
                    "            <label for=\"name\">Name:</label><br>\n" +
                    "            <input type=\"text\" id=\"name\" name=\"name\" value=\"John\"><br><br>\n" +
                    "            <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n" +
                    "        </form> \n" +
                    "        <div id=\"getrespmsg\"></div>\n" +
                    "\n" +
                    "        <script>\n" +
                    "            function loadGetMsg() {\n" +
                    "                let nameVar = document.getElementById(\"name\").value;\n" +
                    "                const xhttp = new XMLHttpRequest();\n" +
                    "                xhttp.onload = function() {\n" +
                    "                    document.getElementById(\"getrespmsg\").innerHTML =\n" +
                    "                    this.responseText;\n" +
                    "                }\n" +
                    "                xhttp.open(\"GET\", \"/computar?computar=\"+nameVar);\n" +
                    "                xhttp.send();\n" +
                    "            }\n" +
                    "        </script>\n" +
                    "\n" +
                    "    </body>\n" +
                    "</html>";
            return htmlcode;
    }

    public static URI getReqURI(String reqURI) throws URISyntaxException {
        System.out.println(reqURI);
        String ruri = reqURI.split(" ")[1];
        System.out.println(ruri );
        return new URI(ruri);
    }



}