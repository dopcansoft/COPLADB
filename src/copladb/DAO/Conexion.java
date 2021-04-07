/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copladb.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author dopcan
 */
public class Conexion {

    private String dbFile;
    
    public Conexion(String fileDB) {
        dbFile = fileDB;
    }
    
    public Connection conectaDB() {
        Connection conn = null;
        System.out.println("Estableciendo conexion ..");
        try {
            // db parameters
            String url = "jdbc:sqlite:"+dbFile;
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
        return conn;
    }
        
   public void createDataBase() {
        // SQLite connection string
        String url = "jdbc:sqlite:"+dbFile;
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS apartado (\n"
                + "   id_apartado INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n"
                + "    fecha TEXT NULL,\n"
                + "    codigo_cliente INTEGER NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    

}
