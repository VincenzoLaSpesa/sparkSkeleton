package com.thedarshan.sparkSkeleton.dbms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author DEVVIK
 */
public class DatabaseInterface {

    public static String databaseName="Dummy";
    
    public DatabaseInterface() {
        try {
            // connect method #1 - embedded driver
            String dbUrl = "jdbc:derby:derby_database/"+databaseName+";create=true";
            Connection conn1 = DriverManager.getConnection(dbUrl);
            if (conn1 != null) {
                System.out.println("Connected to database #1 in " + dbUrl);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
