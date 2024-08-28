/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package escuelaing.edu.co.spark;

/**
 *
 * @author David Leonardo Pineros
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
 
public class Spark {
    private static final int PORT = 8080;
    public static final String WEB_ROOT = "target/classes/webroot";
    public static final Map<String, RESTService> services = new HashMap();
    public static final ExerciseService exerciseService = new ExerciseService();
    
    public static void get(String path, Function<String, String> lambda) {
        services.put(path, request -> lambda.apply(request));
    }

    public static void main(String[] args) throws IOException {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        ServerSocket serverSocket = new ServerSocket(PORT);
        addServices();
 
        Spark.get("/app/hello1", (request) -> "{\"message\":\"Hello, world!\"}");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            threadPool.submit(new ClientHandler(clientSocket));
        }
    }
    
    private static void addServices(){
        services.put("addExercise", new AddExerciseService(exerciseService));
        services.put("listExerciseService", new ListExerciseService(exerciseService));
    }


}
