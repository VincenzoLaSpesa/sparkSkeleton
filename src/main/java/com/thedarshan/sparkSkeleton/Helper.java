package com.thedarshan.sparkSkeleton;


import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import org.apache.tools.ant.util.FileUtils;
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
        Path p= FileSystems.getDefault().getPath(staticFilesLocation);
        if(Files.exists(p)){
            Spark.staticFiles.externalLocation(p.toString());
        }else{ // percorso relativo?
            p= FileSystems.getDefault().getPath(System.getProperty("user.dir"), staticFilesLocation);
            Spark.staticFiles.externalLocation(p.toString());
        }                              
        Spark.staticFiles.expireTime(600L);
        System.out.println("Spark static files bound to: "+ p.toAbsolutePath().toString());
        Spark.exception(Exception.class, (exception, request, response) -> {
            exception.printStackTrace();
        });        
    }

    public static boolean shouldReturnHtml(Request request) {
        String accept = request.headers("Accept");
        return accept != null && accept.contains("text/html");
    }

}
