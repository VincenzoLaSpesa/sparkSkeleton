/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thedarshan.sparkSkeleton;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import spark.Request;
import spark.Spark;
/**
 *
 * @author Darshan
 */
public class Main {
    public static String staticDir="/static";
    

    
    public static void main(String[] args) throws IOException, FileNotFoundException {        
        final String dir = System.getProperty("user.dir");
        System.out.println("Starting project from " + dir);
        
        Helper.bindSpark(8080, staticDir);
        LinkedList<AbstractSparkApplication> apps= new LinkedList<>();
        apps.add(new DummyApp("/dummy", System.getProperty("user.dir")));
        
        apps.stream().forEach((asa) -> {
            try {
                asa.init();
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Unable to start application" + asa.getClass().toString());
            }
        });
        
       
        Spark.get("/hello", (req, res) -> "Hello World");        
        
    }    
}
