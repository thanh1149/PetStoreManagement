package com.petstoremanagement.Global;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    private static String url = "jdbc:mysql://localhost:3306/petstoremanagement";
    private static String user = "springstudent";
    private static String password = "springstudent@";
    private static Connection con;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException: " + e.getMessage());
        }
        return con;
    }

    public static void closeConnection(){
        try {
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
