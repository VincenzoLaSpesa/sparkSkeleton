package com.thedarshan.sparkSkeleton;

import com.thedarshan.sparkSkeleton.sampleApps.DbmsApp;
import com.thedarshan.sparkSkeleton.sampleApps.DummyApp;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import spark.Spark;
/**
 * Copyright (c) 2017 Vincenzo La Spesa, This software is released under the MIT License
 * @author Darshan
 */
public class Main {
    public static String staticDir="/static";
    

    public static void main(String[] args) throws IOException, FileNotFoundException {        
        
        Helper.changeLocale("it", "IT"); // For numbers and date formatting
        
        final String dir = System.getProperty("user.dir");
        System.out.println("Starting server from " + dir);        
        LinkedList<AbstractSparkApplication> apps= new LinkedList<>();
        apps.add(new DummyApp("/dummy", System.getProperty("user.dir")));       
        apps.add(new DbmsApp("/dbms", System.getProperty("user.dir")));       
        Helper.easyBind(8080, staticDir, apps);
        Spark.get("/hello", (req, res) -> "Hello World");  

    }    
}
