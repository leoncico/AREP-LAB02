package escuelaing.edu.co.spark;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;

class ClientHandler implements Runnable {
    private Socket clientSocket;
 
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }
 
    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedOutputStream dataOut = new BufferedOutputStream(clientSocket.getOutputStream())) {

            String requestLine = in.readLine();
            if (requestLine == null) return;
            String[] tokens = requestLine.split(" ");
            String method = tokens[0];
            String URI = tokens[1];
 
            printRequestHeader(requestLine,in);


            if (method.equals("GET") && !URI.startsWith("/app")) {
                handleGetRequest(URI, out, dataOut);
            } 
            else{
                try {
                    String responseBody = callService(URI, method);
                    int contentLength = responseBody.getBytes().length;
    
                    out.print("HTTP/1.1 200 OK\r\n");
                    out.print("Content-Type: application/json\r\n");
                    out.print("Content-Length: " + contentLength + "\r\n");
                    out.print("\r\n");
                    out.print(responseBody);
                    out.flush();
                } catch (Exception e) {
                    out.print("HTTP/1.1 500 Internal Server Error\r\n");
                    out.print("Content-Type: text/html\r\n");
                    out.print("\r\n");
                    out.print("<html><body><h1>Internal Server Error</h1></body></html>");
                    out.flush();
                    e.printStackTrace();
                }
            }
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void printRequestHeader(String requestLine, BufferedReader in) throws IOException{
        System.out.println("Request line: " + requestLine);
        String inputLine = "";
        while((inputLine = in.readLine()) != null){
            System.out.println("hearder: " + inputLine);
            if(!in.ready()){
                break;
            }
            
            
        }
    }
 
    private void handleGetRequest(String fileRequested, PrintWriter out, BufferedOutputStream dataOut) throws IOException {
        File file = new File(Rutine.WEB_ROOT, fileRequested);
        int fileLength = (int) file.length();
        String content = getContentType(fileRequested);
 
        if (file.exists()) {
            byte[] fileData = readFileData(file, fileLength);
            out.println("HTTP/1.1 200 OK");
            out.println("Content-type: " + content);
            out.println("Content-length: " + fileLength);
            out.println(); 
            out.flush();
            dataOut.write(fileData, 0, fileLength);
            dataOut.flush();
        } else {
            out.println("HTTP/1.1 404 Not Found");
            out.println("Content-type: text/html");
            out.println();
            out.flush();
            out.println("<html><body><h1>File Not Found</h1></body></html>");
            out.flush();
        }
    }
 
    private String callService(String URI, String method) {
        RESTService restService = Spark.findHandler(queryClean(URI), method);
        return restService.response(URI);
    }

    private String queryClean(String uri){
        int index = uri.indexOf('?');
        if (index != -1) {
            return uri.substring(0, index);
        }
        return uri;
    }

    private String getContentType(String fileRequested) {
        if (fileRequested.endsWith(".html")) return "text/html";
        else if (fileRequested.endsWith(".css")) return "text/css";
        else if (fileRequested.endsWith(".js")) return "application/javascript";
        else if (fileRequested.endsWith(".png")) return "image/png";
        else if (fileRequested.endsWith(".jpg")) return "image/jpeg";
        return "text/plain";
    }
 
    private byte[] readFileData(File file, int fileLength) throws IOException {
        FileInputStream fileIn = null;
        byte[] fileData = new byte[fileLength];
        try {
            fileIn = new FileInputStream(file);
            fileIn.read(fileData);
        } finally {
            if (fileIn != null) fileIn.close();
        }
        return fileData;
    }
}
