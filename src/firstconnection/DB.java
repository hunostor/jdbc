/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firstconnection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    
    // Create connection (bridge)
    Connection conn = null;
    Statement createStatement = null;

    
    public DB() {
        // Try go on life
        try {
            this.conn = DriverManager.getConnection(URL);
             System.out.println("a hid letrejott");
        } catch (SQLException ex) {
            System.out.println("Valami baj van");
            System.out.println(""+ex);
        }
        
        // if on life, create Truck
        if(conn != null) {
            try {
                this.createStatement = conn.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // Look on, Exists datatable
        DatabaseMetaData dbmd = null;
        try {
            // Look on, database is empty?
            dbmd = conn.getMetaData();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            ResultSet rs1 = dbmd.getTables(null, "APP", "USERS", null);
            if(! rs1.next()) {
                createStatement.execute("create table users(name varchar(20), address varchar(20))");
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a tabla nem hozhato letre");
            System.out.println(""+ex);
        }
    }
    
    public void addUser(String name, String address) {
        try {
            this.createStatement.execute("insert into users values ('Gyula', 'Budapest'), ('Mari', 'Budapest')");
        } catch (SQLException ex) {
            System.out.println("Valami baj van a user hozzaadasakkor");
            System.out.println(""+ex);
        }
    }
}
