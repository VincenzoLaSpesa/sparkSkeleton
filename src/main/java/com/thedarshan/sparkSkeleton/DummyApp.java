/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thedarshan.sparkSkeleton;

import com.google.gson.Gson;
import static j2html.TagCreator.body;
import static j2html.TagCreator.h1;
import static j2html.TagCreator.p;
import static j2html.TagCreator.strong;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.AbstractList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import spark.ModelAndView;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

/**
 *
 * @author Darshan
 */
public class DummyApp extends AbstractSparkApplication {

    List<Map<String, Double>> dataset;
    String templateDir;
    
    public DummyApp(String mountPoint, String dataDir) {
        super(mountPoint, dataDir);
        templateDir=FileSystems.getDefault().getPath(dataDir, "templates").toString();
    }

    @Override
    public void init() throws IOException {
        System.out.println(String.format("Mounting %s in %s", this.getClass().getName(), mountPoint));
        System.out.println("Working Directory is " + System.getProperty("user.dir"));
        System.out.println("Data directory is " + this.dataDirectory);
        System.out.println("Template directory is " + this.templateDir);

        dataset=loadData();

        Spark.get(mountPoint + "/hello", (req, res) -> hello());
        Map map = new HashMap();
        map.put("title", "Iris Dataset");
        map.put("dataset", dataset);        
        Spark.get(mountPoint +"/iris", (req, res) -> new ModelAndView(map, "iris.html"), new HandlebarsTemplateEngine(this.templateDir));        
    }

    @Override
    void shutdown() {
        System.out.println(String.format("Unmounting %s from %s", this.getClass().getName(), mountPoint));
    }

    /**
     * A sample method that uses j2html to build simple html from code without
     * external template
     *
     * @return an html
     */
    private String hello() {
        return body().with(
                h1("Hello there!"),
                p("This is a sample application, its name is "),
                strong().with(p(this.getClass().getName()))
        ).render();
    }
    
   

    /**
     * Load the Iris dataset from a Json file into a LinkedList<HashMap<String, Double>>
     * @throws IOException 
     */
    private List<Map<String, Double>> loadData() throws IOException {
        LinkedList<HashMap<String, Double>> obj= new LinkedList<>();
        String buffer = Helper.readFile(this.dataDirectory+"/static/iris.json");       
        Gson gson = new Gson();
        return gson.fromJson(buffer, obj.getClass());
    }
}
