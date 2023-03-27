package com.example.repository;

import jakarta.inject.Singleton;

import java.sql.*;

@Singleton
public class DBConnect {

    private final Connection conn;

    public DBConnect() {
        this.conn = initDatabase();
    }

    public Connection getConnection() {
        return this.conn;
    }

    public void closeConnection() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection initDatabase() {
        String url = "jdbc:sqlite:inventory.db";

        // Create table if it doesn't exist
        // SQL statement
        String sql = "CREATE TABLE IF NOT EXISTS inventory ("
                + "id text PRIMARY KEY, "
                + "name text NOT NULL, "
                + "quantity integer, "
                + "reorderPoint integer, "
                + "supplier text, "
                + "unitCost real,"
                + "marketingDescription text"
                + ");";

        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            DatabaseMetaData meta = conn.getMetaData();
            System.out.println("The driver name is " + meta.getDriverName());
            System.out.println("A new database has been created.");
            stmt.execute(sql);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
