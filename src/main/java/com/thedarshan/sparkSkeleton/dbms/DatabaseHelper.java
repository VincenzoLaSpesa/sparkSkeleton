/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thedarshan.sparkSkeleton.dbms;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.TreeSet;

/**
 *
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
}
