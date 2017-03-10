package com.thedarshan.sparkSkeleton;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import spark.Request;
import spark.Spark;

/**
 *
 * @author Darshan
 */
public abstract class Helper {

    public static String readFile(String pathname) throws IOException {

        File file = new File(pathname);
        StringBuilder fileContents = new StringBuilder((int) file.length());
        Scanner scanner = new Scanner(file);
        String lineSeparator = System.getProperty("line.separator");

        try {
            while (scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine()).append(lineSeparator);
            }
            return fileContents.toString();
        } finally {
            scanner.close();
        }
    }

    public static void bindSpark(int port, String staticFilesLocation) {
        Spark.port(8080);
        //
        Path p = FileSystems.getDefault().getPath(staticFilesLocation);
        if (Files.exists(p)) {
            Spark.staticFiles.externalLocation(p.toString());
        } else { // percorso relativo?
            p = FileSystems.getDefault().getPath(System.getProperty("user.dir"), staticFilesLocation);
            Spark.staticFiles.externalLocation(p.toString());
        }
        Spark.staticFiles.expireTime(600L);
        System.out.println("Spark static files bound to: " + p.toAbsolutePath().toString());
        Spark.exception(Exception.class, (exception, request, response) -> {
            exception.printStackTrace();
        });
    }

    public static void bindApplications(List<AbstractSparkApplication> apps) {
        apps.stream().forEach((asa) -> {
            try {
                asa.init();
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Unable to start application" + asa.getClass().toString());
            }
        });
    }
    
    

    public static boolean shouldReturnHtml(Request request) {
        String accept = request.headers("Accept");
        return accept != null && accept.contains("text/html");
    }

    static void easyBind(int port, String staticFilesLocation, List<AbstractSparkApplication> apps) {
        bindSpark(port, staticFilesLocation);
        bindApplications(apps);
    }

}
