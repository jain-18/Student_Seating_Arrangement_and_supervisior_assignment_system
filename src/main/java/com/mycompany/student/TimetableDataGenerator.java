
package com.mycompany.student;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//
//public class TimetableDataGenerator {
//
//    public static void main(String[] args) {
//        // Database connection details
//        String jdbcUrl = "jdbc:mysql://localhost:3306/studentdata?zeroDateTimeBehavior=CONVERT_TO_NULL";
//        String username = "user";
//        String password = "password";
//
//        try {
//            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
//
//            // Define the schedule for semesters
//            String[] semesters = {"1", "3", "5", "7"};
//            String[] branchNames = {"comps", "I.T", "extc", "electronics", "civil", "mech", "aids", "mtrx"};
//
//            // Generate and insert timetable data
//            for (String semester : semesters) {
//                for (String branch : branchNames) {
//                    generateTimetableData(connection, branch, semester);
//                }
//            }
//
//            connection.close();
//            System.out.println("Sample timetable data inserted successfully.");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void generateTimetableData(Connection connection, String branch, String semester) throws SQLException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//
//        // Set start and end times based on semester
//        String startTime, endTime;
//        if (semester.equals("1") || semester.equals("7")) {
//            startTime = "09:30 AM";
//            endTime = "12:30 PM";
//        } else {
//            startTime = "02:30 PM";
//            endTime = "05:30 PM";
//        }
//
//        // Define subjects for each semester (you can update this with actual Mumbai University subjects)
//        String[] subjects = {"Subject1", "Subject2", "Subject3", "Subject4", "Subject5"};
//
//        // Insert data for each day of the week (e.g., Monday to Friday)
//        for (int i = Calendar.MONDAY; i <= Calendar.FRIDAY; i++) {
//            String date = sdf.format(calendar.getTime());
//            String dayOfWeek = new SimpleDateFormat("EEEE").format(calendar.getTime());
//
//            // Insert data into the time_table
//            String sql = "INSERT INTO time_table (Date, Branch, Semester, Subject, Start_Time, End_Time) VALUES (?, ?, ?, ?, ?, ?)";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, date);
//            preparedStatement.setString(2, branch);
//            preparedStatement.setString(3, semester);
//            // Choose a subject randomly from the subjects list
//            String subject = subjects[(i - Calendar.MONDAY) % subjects.length];
//            preparedStatement.setString(4, subject);
//            preparedStatement.setString(5, startTime);
//            preparedStatement.setString(6, endTime);
//
//            preparedStatement.executeUpdate();
//
//            System.out.println("Inserted data for " + dayOfWeek + " - " + date);
//
//            // Increment the calendar to the next day
//            calendar.add(Calendar.DAY_OF_MONTH, 1);
//        }
//    }
//}

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimetableDataGenerator {

    public static void main(String[] args) {
        // Database connection details
         String jdbcUrl = "jdbc:mysql://localhost:3306/studentdata?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String username = "user";
        String password = "password";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            // Define the schedule for semesters
            String[] semesters = {"1", "3", "5", "7"};
            String[] branchNames = {"comps", "aids", "extx", "mech", "electronic", "I.T.", "mtrx", "civil"};

            // Generate and insert timetable data
            for (String semester : semesters) {
                for (String branch : branchNames) {
                    generateTimetableData(connection, branch, semester);
                }
            }

            connection.close();
            System.out.println("Sample timetable data inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void generateTimetableData(Connection connection, String branch, String semester) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        // Set start and end times based on semester
        String startTime, endTime;
        if (semester.equals("1") || semester.equals("7")) {
            startTime = "09:30 AM";
            endTime = "12:30 PM";
        } else {
            startTime = "02:30 PM";
            endTime = "05:30 PM";
        }

        // Define subjects for each semester (you can update this with actual Mumbai University subjects)
        String[] subjects = {"Subject1", "Subject2", "Subject3", "Subject4", "Subject5"};

        // Insert data for each day of the week (e.g., Monday to Friday)
        for (int i = Calendar.MONDAY; i <= Calendar.FRIDAY; i++) {
            String date = sdf.format(calendar.getTime());
            String dayOfWeek = new SimpleDateFormat("EEEE").format(calendar.getTime());

            // Insert data into the time_table
            String sql = "INSERT INTO time_table (Date, Branch, Semester, Subject, Start_Time, End_Time) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, date);
            preparedStatement.setString(2, branch);
            preparedStatement.setString(3, semester);
            // Choose a subject randomly from the subjects list
            String subject = subjects[(i - Calendar.MONDAY) % subjects.length];
            preparedStatement.setString(4, subject);
            preparedStatement.setString(5, startTime);
            preparedStatement.setString(6, endTime);

            preparedStatement.executeUpdate();

            System.out.println("Inserted data for " + dayOfWeek + " - " + date);

            // Increment the calendar to the next day
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }
}

