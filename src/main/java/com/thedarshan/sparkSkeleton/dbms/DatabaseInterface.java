/*
 * Copyright (c) 2017 Vincenzo La Spesa, This software is released under the MIT License
 */

package com.thedarshan.sparkSkeleton.dbms;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * @author DEVVIK
 */
public class DatabaseInterface {

    public static DatabaseInterface databaseSingleton;
    public static String databasePath="derby_database";

    private HashMap<String, java.sql.Connection> connections;
    private HashMap<String, ConnectionSource> connectionsources;

    public static DatabaseInterface getSingleton() {
        if (databaseSingleton == null) {
            databaseSingleton = new DatabaseInterface();
        }
        return databaseSingleton;
    }

    public Connection getConnection(String database) throws SQLException {
        if (!connections.containsKey(database)) {
            String dbUrl = "jdbc:derby:"+databasePath+"/" + database + ";create=true";
            Connection conn1 = DriverManager.getConnection(dbUrl);            
            if (conn1 != null) {
                System.out.println("Connected to database " + dbUrl);
                connections.put(database, conn1);
            }else{
                System.err.println("Unable to connect to " + dbUrl);
            }
            return conn1;

        }
        return connections.get(database);
    }
    
    public ConnectionSource getConnectionSource(String database) throws SQLException {
        if (!connectionsources.containsKey(database)) {
            String dbUrl = "jdbc:derby:"+databasePath+"/" + database;
            ConnectionSource connectionSource = new JdbcConnectionSource(dbUrl);
            System.out.println("Connected to database " + dbUrl);
            connectionsources.put(database, connectionSource);
            return connectionSource;
        }
        return connectionsources.get(database);
    }

    
    public int closeAllConnections() throws SQLException, IOException{
        int n=0;
        for(java.sql.Connection c : connections.values()){
            c.close();n++;
        }
        for(ConnectionSource cs: connectionsources.values()){
            cs.close();n++;
        }
        
        return n;
    }
    
    private DatabaseInterface() {
        this.connections = new HashMap<>();
        this.connectionsources = new HashMap<>();
    }
}
