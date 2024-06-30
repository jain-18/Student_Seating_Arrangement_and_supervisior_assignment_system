
package com.mycompany.student;

import java.sql.*;

import java.sql.Connection;

import java.sql.Connection;

public class SupervisorShiftTableCreation{
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/studentdata?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String username = "user";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            String TABLE_NAME = "supervisor_shifts";
            if (doesTableExist(connection, TABLE_NAME)) {
                dropTable(connection, TABLE_NAME);
            }

            StringBuilder createTableQuery = new StringBuilder("CREATE TABLE " + TABLE_NAME +
                    "(sup_id INT AUTO_INCREMENT PRIMARY KEY, supervisor_name VARCHAR(255), total_duties int, ");

            // Add columns for shifts from 1 to 15
            for (int i = 1; i <= 15; i++) {
                createTableQuery.append("shift").append(i).append(" VARCHAR(50), ");
            }

            // Remove the trailing comma and space from the query
            createTableQuery.setLength(createTableQuery.length() - 2);

            // Finish constructing the create table query
            createTableQuery.append(")");

            // Execute the dynamic SQL query to create the new table
            Statement createTableStatement = connection.createStatement();
            createTableStatement.executeUpdate(createTableQuery.toString());
            connection.createStatement().executeUpdate("INSERT INTO supervisor_shifts (supervisor_name) SELECT supervisor_name FROM supervisor");

            System.out.println("Table 'supervisor_shifts' created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Check if the table exists
    private static boolean doesTableExist(Connection connection, String tableName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("SHOW TABLES LIKE '" + tableName + "'");
            return statement.getResultSet().next();
        }
    }

    // Drop the table
    private static void dropTable(Connection connection, String tableName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE " + tableName);
            System.out.println("Table dropped successfully.");
        }
    }
}
