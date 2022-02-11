package com.example.application.views;


import java.sql.Connection;
import java.sql.DriverManager;
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
            statement.executeUpdate("drop table if exists cashflow");
            statement.executeUpdate("create table cashflow (id integer, cash_in real, cash_back real)");
            

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
    
    public boolean writeData(Statement statement, float cash_in, float cash_back) {
        int id = 1;
        try {
            String update = String.format("insert into cashflow values(%i, %i, $i)", id, cash_in, cash_back);
            statement.executeUpdate(update);
            id++;
        } catch (SQLException e) {
            System.out.println("write data failed!");
        }
        return true;
    }

}

