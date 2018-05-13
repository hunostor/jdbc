/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firstconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hunostor
 */
public class DB {
    final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    final String URL = "jdbc:derby:sampleDB;create=true";
    final String USERNAME = "";
    final String PASSWORD = "";
    
    public DB() {
        try {
            Connection conn = DriverManager.getConnection(URL);
             System.out.println("a hid letrejott");
        } catch (SQLException ex) {
            System.out.println("Valami baj van");
            System.out.println(""+ex);
        }
    }
}
