

package com.mycompany.student;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClassroomRequirements {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/studentdata?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String username = "user";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            // SQL query to create the classroom_req table
            String createTableSQL = "CREATE TABLE IF NOT EXISTS classroom_req (" +
                                    "    exam_date DATE PRIMARY KEY," +
                                    "    morning_classrooms_required INT," +
                                    "    evening_classrooms_required INT" +
                                    ");";
            connection.createStatement().executeUpdate(createTableSQL);

            // SQL query to calculate classrooms required in the morning shift
            String morningSessionSQL = "INSERT INTO classroom_req (exam_date, morning_classrooms_required) " +
                    "SELECT " +
                    "    t.Date AS exam_date," +
                    "    CEIL(COUNT(s.seat_no) / 30) AS morning_classrooms_required " +
                    "FROM " +
                    "    time_table t " +
                    "LEFT JOIN " +
                    "    student_data s ON t.Branch = s.branch AND t.Semester = s.semester " +
                    "WHERE SUBSTRING(t.Start_Time, 1, 5) = '09:30' " + // Morning session condition
                    "GROUP BY " +
                    "    t.Date;";

            // SQL query to calculate classrooms required in the evening shift
            String eveningSessionSQL = "UPDATE classroom_req " +
                    "SET evening_classrooms_required = ( " +
                    "    SELECT CEIL(COUNT(s.seat_no) / 30) AS evening_classrooms_required " +
                    "    FROM " +
                    "        time_table t " +
                    "    LEFT JOIN " +
                    "        student_data s ON t.Branch = s.branch AND t.Semester = s.semester " +
                    "    WHERE SUBSTRING(t.Start_Time, 1, 5) = '02:30' " + // Evening session condition
                    "    AND classroom_req.exam_date = t.Date " +
                    ");";

            try (PreparedStatement morningStatement = connection.prepareStatement(morningSessionSQL);
                 PreparedStatement eveningStatement = connection.prepareStatement(eveningSessionSQL)) {
                morningStatement.executeUpdate();
                eveningStatement.executeUpdate();

                System.out.println("Classroom requirements for morning and evening sessions updated successfully.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}





