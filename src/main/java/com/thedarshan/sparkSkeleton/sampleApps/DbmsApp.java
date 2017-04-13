/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thedarshan.sparkSkeleton.sampleApps;

import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.thedarshan.sparkSkeleton.AbstractSparkApplication;
import com.thedarshan.sparkSkeleton.Helper;
import com.thedarshan.sparkSkeleton.dbms.DatabaseHelper;
import com.thedarshan.sparkSkeleton.dbms.DatabaseInterface;
import com.thedarshan.sparkSkeleton.sampleApps.entities.Iris;
import freemarker.template.Configuration;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import spark.ModelAndView;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

/**
 *
 * @author Darshan
 */
public class DbmsApp extends AbstractSparkApplication {

    List<Map<String, Double>> dataset;
    String templateDir;
    Configuration conf;
    FreeMarkerEngine templateEngine;

    public DbmsApp(String mountPoint, String dataDir) {
        super(mountPoint, dataDir);
        templateDir = FileSystems.getDefault().getPath(dataDir, "templates").toString();
        conf = new Configuration();
    }

    @Override
    public void init() throws IOException, SQLException {
        System.out.println(String.format("Mounting %s in %s", this.getClass().getName(), mountPoint));
        System.out.println("Working Directory is " + System.getProperty("user.dir"));
        System.out.println("Data directory is " + this.dataDirectory);
        System.out.println("Template directory is " + this.templateDir);

        conf.setDirectoryForTemplateLoading(new File(templateDir));
        templateEngine = new FreeMarkerEngine(conf);

        initDatabase();

        Spark.get(mountPoint + "/hello", (req, res) -> hello());
        Map map = new HashMap();
        map.put("title", "Iris Dataset");
        map.put("dataset", getData());        

        Spark.get(mountPoint + "/iris", (req, res) -> new ModelAndView(map, "iris_nested.ftl"), templateEngine);

    }

    private boolean initDatabase() {
        DatabaseInterface dbi = DatabaseInterface.getSingleton();
        ConnectionSource cs;
        try {
            Set<String> s = DatabaseHelper.getTables(dbi.getConnection("Iris"));
            cs = dbi.getConnectionSource("Iris");
            s.stream().forEach((t) -> {
                System.out.println("->\t " + t);
            });
            
            DatabaseHelper.prepareTable("iris", Iris.class, cs, s);
            
            Dao<Iris, String> dao = DaoManager.createDao(cs, Iris.class);
            Iris[] data = loadData();
            int n = 0;
            for (Iris obj : data) {
                obj.id = n++;
                dao.createOrUpdate(obj);
            }
        } catch (IOException | SQLException ex) {
            Logger.getLogger(DbmsApp.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public void shutdown() {
        System.out.println(String.format("Unmounting %s from %s", this.getClass().getName(), mountPoint));
        try {
            DatabaseInterface.getSingleton().closeAllConnections();
        } catch (SQLException ex) {
            Logger.getLogger(DbmsApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DbmsApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * A sample method that builds simple html from code without external
     * template
     *
     * @return an html
     */
    private String hello() {
        return String.format("<html><body><h1>Hello there!</h1><p>This is a sample application, its name is <strong>%s</strong></p></body></html>", this.getClass().getName());
    }

    /**
     * Load the Iris dataset from a Json file into a LinkedList<Iris>
     *
     * @
     *
     * throws IOException
     */
    private Iris[] loadData() throws IOException {
        String buffer = Helper.readFile(this.dataDirectory + "/static/iris.json");
        Gson gson = new Gson();
        return gson.fromJson(buffer, Iris[].class);
    }

    private List<Iris> getData() throws SQLException {
        ConnectionSource cs=DatabaseInterface.getSingleton().getConnectionSource("Iris");
        Dao<Iris, String> dao = DaoManager.createDao(cs, Iris.class);
        return dao.queryForAll();        
    }
}
