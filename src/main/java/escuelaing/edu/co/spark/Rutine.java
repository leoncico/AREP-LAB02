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
 
public class Rutine {
    private static final int PORT = 8080;
    private static Rutine instance;
    public static String WEB_ROOT;
    public static final Map<String, RESTService> services = new HashMap();
    public static final ExerciseService exerciseService = new ExerciseService();

    public static Rutine getInstance(){
        if(instance == null){
            instance = new Rutine();
        }
        return instance;
    }

    public static void main(String[] args) throws IOException {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        ServerSocket serverSocket = new ServerSocket(PORT);

        Spark.staticFileLocation("target/classes/webroot");

        Spark.get("/app/exercises", (req) ->{
            ListExerciseService listExerciseService =  new ListExerciseService(exerciseService);
            String response = listExerciseService.response(req);
            return response;
        });

        //Falta post
        Spark.post("/app/addExercise", (req) ->{
            AddExerciseService addExerciseService = new AddExerciseService(exerciseService);
            String response = addExerciseService.response(req);
            return response;
        });


        while (true) {
            Socket clientSocket = serverSocket.accept();
            threadPool.submit(new ClientHandler(clientSocket));
        }

    }

    public static void setStaticFileLocation(String webRoot){
        WEB_ROOT = webRoot;
    }

}
