/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firstconnection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
    final String URL = "jdbc:derby:UsersDB;create=true";
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
            ResultSet rs = dbmd.getTables(null, "APP", "USERS", null);
            if(! rs.next()) {
                createStatement.execute("create table users(name varchar(20), address varchar(20))");
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a tabla nem hozhato letre");
            System.out.println(""+ex);
        }
    }
    
    public void addUser(String name, String address) {
        try {
//            String sql = "insert into users values ('"+name+"', '"+address+"')";
//            this.createStatement.execute(sql);
            String sql = "insert into users values (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a user hozzaadasakkor");
            System.out.println(""+ex);
        }
    }
    
    
    public void showAllUsers() {
        String sql = "select * from users";
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            while(rs.next()) {
                String name = rs.getString("name");
                String address = rs.getString("address");
                System.out.println("Neve: "+name+" cime: "+address);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van az userek lekerdezesekor");
            System.out.println(""+ex);
        }
    }
    
    public void showUsersMeta() {
        String sql = "select * from users";
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        try {
            rs = createStatement.executeQuery(sql);
            rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for(int x= 1; x <= columnCount; x++) {
                System.out.println(" | "+rsmd.getColumnName(x) + " | ");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
