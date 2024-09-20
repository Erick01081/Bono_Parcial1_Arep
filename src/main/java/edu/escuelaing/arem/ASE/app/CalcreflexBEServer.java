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
    import java.util.Arrays;

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
            String[] parts = uri.split("=")[1].split("\\(");
            String command = parts[0];
            String[] params = parts[1].replace(")", "").split(",");

            if (command.equals("bbl")) {
                return handleBubbleSort(params);
            } else {
                return handleMathFunction(command, params);
            }
        }

        private static String handleMathFunction(String command, String[] params) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            Class<?> c = Math.class;
            Method m;
            Object[] methodParams;

            if (params.length == 1) {
                Class<?>[] parameterTypes = {double.class};
                m = c.getDeclaredMethod(command, parameterTypes);
                methodParams = new Object[]{Double.parseDouble(params[0])};
            } else if (params.length == 2) {
                Class<?>[] parameterTypes = {double.class, double.class};
                m = c.getDeclaredMethod(command, parameterTypes);
                methodParams = new Object[]{Double.parseDouble(params[0]), Double.parseDouble(params[1])};
            } else {
                throw new IllegalArgumentException("Unsupported number of parameters for math function");
            }

            return m.invoke(null, methodParams).toString();
        }

        private static String handleBubbleSort(String[] params) {
            double[] numbers = new double[params.length];
            for (int i = 0; i < params.length; i++) {
                numbers[i] = Double.parseDouble(params[i]);
            }
            double[] sortedNumbers = BubbleSort(numbers);
            return Arrays.toString(sortedNumbers);
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