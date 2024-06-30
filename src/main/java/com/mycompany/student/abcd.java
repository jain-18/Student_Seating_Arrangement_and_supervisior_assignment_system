
package com.mycompany.student;


package com.mycompany.student;


//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class StudentClassroomAssignment {
//    public static void main(String[] args) {
//        Connection connection = null;
//        try {
//            String jdbcUrl = "jdbc:mysql://localhost:3306/studentdata?zeroDateTimeBehavior=CONVERT_TO_NULL";
//        String username = "user";
//        String password = "password";
//
//            connection = DriverManager.getConnection(jdbcUrl, username, password);
//            // Create the studentclassroomassignment table
//            createStudentClassroomAssignmentTable(connection);
//
//            // Fetch exam dates from classroom_assignment table
//            List<String> examDates = getExamDates(connection);
//
//            for (String examDate : examDates) {
//                // Get morning and evening classroom assignments
//                List<Integer> morningBlocks = getMorningSessionAssignments(connection, examDate);
//                List<Integer> eveningBlocks = getEveningSessionAssignments(connection, examDate);
//
//                // Assign blocks to branches and semesters
//                Map<String, Map<String, Integer>> branchSemesterBlockMap = assignBlocksToBranchesAndSemesters(connection, morningBlocks, eveningBlocks);
//
//                // Assign students to classrooms
//                assignStudentsToClassrooms(connection, examDate, branchSemesterBlockMap, "Morning");
//                assignStudentsToClassrooms(connection, examDate, branchSemesterBlockMap, "Evening");
//
//                System.out.println("Students assigned for exam date: " + examDate);
//            }
//
//            System.out.println("Student classroom assignments completed.");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    private static void createStudentClassroomAssignmentTable(Connection connection) throws SQLException {
//        String createTableSQL = "CREATE TABLE studentclassroomassignment ("
//                + "assignment_id INT AUTO_INCREMENT PRIMARY KEY,"
//                + "exam_date DATE,"
//                + "branch VARCHAR(255),"
//                + "semester VARCHAR(255),"
//                + "start_seat_no INT,"
//                + "end_seat_no INT,"
//                + "block_no INT,"
//                + "session VARCHAR(255)"
//                + ")";
//
//        try (Statement statement = connection.createStatement()) {
//            statement.execute(createTableSQL);
//        }
//    }
//
//    private static List<String> getExamDates(Connection connection) throws SQLException {
//        List<String> examDates = new ArrayList<>();
//        String sql = "SELECT DISTINCT exam_date FROM classroom_assignment";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                examDates.add(resultSet.getString("exam_date"));
//            }
//        }
//        return examDates;
//    }
//
//    private static List<Integer> getMorningSessionAssignments(Connection connection, String examDate) throws SQLException {
//        String sql = "SELECT morning_session_assignments FROM classroom_assignment WHERE exam_date = ?";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setString(1, examDate);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                String assignments = resultSet.getString("morning_session_assignments");
//                return parseAssignmentString(assignments);
//            }
//        }
//        return new ArrayList<>();
//    }
//
//    private static List<Integer> getEveningSessionAssignments(Connection connection, String examDate) throws SQLException {
//        String sql = "SELECT evening_session_assignments FROM classroom_assignment WHERE exam_date = ?";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setString(1, examDate);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                String assignments = resultSet.getString("evening_session_assignments");
//                return parseAssignmentString(assignments);
//            }
//        }
//        return new ArrayList<>();
//    }
//
//    private static List<Integer> parseAssignmentString(String assignmentString) {
//        List<Integer> assignments = new ArrayList<>();
//        assignmentString = assignmentString.replace("[", "").replace("]", ""); // Remove square brackets
//        String[] parts = assignmentString.split(",");
//        for (String part : parts) {
//            assignments.add(Integer.parseInt(part.trim()));
//        }
//        return assignments;
//    }
//
//    private static Map<String, Map<String, Integer>> assignBlocksToBranchesAndSemesters(Connection connection, List<Integer> morningBlocks, List<Integer> eveningBlocks) throws SQLException {
//        Map<String, Map<String, Integer>> branchSemesterBlockMap = new HashMap<>();
//        String sql = "SELECT branch, semester FROM student_data " +
//                "WHERE branch IS NOT NULL AND semester IS NOT NULL " +
//                "GROUP BY branch, semester " +
//                "ORDER BY RAND()";
//
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            ResultSet resultSet = preparedStatement.executeQuery();
//            for (Integer block : morningBlocks) {
//                if (resultSet.next()) {
//                    String branch = resultSet.getString("branch");
//                    String semester = resultSet.getString("semester");
//                    branchSemesterBlockMap.putIfAbsent(branch, new HashMap<>());
//                    branchSemesterBlockMap.get(branch).put(semester, block);
//                } else {
//                    break; // Break if no more branches and semesters available
//                }
//            }
//
//            for (Integer block : eveningBlocks) {
//                if (resultSet.next()) {
//                    String branch = resultSet.getString("branch");
//                    String semester = resultSet.getString("semester");
//                    branchSemesterBlockMap.putIfAbsent(branch, new HashMap<>());
//                    branchSemesterBlockMap.get(branch).put(semester, block);
//                } else {
//                    break; // Break if no more branches and semesters available
//                }
//            }
//        }
//        return branchSemesterBlockMap;
//    }
//
//    private static void assignStudentsToClassrooms(Connection connection, String examDate, Map<String, Map<String, Integer>> branchSemesterBlockMap, String session) throws SQLException {
//        for (Map.Entry<String, Map<String, Integer>> branchEntry : branchSemesterBlockMap.entrySet()) {
//            String branch = branchEntry.getKey();
//            Map<String, Integer> semesterBlockMap = branchEntry.getValue();
//
//            for (Map.Entry<String, Integer> semesterEntry : semesterBlockMap.entrySet()) {
//                String semester = semesterEntry.getKey();
//                Integer block = semesterEntry.getValue();
//
//                String sql = "SELECT seat_no FROM student_data " +
//                        "WHERE branch = ? AND semester = ? " +
//                        "ORDER BY seat_no ASC";
//
//                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//                    preparedStatement.setString(1, branch);
//                    preparedStatement.setString(2, semester);
//                    ResultSet resultSet = preparedStatement.executeQuery();
//                    int startSeatNo = -1;
//                    int endSeatNo = -1;
//
//                    while (resultSet.next()) {
//                        int seatNo = resultSet.getInt("seat_no");
//
//                        if (startSeatNo == -1) {
//                            startSeatNo = seatNo;
//                        } else if (seatNo > endSeatNo + 1) {
//                            insertStudentAssignment(connection, examDate, branch, semester, startSeatNo, endSeatNo, block, session);
//                            startSeatNo = seatNo;
//                        }
//
//                        endSeatNo = seatNo;
//                    }
//
//                    if (startSeatNo != -1) {
//                        insertStudentAssignment(connection, examDate, branch, semester, startSeatNo, endSeatNo, block, session);
//                    }
//                }
//            }
//        }
//    }
//
//    private static void insertStudentAssignment(Connection connection, String examDate, String branch, String semester, int startSeatNo, int endSeatNo, int block, String session) throws SQLException {
//        String insertSQL = "INSERT INTO studentclassroomassignment (exam_date, branch, semester, start_seat_no, end_seat_no, block_no, session) VALUES (?, ?, ?, ?, ?, ?, ?)";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
//            preparedStatement.setString(1, examDate);
//            preparedStatement.setString(2, branch);
//            preparedStatement.setString(3, semester);
//            preparedStatement.setInt(4, startSeatNo);
//            preparedStatement.setInt(5, endSeatNo);
//            preparedStatement.setInt(6, block);
//            preparedStatement.setString(7, session);
//            preparedStatement.executeUpdate();
//        }
//    }
//}

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class StudentClassroomAssignment {
//    public static void main(String[] args) {
//        Connection connection = null;
//        try {
//            String dbUrl = "jdbc:mysql://localhost:3306/studentdata?zeroDateTimeBehavior=CONVERT_TO_NULL";
//            String dbUser = "user";
//            String dbPassword = "password";
//
//            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
//
//            // Create the studentclassroomassignment table
//            createStudentClassroomAssignmentTable(connection);
//
//            // Fetch exam dates from classroom_assignment table
//            List<String> examDates = getExamDates(connection);
//
//            for (String examDate : examDates) {
//                // Get morning and evening classroom assignments
//                List<Integer> morningBlocks = getMorningSessionAssignments(connection, examDate);
//                List<Integer> eveningBlocks = getEveningSessionAssignments(connection, examDate);
//
//                // Assign blocks to branches and semesters for morning and evening separately
//                Map<String, Map<String, Integer>> branchSemesterBlockMapMorning = assignBlocksToBranchesAndSemesters(connection, morningBlocks);
//                Map<String, Map<String, Integer>> branchSemesterBlockMapEvening = assignBlocksToBranchesAndSemesters(connection, eveningBlocks);
//
//                // Assign students to classrooms for morning and evening separately
//                assignStudentsToClassrooms(connection, examDate, branchSemesterBlockMapMorning, "Morning");
//                assignStudentsToClassrooms(connection, examDate, branchSemesterBlockMapEvening, "Evening");
//
//                System.out.println("Students assigned for exam date: " + examDate);
//            }
//
//            System.out.println("Student classroom assignments completed.");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    private static void createStudentClassroomAssignmentTable(Connection connection) throws SQLException {
//        String createTableSQL = "CREATE TABLE studentclassroomassignment ("
//                + "assignment_id INT AUTO_INCREMENT PRIMARY KEY,"
//                + "exam_date DATE,"
//                + "branch VARCHAR(255),"
//                + "semester VARCHAR(255),"
//                + "start_seat_no INT,"
//                + "end_seat_no INT,"
//                + "block_no INT,"
//                + "session VARCHAR(255)"
//                + ")";
//
//        try (Statement statement = connection.createStatement()) {
//            statement.execute(createTableSQL);
//        }
//    }
//
//    private static List<String> getExamDates(Connection connection) throws SQLException {
//        List<String> examDates = new ArrayList<>();
//        String sql = "SELECT DISTINCT exam_date FROM classroom_assignment";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                examDates.add(resultSet.getString("exam_date"));
//            }
//        }
//        return examDates;
//    }
//
//    private static List<Integer> getMorningSessionAssignments(Connection connection, String examDate) throws SQLException {
//        String sql = "SELECT morning_session_assignments FROM classroom_assignment WHERE exam_date = ?";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setString(1, examDate);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                String assignments = resultSet.getString("morning_session_assignments");
//                return parseAssignmentString(assignments);
//            }
//        }
//        return new ArrayList<>();
//    }
//
//    private static List<Integer> getEveningSessionAssignments(Connection connection, String examDate) throws SQLException {
//        String sql = "SELECT evening_session_assignments FROM classroom_assignment WHERE exam_date = ?";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setString(1, examDate);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                String assignments = resultSet.getString("evening_session_assignments");
//                return parseAssignmentString(assignments);
//            }
//        }
//        return new ArrayList<>();
//    }
//
//    private static List<Integer> parseAssignmentString(String assignmentString) {
//        List<Integer> assignments = new ArrayList<>();
//        assignmentString = assignmentString.replace("[", "").replace("]", ""); // Remove square brackets
//        String[] parts = assignmentString.split(",");
//        for (String part : parts) {
//            assignments.add(Integer.parseInt(part.trim()));
//        }
//        return assignments;
//    }
//
//    private static Map<String, Map<String, Integer>> assignBlocksToBranchesAndSemesters(Connection connection, List<Integer> blocks) throws SQLException {
//        Map<String, Map<String, Integer>> branchSemesterBlockMap = new HashMap<>();
//        String sql = "SELECT branch, semester FROM student_data " +
//                "WHERE branch IS NOT NULL AND semester IS NOT NULL " +
//                "GROUP BY branch, semester " +
//                "ORDER BY RAND()";
//
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            ResultSet resultSet = preparedStatement.executeQuery();
//            for (Integer block : blocks) {
//                if (resultSet.next()) {
//                    String branch = resultSet.getString("branch");
//                    String semester = resultSet.getString("semester");
//                    branchSemesterBlockMap.putIfAbsent(branch, new HashMap<>());
//                    branchSemesterBlockMap.get(branch).put(semester, block);
//                } else {
//                    break; // Break if no more branches and semesters available
//                }
//            }
//        }
//        return branchSemesterBlockMap;
//    }
//
//    private static void assignStudentsToClassrooms(Connection connection, String examDate, Map<String, Map<String, Integer>> branchSemesterBlockMap, String session) throws SQLException {
//        for (Map.Entry<String, Map<String, Integer>> branchEntry : branchSemesterBlockMap.entrySet()) {
//            String branch = branchEntry.getKey();
//            Map<String, Integer> semesterBlockMap = branchEntry.getValue();
//
//            for (Map.Entry<String, Integer> semesterEntry : semesterBlockMap.entrySet()) {
//                String semester = semesterEntry.getKey();
//                Integer block = semesterEntry.getValue();
//
//                String sql = "SELECT seat_no FROM student_data " +
//                        "WHERE branch = ? AND semester = ? " +
//                        "ORDER BY seat_no ASC";
//
//                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//                    preparedStatement.setString(1, branch);
//                    preparedStatement.setString(2, semester);
//                    ResultSet resultSet = preparedStatement.executeQuery();
//                    int startSeatNo = -1;
//                    int endSeatNo = -1;
//                    int assignedStudents = 0;
//
//                    while (resultSet.next()) {
//                        int seatNo = resultSet.getInt("seat_no");
//
//                        if (startSeatNo == -1) {
//                            startSeatNo = seatNo;
//                        } else if (seatNo > endSeatNo + 1 || assignedStudents >= 30) {
//                            insertStudentAssignment(connection, examDate, branch, semester, startSeatNo, endSeatNo, block, session);
//                            startSeatNo = seatNo;
//                            assignedStudents = 0;
//                        }
//
//                        endSeatNo = seatNo;
//                        assignedStudents++;
//                    }
//
//                    if (startSeatNo != -1) {
//                        insertStudentAssignment(connection, examDate, branch, semester, startSeatNo, endSeatNo, block, session);
//                    }
//                }
//            }
//        }
//    }
//
//    private static void insertStudentAssignment(Connection connection, String examDate, String branch, String semester, int startSeatNo, int endSeatNo, int block, String session) throws SQLException {
//        String insertSQL = "INSERT INTO studentclassroomassignment (exam_date, branch, semester, start_seat_no, end_seat_no, block_no, session) VALUES (?, ?, ?, ?, ?, ?, ?)";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
//            preparedStatement.setString(1, examDate);
//            preparedStatement.setString(2, branch);
//            preparedStatement.setString(3, semester);
//            preparedStatement.setInt(4, startSeatNo);
//            preparedStatement.setInt(5, endSeatNo);
//            preparedStatement.setInt(6, block);
//            preparedStatement.setString(7, session);
//            preparedStatement.executeUpdate();
//        }
//    }
//}

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentClassroomAssignment {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            String dbUrl = "jdbc:mysql://localhost:3306/studentdata?zeroDateTimeBehavior=CONVERT_TO_NULL";
            String dbUser = "user";
            String dbPassword = "password";

            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            // Create the studentclassroomassignment table
            createStudentClassroomAssignmentTable(connection);

            // Fetch exam dates from classroom_assignment table
            List<String> examDates = getExamDates(connection);

            for (String examDate : examDates) {
                // Fetch morning and evening session assignments separately
                List<Integer> morningBlocks = getMorningSessionAssignments(connection, examDate);
                List<Integer> eveningBlocks = getEveningSessionAssignments(connection, examDate);

                // Filter students based on their semester for morning and evening sessions
                Map<String, Map<String, Integer>> branchSemesterBlockMapMorning = assignBlocksToBranchesAndSemesters(connection, morningBlocks, "09:30", "Morning");
                Map<String, Map<String, Integer>> branchSemesterBlockMapEvening = assignBlocksToBranchesAndSemesters(connection, eveningBlocks, "14:30", "Evening");

                // Assign students to classrooms for morning and evening sessions separately
                assignStudentsToClassrooms(connection, examDate, branchSemesterBlockMapMorning, "Morning");
                assignStudentsToClassrooms(connection, examDate, branchSemesterBlockMapEvening, "Evening");

                System.out.println("Students assigned for exam date: " + examDate);
            }

            System.out.println("Student classroom assignments completed.");
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

    private static void createStudentClassroomAssignmentTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE studentclassroomassignment ("
                + "assignment_id INT AUTO_INCREMENT PRIMARY KEY,"
                + "exam_date DATE,"
                + "branch VARCHAR(255),"
                + "semester VARCHAR(255),"
                + "start_seat_no INT,"
                + "end_seat_no INT,"
                + "block_no INT,"
                + "session VARCHAR(255)"
                + ")";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
        }
    }

    private static List<String> getExamDates(Connection connection) throws SQLException {
        List<String> examDates = new ArrayList<>();
        String sql = "SELECT DISTINCT exam_date FROM classroom_assignment";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                examDates.add(resultSet.getString("exam_date"));
            }
        }
        return examDates;
    }

    private static List<Integer> getMorningSessionAssignments(Connection connection, String examDate) throws SQLException {
        String sql = "SELECT morning_session_assignments FROM classroom_assignment WHERE exam_date = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, examDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String assignments = resultSet.getString("morning_session_assignments");
                return parseAssignmentString(assignments);
            }
        }
        return new ArrayList<>();
    }

    private static List<Integer> getEveningSessionAssignments(Connection connection, String examDate) throws SQLException {
        String sql = "SELECT evening_session_assignments FROM classroom_assignment WHERE exam_date = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, examDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String assignments = resultSet.getString("evening_session_assignments");
                return parseAssignmentString(assignments);
            }
        }
        return new ArrayList<>();
    }

    private static List<Integer> parseAssignmentString(String assignmentString) {
        List<Integer> assignments = new ArrayList<>();
        assignmentString = assignmentString.replace("[", "").replace("]", ""); // Remove square brackets
        String[] parts = assignmentString.split(",");
        for (String part : parts) {
            assignments.add(Integer.parseInt(part.trim()));
        }
        return assignments;
    }

    private static Map<String, Map<String, Integer>> assignBlocksToBranchesAndSemesters(Connection connection, List<Integer> blocks, String startTime, String session) throws SQLException {
        Map<String, Map<String, Integer>> branchSemesterBlockMap = new HashMap<>();
        String sql = "SELECT branch, semester FROM time_table " +
                "WHERE SUBSTRING(Start_Time, 1, 5) = ? " +
                "ORDER BY RAND()";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, startTime);
            ResultSet resultSet = preparedStatement.executeQuery();
            for (Integer block : blocks) {
                if (resultSet.next()) {
                    String branch = resultSet.getString("branch");
                    String semester = resultSet.getString("semester");
                    branchSemesterBlockMap.putIfAbsent(branch, new HashMap<>());
                    branchSemesterBlockMap.get(branch).put(semester, block);
                } else {
                    break; // Break if no more branches and semesters available
                }
            }
        }
        return branchSemesterBlockMap;
    }

    private static void assignStudentsToClassrooms(Connection connection, String examDate, Map<String, Map<String, Integer>> branchSemesterBlockMap, String session) throws SQLException {
        for (Map.Entry<String, Map<String, Integer>> branchEntry : branchSemesterBlockMap.entrySet()) {
            String branch = branchEntry.getKey();
            Map<String, Integer> semesterBlockMap = branchEntry.getValue();

            for (Map.Entry<String, Integer> semesterEntry : semesterBlockMap.entrySet()) {
                String semester = semesterEntry.getKey();
                Integer block = semesterEntry.getValue();

                String sql = "SELECT seat_no FROM student_data " +
                        "WHERE branch = ? AND semester = ? " +
                        "ORDER BY seat_no ASC";

                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, branch);
                    preparedStatement.setString(2, semester);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    int startSeatNo = -1;
                    int endSeatNo = -1;
                    int assignedStudents = 0;

                    while (resultSet.next()) {
                        int seatNo = resultSet.getInt("seat_no");

                        if (startSeatNo == -1) {
                            startSeatNo = seatNo;
                        } else if (seatNo > endSeatNo + 1 || assignedStudents >= 30) {
                            insertStudentAssignment(connection, examDate, branch, semester, startSeatNo, endSeatNo, block, session);
                            startSeatNo = seatNo;
                            assignedStudents = 0;
                        }

                        endSeatNo = seatNo;
                        assignedStudents++;
                    }

                    if (startSeatNo != -1) {
                        insertStudentAssignment(connection, examDate, branch, semester, startSeatNo, endSeatNo, block, session);
                    }
                }
            }
        }
    }

    private static void insertStudentAssignment(Connection connection, String examDate, String branch, String semester, int startSeatNo, int endSeatNo, int block, String session) throws SQLException {
        String insertSQL = "INSERT INTO studentclassroomassignment (exam_date, branch, semester, start_seat_no, end_seat_no, block_no, session) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, examDate);
            preparedStatement.setString(2, branch);
            preparedStatement.setString(3, semester);
            preparedStatement.setInt(4, startSeatNo);
            preparedStatement.setInt(5, endSeatNo);
            preparedStatement.setInt(6, block);
            preparedStatement.setString(7, session);
            preparedStatement.executeUpdate();
        }
    }
}

