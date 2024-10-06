package org.main.DbConnection;
import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {

    public Connection connect_to_db(){
        Connection conn = null;
        try{
            Class.forName("org.postgresql.Driver");
            String dbname = "Student";
            String user = "postgres";
            String pass = "dbadmin";
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+ dbname, user, pass);
            if(conn!=null){
                System.out.println("Connection Established");
            } else{
                System.out.println("Connection Failed");
            }
        } catch(Exception e){
            System.out.println("Error:" + e);
        }

        return conn;
    }
}
