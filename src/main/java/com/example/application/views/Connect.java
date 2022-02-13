package com.example.application.views;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {
    public static void connect(float cash_in, float cash_back) {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:db/transactions.db";
            // create a connection to the database
            conn = DriverManager.getConnection("jdbc:sqlite:transactions.db");
            
            System.out.println("Connection to SQLite has been established.");
            /** 
            CREATE TABLE CASHFLOW(
                primary_key INT PRIMARY KEY NOT NULL,
                amount INT NOT NULL,
                cash_in REAL,
                cash_out REAL
            );**/
            Statement statement = conn.createStatement();
            // statement.executeUpdate("drop table if exists cashflow");
            // statement.executeUpdate("create table cashflow (cash_in real, cash_back real)");
            writeData(statement, cash_in, cash_back);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    public static boolean writeData(Statement statement, float cash_in, float cash_back) {
        int id = 1;
        try {
            // write cashflow into db after buy button was pressed

            System.out.println("id: " + id +" in: "+cash_in+" back: " + cash_back);
            String update = String.format("insert into cashflow values(%f, %f)", cash_in, cash_back);
            statement.executeUpdate(update);
            id++;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            System.out.println("write data failed!");
            return false;
        }
        return true;
    }

    // returns the total value in and the total value out
    // array [0] is in, array [1] is out
    public static float[] totalBackIn() {
        float inOut[] = new float[2];

        try {
            // connection to the database
            Connection conn = DriverManager.getConnection("jdbc:sqlite:transactions.db");
            Statement statement = conn.createStatement();

            // fetch data
            ResultSet resultSet = statement.executeQuery("SELECT * FROM cashflow");
            
            // loop through the result set
            float sumIn = 0, sumOut = 0;
            while (resultSet.next()) {
                sumIn += resultSet.getFloat("cash_in");
                sumOut += resultSet.getFloat("cash_back");
            }
            inOut[0] = sumIn;
            inOut[1] = sumOut;

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return inOut;
    }

}
