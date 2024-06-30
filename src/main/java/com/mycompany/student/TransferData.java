package com.mycompany.student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

public class TransferData {

    private static final String SOURCE_DB_URL = "jdbc:mysql://localhost:3306/studentdata?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static final String SOURCE_DB_USERNAME = "user";
    private static final String SOURCE_DB_PASSWORD = "password";

    private static final String DESTINATION_DB_URL = "jdbc:mysql://roundhouse.proxy.rlwy.net:24313/railway";
    private static final String DESTINATION_DB_USERNAME = "root";
    private static final String DESTINATION_DB_PASSWORD = "GG3f-Bf53B6DhfAdBDDhG2CbC1BcEAAH";

    public static void main(String[] args) {
        try (Connection sourceConnection = DriverManager.getConnection(SOURCE_DB_URL, SOURCE_DB_USERNAME, SOURCE_DB_PASSWORD);
             Connection destinationConnection = DriverManager.getConnection(DESTINATION_DB_URL, DESTINATION_DB_USERNAME, DESTINATION_DB_PASSWORD)) {

            // Create the destination table (if it doesn't exist)
            createDestinationTable(destinationConnection);

            // Retrieve data from source table
            Statement sourceStatement = sourceConnection.createStatement();
            ResultSet resultSet = sourceStatement.executeQuery("SELECT * FROM supervisor_shifts");

            // Get column names from source table
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            List<String> columnNames = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }

            // Build dynamic INSERT INTO statement
            StringBuilder insertSQL = new StringBuilder("INSERT INTO destination_table (");
            for (String columnName : columnNames) {
                insertSQL.append(columnName).append(", ");
            }
            insertSQL.setLength(insertSQL.length() - 2); // Remove the trailing comma and space
            insertSQL.append(") VALUES (");
            for (int i = 0; i < columnCount; i++) {
                insertSQL.append("?, ");
            }
            insertSQL.setLength(insertSQL.length() - 2); // Remove the trailing comma and space
            insertSQL.append(")");

            // Prepare the dynamic INSERT INTO statement
            PreparedStatement insertStatement = destinationConnection.prepareStatement(insertSQL.toString());

            while (resultSet.next()) {
                // Set values for the insert statement based on the source table columns
                for (int i = 1; i <= columnCount; i++) {
                    insertStatement.setObject(i, resultSet.getObject(i));
                }

                // Execute the insert statement
                insertStatement.executeUpdate();
            }

            // Close resources
            resultSet.close();
            sourceStatement.close();
            insertStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Create the destination table if it doesn't exist
  private static void createDestinationTable(Connection connection) {
    try {
        Statement statement = connection.createStatement();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS destination_table (" +
            "sup_id INT," +
            "supervisor_name VARCHAR(255)," +
            "total_duties INT," +
            "shift1 VARCHAR(50),"+
            "shift2 VARCHAR(50),"+
            "shift3 VARCHAR(50),"+
            "shift4 VARCHAR(50),"+
            "shift5 VARCHAR(50),"+
            "shift6 VARCHAR(50),"+
            "shift7 VARCHAR(50),"+
            "shift8 VARCHAR(50),"+
            "shift9 VARCHAR(50),"+
            "shift10 VARCHAR(50),"+
            "shift11 VARCHAR(50),"+
            "shift12 VARCHAR(50),"+
            "shift13 VARCHAR(50),"+
            "shift14 VARCHAR(50),"+
            "shift15 VARCHAR(50)"+
            ")";
        statement.executeUpdate(createTableSQL);
        statement.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}

