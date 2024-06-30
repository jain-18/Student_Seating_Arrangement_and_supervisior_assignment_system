
package com.mycompany.student;



import java.sql.*;

public class SahilTest1 {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/studentdata?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String username = "user";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            // Step 1: Fetch distinct exam dates from the timetable table where there is an exam, sorted in ascending order

            String TABLE_NAME = "datetimes";
            if (doesTableExist(connection, "supervisor_assignments")) {
                dropTable(connection, "supervisor_assignments");
            }
            if (doesTableExist(connection, TABLE_NAME)) {
                dropTable(connection, TABLE_NAME);
            }
            String qq = ("create table datetimes(exam_date date ,start_time varchar(50))");
            connection.createStatement().executeUpdate(qq);
            qq=("insert into datetimes select Date,Start_Time from time_table order by Date asc");
            connection.createStatement().executeUpdate(qq);
//            connection.createStatement().executeUpdate("drop table supervisor_assignments");
            String fetchExamDatesQuery = "select distinct * from datetimes";
            Statement fetchExamDatesStatement = connection.createStatement();

            ResultSet examDatesResultSet = fetchExamDatesStatement.executeQuery(fetchExamDatesQuery);

            // Step 2: Construct dynamic SQL query for creating the new table
            StringBuilder createTableQuery = new StringBuilder("CREATE TABLE supervisor_assignments (sup_id INT AUTO_INCREMENT PRIMARY KEY ,supervisor_name varchar(40), ");

            // Iterate over exam dates and add two columns for each date in the new table
            while (examDatesResultSet.next()) {
                String checkdate ;
                String time = examDatesResultSet.getString(2);
                String examDate = examDatesResultSet.getString("exam_date");
                String[] parts = time.split(":");

//                int hour = Integer.parseInt(parts[0]);
//                if(hour<12){
//                    createTableQuery.append("`").append(examDate).append("_M` varchar(50), ");
//                }
//                if(hour>12){
//                    createTableQuery.append("`").append(examDate).append("_E` varchar(50), ");
//                }


            if(time.equals("09:30 AM")){
                createTableQuery.append("`").append(examDate).append("_M` varchar(50), ");
            }
            else if(time.equals("02:30 PM")){
                createTableQuery.append("`").append(examDate).append("_E` varchar(50), ");
            
            }
            }

            // Remove the trailing comma and space from the query
            createTableQuery.setLength(createTableQuery.length() - 2);

            // Finish constructing the create table query
            createTableQuery.append(")");

            // Step 3: Execute the dynamic SQL query to create the new table
            Statement createTableStatement = connection.createStatement();
            createTableStatement.executeUpdate(createTableQuery.toString());
            connection.createStatement().executeUpdate("ALTER TABLE supervisor_assignments AUTO_INCREMENT = 1");
//            connection.createStatement().executeUpdate("drop table datetimes");
            connection.createStatement().executeUpdate("INSERT INTO supervisor_assignments (supervisor_name) SELECT supervisor_name FROM supervisor");


//            System.out.println("New table 'exam_results' created successfully with dynamic columns.");

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


 