/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package escuelaing.edu.co.spark;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author david.pineros-c
 */
public class HelloWorld {
    
    public static Map<String, Service> services = new HashMap();
    
    public static void main(String[] args) {
        get("/hello", (req, resp) -> "Hello " + req);
        get("/pi", (req, resp) -> {return String.valueOf(Math.PI);});      
        
        String requestedUrl = "/app/hello?name=Pedro";
        
        System.out.println(services.get("/pi").getValue("", ""));
        System.out.println(services.get("/hello").getValue("", ""));
    }
  
    
    public static void get(String url, Service service){
        services.put(url, service);
    }
}