package org.main.DbConnection;

import java.sql.*;
import java.util.Random;

public class DbFunctions {

    public void createTable(Connection conn, String table_name){
        Statement statement;
        try{
            String query = String.format("create table %s (id Serial, name varchar(200), srn varchar(7), degree varchar(100), primary key(empid));", table_name);
            statement=conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Table Created Successfully");
        } catch(Exception e){
            System.out.println("Error: " + e);
        }
    }

    public static String generateStudentRegisterNumber() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sequence = new StringBuilder();
        Random rand = new Random();

        for (int i = 0; i < 7; i++) {
            sequence.append(characters.charAt(rand.nextInt(characters.length())));
        }

        return sequence.toString();
    }

    public static boolean sequenceExistsInDatabase(String table_name, String column, String sequence, Connection connection) throws SQLException {
        String query = String.format("SELECT COUNT(*) FROM %s WHERE %s = ?", table_name, column);
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, sequence);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public static String generateUniqueSequence(Connection connection) throws SQLException {
        String sequence;
        do {
            sequence = generateStudentRegisterNumber();
        } while (sequenceExistsInDatabase("students", "srn", sequence, connection));

        return sequence;
    }

    public void insert_row(Connection conn, String table_name, String name, String degree) throws SQLException {
        String uniqueSequence = generateUniqueSequence(conn);
        Statement statement;
        try{
            String query = String.format("insert into %s(name, srn, degree) values('%s', '%s', '%s');", table_name, name, uniqueSequence, degree);
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Row inserted");
        } catch(Exception e){
            System.out.println("Error: " + e);
        }
    }

    public void read_data(Connection conn, String table_name){
        Statement statement;
        ResultSet rs;
        try{
            String query = String.format("select * from %s", table_name);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            while(rs.next()){
                System.out.print(rs.getString("id") + " ");
                System.out.print(rs.getString("name") + " ");
                System.out.print(rs.getString("srn") + " ");
                System.out.println(rs.getString("degree"));
            }
        } catch(Exception e){
            System.out.println("Error: " + e);
        }
    }

    public int count_data(Connection conn, String table_name){
        Statement statement;
        ResultSet rs;
        int count = 0;
        try{
            String query = String.format("select * from %s", table_name);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            while(rs.next()){
                count++;
            }
        } catch(Exception e){
            System.out.println("Error: " + e);
        }
        return count;
    }

    public String get_name_by_id(Connection conn, String table_name, int id){
        Statement statement;
        ResultSet rs;
        String name = "";
        try{
            String query = String.format("select * from %s", table_name);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            while(rs.next()){
                if(rs.getString("id").equalsIgnoreCase(String.valueOf(id))){
                    name = rs.getString(("name"));
                }
            }
        } catch(Exception e){
            System.out.println("Error: " + e);
        }
        return name;
    }

    public int get_id_by_name(Connection conn, String table_name, String name){
        Statement statement;
        ResultSet rs;
        int id = 0;
        try{
            String query = String.format("select * from %s", table_name);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            while(rs.next()){
                if(rs.getString("name").equalsIgnoreCase(name)){
                    id = rs.getInt(("id"));
                }
            }
        } catch(Exception e){
            System.out.println("Error: " + e);
        }
        return id;
    }

    public void update_name(Connection conn, String table_name, int id, String new_name){
        Statement statement;
        try{
            String query = String.format("update %s set name='%s' where id=%s", table_name, new_name, id);
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Data updated");
        } catch(Exception e){
            System.out.println("Error: " + e);
        }
    }

    public void update_degree(Connection conn, String table_name, int id, String new_degree){
        Statement statement;
        try{
            String query = String.format("update %s set degree='%s' where id=%s", table_name, new_degree, id);
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Data updated");
        } catch(Exception e){
            System.out.println("Error: " + e);
        }
    }

    public void delete_row_by_id(Connection conn, String table_name, int id){
        Statement statement;
        try{
            String query = String.format("delete from %s where id=%s", table_name, id);
            statement=conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Data deleted");
        } catch(Exception e){
            System.out.println("Error: " + e);
        }
    }
}
