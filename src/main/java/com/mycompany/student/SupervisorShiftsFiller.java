
package com.mycompany.student;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SupervisorShiftsFiller {
    public static void main(String[] args) {
         String jdbcUrl = "jdbc:mysql://localhost:3306/studentdata?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String username = "user";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            String selectAssignmentsQuery = "SELECT * FROM supervisor_assignments";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectAssignmentsQuery);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            String cell=null,ShiftCol = null;

            while(resultSet.next()) {
                int j=4;
                for (int i = 4; i <= columnCount; i++) {
                    String supervisor = resultSet.getString("supervisor_name");
                    String check_full = Checkfull(connection,i);
                    String query = ("select `"+check_full+"` from supervisor_assignments where supervisor_name = '"+supervisor+"'");
                    ResultSet rr = connection.createStatement().executeQuery(query);
                    if(rr.next()){
                        cell = rr.getString(1);
                    }
                    if(cell != null) {

                        String[] parts = check_full.split("_");
                        String date = convertDateFormat(parts[0]);
                        String[] datee = date.split("-");
                        String correctDate = datee[0] + "/" + datee[1] + "-" + parts[1];
                        query = ("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'supervisor_Shifts' AND ORDINAL_POSITION = '" + j + "'");
                        rr = connection.createStatement().executeQuery(query);
                        if (rr.next()) {
                            ShiftCol = rr.getString(1);
                        }
                        query = ("UPDATE supervisor_shifts set `" + ShiftCol + "` = '" + correctDate + "' where supervisor_name = '" + supervisor + "'");
                        connection.createStatement().executeUpdate(query);
                        System.out.println("done " + j);
                        j++;
                    }else if(cell == null){
//                        query = ("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'supervisor_Shifts' AND ORDINAL_POSITION = '" + j + "'");
//                        rr = connection.createStatement().executeQuery(query);
//                        if(rr.next()){
//                            ShiftCol = rr.getString(1);
//                        }
//                        query = ("UPDATE supervisor_shifts set `"+ShiftCol+"` = ' ' where supervisor_name = '"+supervisor+"'");
//                        connection.createStatement().executeUpdate(query);
//                        System.out.println("done "+j);
//                        j++;
                    }
                    query = ("UPDATE supervisor_shifts set total_duties = '"+(j-4)+"' where supervisor_name = '"+supervisor+"'");
                    connection.createStatement().executeUpdate(query);
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String convertDateFormat(String mysqlDateString) {
        // Define the input date format for parsing
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Define the desired output date format
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {
            // Parse the input string to get a Date object
            Date date = inputFormat.parse(mysqlDateString);

            // Use the output format to convert Date object to the desired string format
            return String.valueOf(outputFormat.format(date));
//            return String.valueOf(date);
        } catch (ParseException e) {
            e.printStackTrace(); // Handle the exception according to your requirements
            return null; // Return null or an error message if parsing fails
        }
    }
    public static String Checkfull(Connection connection,int i) throws SQLException {
        String queryy = ("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'supervisor_Assignments' AND ORDINAL_POSITION = '" + i + "'");
        ResultSet rr = connection.createStatement().executeQuery(queryy);
        String check_full = "";
        if (rr.next()) {
            check_full = rr.getString(1);
        }
        return check_full;
    }

}
