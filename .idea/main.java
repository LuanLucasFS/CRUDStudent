package org.main;

import org.main.DbConnection.DbConnection;
import org.main.DbConnection.DbFunctions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import static java.lang.System.exit;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static DbConnection dbConn = new DbConnection();
    public static DbFunctions db = new DbFunctions();
    public static Connection conn = dbConn.connect_to_db();
    public static String table_name = "students";


    public static void menu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                \nChoose an option:\
                
                1-Add student\
                
                2-Show students\
                
                3-Update student\
                
                4-Remove student\
                
                0-Exit""");
        int opt = scanner.nextInt();
        scanner.nextLine();
        switch (opt) {
            case 1:
                addStudent();
                break;
            case 2:
                read();
                break;
            case 3:
                update();
                break;
            case 4:
                remove();
                break;
            case 0:
                exit(0);
            default:
                System.out.println("\nUnexpected value: " + opt);
        }
    }

    public static void read(){
        db.read_data(conn, table_name);
    }

    public static void addStudent() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nInsert the student's name:");
        String name = scanner.nextLine();
        System.out.println("\nWhat is the student university degree?");
        String degree = scanner.nextLine();
        System.out.println("\nName: " + name + "\nDegree: " + degree + "\nStudent added successfully");
        db.insert_row(conn, table_name, name, degree);
    }

    public static void upd_name(){
        Scanner scanner = new Scanner(System.in);
        int opt;
        int id;
        String old_name;
        String new_name;
        System.out.println("""
                \nSearch by: \
                
                1- ID\
                
                2- Name""");
        opt=scanner.nextInt();
        scanner.nextLine();
        switch (opt) {
            case 1:
                read();
                do {
                    System.out.println("\nInsert the ID of the student you want to update: ");
                    id = scanner.nextInt();
                    scanner.nextLine();
                }while(id < 0 && id > db.count_data(conn, table_name));
                old_name = db.get_name_by_id(conn, table_name, id);
                System.out.printf("\nInsert new name for %s:%n", old_name);
                new_name = scanner.nextLine();
                db.update_name(conn, table_name, id, new_name);
                break;
            case 2:
                read();
                System.out.println("\nInsert the name of the student you want to update: ");
                old_name = scanner.nextLine();
                id = db.get_id_by_name(conn, table_name, old_name);
                System.out.printf("\nInsert new name for %s:%n", old_name);
                new_name = scanner.nextLine();
                db.update_name(conn, table_name, id, new_name);
                break;
        }
    }

    public static void upd_degree(){
        Scanner scanner = new Scanner(System.in);
        int opt;
        int id;
        String name;
        String degree;
        System.out.println("""
                \nSearch by: \
                
                1- ID\
                
                2- Name""");
        opt=scanner.nextInt();
        scanner.nextLine();
        switch (opt) {
            case 1:
                read();
                do {
                    System.out.println("\nInsert the ID of the student you want to update: ");
                    id = scanner.nextInt();
                    scanner.nextLine();
                }while(id < 0 && id > db.count_data(conn, table_name));
                name = db.get_name_by_id(conn, table_name, id);
                System.out.printf("\nInsert new degree for %s:%n", name);
                degree = scanner.nextLine();
                db.update_degree(conn, table_name, id, degree);
                break;
            case 2:
                read();
                System.out.println("\nInsert the name of the student you want to update: ");
                name = scanner.nextLine();
                id = db.get_id_by_name(conn, table_name, name);
                System.out.printf("\nInsert new degree for %s:%n", name);
                degree = scanner.nextLine();
                db.update_degree(conn, table_name, id, degree);
                break;
        }
    }

    public static void update() {
        Scanner scanner = new Scanner(System.in);
        int opt;
        System.out.println("""
                \nUpdate: \
                
                1- Name \
                
                2- Degree \
                """);
        opt = scanner.nextInt();
        scanner.nextLine();
        switch (opt) {
            case 1:
                upd_name();
                break;
            case 2:
                upd_degree();
                break;
        }
    }

    public static void remove(){
        Scanner scanner = new Scanner(System.in);
        int opt;
        int id;
        System.out.println("""
                \nRemove by: \
                
                1- ID \
                
                2- Name""");
        opt = scanner.nextInt();
        scanner.nextLine();
        switch (opt){
            case 1:
                read();
                do {
                    System.out.println("\nInsert the ID of the student you want to remove: ");
                    id = scanner.nextInt();
                    scanner.nextLine();
                }while(id < 0 && id > db.count_data(conn, table_name));
                db.delete_row_by_id(conn, table_name, id);
                break;
            case 2:
                read();
                System.out.println("Insert the name of the student you want to remove: ");
                String name = scanner.nextLine();
                id = db.get_id_by_name(conn, table_name, name);
                db.delete_row_by_id(conn, table_name, id);
                break;
        }
    }

    public static void main(String[] args) throws SQLException {

        do {
            menu();
        } while (true);
    }
}