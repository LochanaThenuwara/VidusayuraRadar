/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASN
 */

public class DBFacade {
    public static Connection conn = null;
    public static DBFacade db;
    int valid = 0;
    public static String uName;
    public static String userid;
    
    public DBFacade() {
        final String database = "vidusayura";
        final String host = "192.248.22.133:3306";
        final String userName = "root";        
        final String password = "colombo007";
        String url = "jdbc:mysql://"+host+"/"+database;        
        try {
        //Setup the connection with the DB
            Class.forName("com.mysql.jdbc.Driver");            
            DBFacade.conn = (Connection)DriverManager.getConnection(url,userName,password);
        }catch (Exception ex) {
            valid =-1;
            Logger.getLogger(DBFacade.class.getName()).log(Level.SEVERE, null, ex);        
        }
    }
   
    public static synchronized DBFacade getDbCon() {
        if ( db == null ) {
            db = new DBFacade();
        }
        return db; 
    }
    
    public int checkLogin(String uname, String pword){
        ResultSet rs = null;
        Statement stmt  = null;
        try {
            String sql = "SELECT harbour FROM sessions ORDER BY id DESC LIMIT 1";
            if(db.conn==null){
                return valid;
            }
            stmt  = db.conn.createStatement();
            rs = stmt.executeQuery(sql);  
            System.out.println(rs.getRow());
            
            while(rs.next()){
                
            }
            rs.close();
            stmt.close();                    
        } catch (SQLException ex) {
            Logger.getLogger(DBFacade.class.getName()).log(Level.SEVERE, null, ex);
            return valid;
        }
        return valid;    
    }     
}
