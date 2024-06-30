package com.mycompany.student;
import java.sql.*;
import java.util.*;

public class main {
    public static void main(String[] args) {
        try {
            

            // Establish a connection to the database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdata?zeroDateTimeBehavior=CONVERT_TO_NULL", "user", "password");

            // Create a statement to execute SQL commands
            Statement statement = connection.createStatement();

            // Create the studentclassroomassignment table
            statement.executeUpdate("CREATE TABLE studentclassroomassignment (date DATE, branch VARCHAR(255), semester INT, seat_no_range VARCHAR(255), block_no INT)");

            // Fetch all data from the other tables
            ResultSet studentDataResultSet = statement.executeQuery("SELECT * FROM student_data");
            ResultSet classroomResultSet = statement.executeQuery("SELECT * FROM classroom");
            ResultSet timeTableResultSet = statement.executeQuery("SELECT * FROM time_table");
            ResultSet classroomAssignmentResultSet = statement.executeQuery("SELECT * FROM classroom_assignment");

            // Create a HashMap to store the classroom assignment data
            HashMap<String, String> classroomAssignmentData = new HashMap<>();
            while (classroomAssignmentResultSet.next()) {
                String examDate = classroomAssignmentResultSet.getString("exam_date");
                String morningSessionAssignments = classroomAssignmentResultSet.getString("morning_session_assignments");
                String eveningSessionAssignments = classroomAssignmentResultSet.getString("evening_session_assignments");
                classroomAssignmentData.put(examDate, morningSessionAssignments + "," + eveningSessionAssignments);
            }

            // Iterate through the student data and assign the block number, seat number range, and block number to each student
            while (studentDataResultSet.next()) {
                String date = studentDataResultSet.getString("date");
                String branch = studentDataResultSet.getString("branch");
                int semester = studentDataResultSet.getInt("semester");
                int seat_no = studentDataResultSet.getInt("seat_no");

                // Find the block number for the student
                int block_no = -1;
                while (classroomResultSet.next()) {
                    int currentBlockNo = classroomResultSet.getInt("block_no");
                    int benchCount = classroomResultSet.getInt("bench_count");

                    if (block_no == -1 || (block_no + 1) * benchCount <= seat_no) {
                        block_no = currentBlockNo;
                    } else {
                        break;
                    }
                }

                // Find the seat number range for the student
                String seat_no_range = null;
                while (timeTableResultSet.next()) {
                    String currentDate = timeTableResultSet.getString("date");
                    String currentBranch = timeTableResultSet.getString("branch");
                    int currentSemester = timeTableResultSet.getInt("semester");
                    String startTime = timeTableResultSet.getString("start_time");

                    if (date.equals(currentDate) && branch.equals(currentBranch) && semester == currentSemester) {
                        if (startTime.equals("09:00 AM")) {
                            seat_no_range = classroomAssignmentData.get(date);
                        } else if (startTime.equals("02:00 PM")) {
                            String[] assignments = classroomAssignmentData.get(date).split(",");
                            seat_no_range = assignments[1];
                        }
                    }
                }

                // Insert the data into the studentclassroomassignment table
                statement.executeUpdate("INSERT INTO studentclassroomassignment (date, branch, semester, seat_no_range, block_no) VALUES ('" + date + "', '" + branch + "', " + semester + ", '" + seat_no_range + "', " + block_no + ")");
            }

            // Close the database connection
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}