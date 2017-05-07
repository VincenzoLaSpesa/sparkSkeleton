/*
 * Copyright (c) 2017 Vincenzo La Spesa, This software is released under the MIT License
 */

package com.thedarshan.sparkSkeleton.dbms;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Darshan
 */
public abstract class DatabaseHelper {

    public static Set<String> getTables(Connection c) throws SQLException {
        Set<String> s = new TreeSet<>();
        DatabaseMetaData dbmd = c.getMetaData();
        String[] types = {"TABLE"};
        ResultSet rs = dbmd.getTables(null, null, "%", types);
        while (rs.next()) {
            //System.out.println(rs.getString("TABLE_NAME"));
            s.add(rs.getString("TABLE_NAME"));
        }
        return s;
    }
    
    public static void prepareTable(String tablename, Class dataClass , ConnectionSource cs, Set<String> tableSet) throws SQLException{
            if (!tableSet.contains(tablename.toUpperCase())) {
                System.out.println("Creating table for " + dataClass.getName());
                int x = TableUtils.createTable(cs, dataClass);
                System.out.println("->\t " + x);
            }
            TableUtils.clearTable(cs, dataClass);    
    }    
}
