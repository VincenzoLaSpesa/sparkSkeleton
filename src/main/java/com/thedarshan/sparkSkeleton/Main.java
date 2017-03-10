package com.thedarshan.sparkSkeleton;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import spark.Spark;
/**
 *
 * @author Darshan
 */
public class Main {
    public static String staticDir="/static";
    

    public static void main(String[] args) throws IOException, FileNotFoundException {        
        final String dir = System.getProperty("user.dir");
        System.out.println("Starting server from " + dir);        
        LinkedList<AbstractSparkApplication> apps= new LinkedList<>();
        apps.add(new DummyApp("/dummy", System.getProperty("user.dir")));       
        Helper.easyBind(8080, staticDir, apps);
        Spark.get("/hello", (req, res) -> "Hello World");                
    }    
}
