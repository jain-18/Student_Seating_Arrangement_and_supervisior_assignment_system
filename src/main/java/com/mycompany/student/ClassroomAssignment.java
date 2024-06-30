
package com.mycompany.student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClassroomAssignment {
    public static void main(String[] args){
        Connection connection = null;
        try {
             String jdbcUrl = "jdbc:mysql://localhost:3306/studentdata?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String username = "user";
        String password = "password";

            connection = DriverManager.getConnection(jdbcUrl, username, password);

            // Create the classroom_assignment table
            createClassroomAssignmentTable(connection);

            // Fetch exam dates from classroom_req table
            List<String> examDates = getExamDates(connection);

            for (String examDate : examDates) {
                int morningRequired = getMorningRequired(connection, examDate);
                int eveningRequired = getEveningRequired(connection, examDate);

                // Assign classrooms based on requirements
                List<Integer> morningBlocks = assignBlocks(connection, morningRequired);
                List<Integer> eveningBlocks = assignBlocks(connection, eveningRequired);

                // Insert assignments into classroom_assignment table
                insertClassroomAssignment(connection, examDate, morningBlocks, eveningBlocks);

                System.out.println("Exam Date: " + examDate);
                System.out.println("Morning Session Blocks: " + morningBlocks);
                System.out.println("Evening Session Blocks: " + eveningBlocks);
                System.out.println();
            }

            System.out.println("Classroom assignments completed.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void createClassroomAssignmentTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE classroom_assignment ("
                + "assignment_id INT AUTO_INCREMENT PRIMARY KEY,"
                + "exam_date DATE,"
                + "morning_session_assignments VARCHAR(255),"
                + "evening_session_assignments VARCHAR(255)"
                + ")";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
        }
    }

    private static List<String> getExamDates(Connection connection) throws SQLException {
        List<String> examDates = new ArrayList<>();
        String sql = "SELECT DISTINCT exam_date FROM classroom_req";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                examDates.add(resultSet.getString("exam_date"));
            }
        }
        return examDates;
    }

    private static int getMorningRequired(Connection connection, String examDate) throws SQLException {
        String sql = "SELECT morning_classrooms_required FROM classroom_req WHERE exam_date = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, examDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("morning_classrooms_required");
            }
        }
        return 0;
    }

    private static int getEveningRequired(Connection connection, String examDate) throws SQLException {
        String sql = "SELECT evening_classrooms_required FROM classroom_req WHERE exam_date = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, examDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("evening_classrooms_required");
            }
        }
        return 0;
    }

    private static List<Integer> assignBlocks(Connection connection, int required) throws SQLException {
        List<Integer> assignedBlocks = new ArrayList<>();
        String sql = "SELECT block_no FROM classroom ORDER BY RAND() LIMIT ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, required);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                assignedBlocks.add(resultSet.getInt("block_no"));
            }
        }
        return assignedBlocks;
    }

    private static void insertClassroomAssignment(Connection connection, String examDate, List<Integer> morningBlocks, List<Integer> eveningBlocks) throws SQLException {
        String insertSQL = "INSERT INTO classroom_assignment (exam_date, morning_session_assignments, evening_session_assignments) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, examDate);
            preparedStatement.setString(2, morningBlocks.toString());
            preparedStatement.setString(3, eveningBlocks.toString());
            preparedStatement.executeUpdate();
        }
    }
}


