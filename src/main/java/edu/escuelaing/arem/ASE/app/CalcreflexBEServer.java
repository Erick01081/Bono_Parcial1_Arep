    package edu.escuelaing.arem.ASE.app;

    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.io.PrintWriter;
    import java.lang.reflect.InvocationTargetException;
    import java.lang.reflect.Method;
    import java.net.ServerSocket;
    import java.net.Socket;
    import java.net.URI;
    import java.net.URISyntaxException;

    /**
     * Hello world!
     *
     */
    public class CalcreflexBEServer
    {
        public static void main(String[] args) throws IOException, URISyntaxException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(36000);
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

                URI reqURL = getReqURI(firstLine);

                if (reqURL.getPath().startsWith("/compreflex")) {
                    String computationResult = computeMathCommand(reqURL.toString());
                    outputLine = "HTTP/1.1 200 OK\r\n"
                            + "Content-Type: application/json\r\n"
                            + "\r\n" + "{\"result\":\"" + computationResult + "\"}\n";
                } else {
                    outputLine = getDefaultResponse();
                }
                out.println(outputLine);
                out.close();
                in.close();
                clientSocket.close();
            }
            serverSocket.close();
        }

        public static String getDefaultResponse(){
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
                    "Methot not found" +
                    "\n" +
                    "    </body>\n" +
                    "</html>";
            return htmlcode;
        }

        public static URI getReqURI(String reqURI) throws URISyntaxException{
            System.out.println(reqURI);
            String ruri = reqURI.split(" ")[1];
            System.out.println(ruri);
            System.out.println(ruri.split("=")[1]);

            return new URI(ruri);
        }



        public static String computeMathCommand(String uri) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            String command = uri.split("=")[1];


            Class c = Math.class;
            Class[] parameterTypes={double.class};
            Method m =c.getDeclaredMethod(command.split("\\(")[0], parameterTypes);

            String number = command.split("[\\(\\)]")[1];

            double numero = Double.parseDouble(number);

            Object[] params = {numero};
            String resp = m.invoke(null, params).toString();
            return resp;
        }

        public static double[] BubbleSort(double[] list) {
            int n = list.length;
            boolean swapped;

            for (int i = 0; i < n - 1; i++) {
                swapped = false;

                for (int j = 0; j < n - 1 - i; j++) {
                    if (list[j] > list[j + 1]) {
                        Double temp = list[j];
                        list[j] = list[j + 1];
                        list[j + 1] = temp;
                        swapped = true;
                    }
                }

                if (!swapped) {
                    break;
                }
            }
            return list;
        }
    }