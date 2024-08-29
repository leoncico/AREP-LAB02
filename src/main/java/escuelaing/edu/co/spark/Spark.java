package escuelaing.edu.co.spark;

import java.util.HashMap;
import java.util.Map;

public class Spark {
    private static Spark instance;
    public static final Map<String, RESTService> getServices = new HashMap();
    public static final Map<String, RESTService> postServices = new HashMap();

    public static Spark getInstance(){
        if(instance == null){
            instance = new Spark();
        }
        return instance;
    }

    public static RESTService findHandler(String path, String method) {
        if ("GET".equalsIgnoreCase(method)) {
            return getInstance().getServices.get(path);
        } else if ("POST".equalsIgnoreCase(method)) {
            return getInstance().postServices.get(path);
        }else {
            return null;
        }
    }

    public static void get(String path, RESTService service) {
        getServices.put(path, service);
    }

    public static void post(String path, RESTService service) {
        postServices.put(path, service);
    }

    public static void staticFileLocation(String path) {
        Rutine.getInstance().setStaticFileLocation(path);
    }

}
