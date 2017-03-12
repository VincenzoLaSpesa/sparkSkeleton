package com.thedarshan.sparkSkeleton.sampleApps;

import com.thedarshan.sparkSkeleton.dbms.DatabaseInterface;
import com.google.gson.Gson;
import com.thedarshan.sparkSkeleton.AbstractSparkApplication;
import com.thedarshan.sparkSkeleton.Helper;
import freemarker.template.Configuration;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import spark.ModelAndView;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

/**
 *
 * @author Darshan
 */
public class DummyApp extends AbstractSparkApplication {

    List<Map<String, Double>> dataset;
    String templateDir;
    Configuration conf;
    FreeMarkerEngine templateEngine;
    
    public DummyApp(String mountPoint, String dataDir)  {
        super(mountPoint, dataDir);
        templateDir=FileSystems.getDefault().getPath(dataDir, "templates").toString();
        conf= new Configuration();
    }

    @Override
    public void init() throws IOException {
        System.out.println(String.format("Mounting %s in %s", this.getClass().getName(), mountPoint));
        System.out.println("Working Directory is " + System.getProperty("user.dir"));
        System.out.println("Data directory is " + this.dataDirectory);
        System.out.println("Template directory is " + this.templateDir);

        dataset=loadData();
        conf.setDirectoryForTemplateLoading(new File(templateDir));
        templateEngine= new FreeMarkerEngine(conf);

        Spark.get(mountPoint + "/hello", (req, res) -> hello());
        Map map = new HashMap();
        map.put("title", "Iris Dataset");
        map.put("dataset", dataset);        
        Spark.get(mountPoint +"/iris", (req, res) -> new ModelAndView(map, "iris.ftl"), templateEngine);               
    }

    @Override
    public void shutdown() {
        System.out.println(String.format("Unmounting %s from %s", this.getClass().getName(), mountPoint));
    }

    /**
     * A sample method that builds simple html from code without external template
     *
     * @return an html
     */
    private String hello() {
        return String.format("<html><body><h1>Hello there!</h1><p>This is a sample application, its name is <strong>%s</strong></p></body></html>",this.getClass().getName());
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
