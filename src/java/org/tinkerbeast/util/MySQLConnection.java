/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tinkerbeast.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author rishin.goswami
 */
public class MySQLConnection {

    private static final String DRIVER = "com.mysql.jdbc.Driver";

    public static Connection getConnection(String url, String uname, String passwd) {

        Connection con = null;

        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(url, uname, passwd);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return con;
    }
}
