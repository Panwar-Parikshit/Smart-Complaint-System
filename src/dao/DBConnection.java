package dao;

import java.sql.Connection; // for connection to database
import java.sql.DriverManager; // helps connect java to mysql

public class DBConnection {

    public static Connection getConnection() { //returns a connection object which is used everywhere in the project 
        try {
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/complaint_system",
                "root", // mysql username
                "akshat@1"
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}