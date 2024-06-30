package com.mycompany.student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertSampleStudentData {
    public static void main(String[] args) {
        // JDBC database connection parameters
        String jdbcUrl = "jdbc:mysql://localhost:3306/studentdata?zeroDateTimeBehavior=CONVERT_TO_NULL"; // Replace with your database URL
        String username = "user"; // Replace with your database username
        String password = "password"; // Replace with your database password

        Connection connection = null;
        try {
            // Establish a database connection
            connection = DriverManager.getConnection(jdbcUrl, username, password);

            // Define the branches and semesters
            String[] branches = {"comps", "aids", "extx", "mech", "electronic", "I.T.", "mtrx", "civil"};
            String[] semesters = {"1", "3", "5", "7"};

            // Generate and execute INSERT statements for all branches and semesters
            for (String branch : branches) {
                for (String semester : semesters) {
                    generateAndExecuteInserts(connection, branch, semester);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database connection
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static void generateAndExecuteInserts(Connection connection, String branch, String semester) throws SQLException {
        String insertSQL = "INSERT INTO `student_data` (`seat_no`, `branch`, `semester`) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            int startSeatNo = getStartSeatNo(branch, semester);
            for (int i = 0; i < 60; i++) { // Generate 60 to 120 insert statements
                int seatNo = startSeatNo + i;
                preparedStatement.setInt(1, seatNo);
                preparedStatement.setString(2, branch);
                preparedStatement.setString(3, semester);
                preparedStatement.executeUpdate();
            }
        }
    }

    
    private static int getStartSeatNo(String branch, String semester) {
    // Define specific seat number ranges for each branch and semester
    int baseSeatNo = 1000000; // Adjust as needed
    int branchOffset = 0;
    int semesterOffset = 0;

    // Determine the offset for the branch
    switch (branch) {
        case "comps":
            branchOffset = 0; // Example offset for 'comps'
            break;
        case "aids":
            branchOffset = 1000; // Example offset for 'aids'
            break;
        case "extx":
            branchOffset = 2000; // Example offset for 'extx'
            break;
        case "mech":
            branchOffset = 3000; // Example offset for 'mech'
            break;
        case "electronic":
            branchOffset = 4000; // Example offset for 'electronic'
            break;
        case "I.T.":
            branchOffset = 5000; // Example offset for 'I.T.'
            break;
        case "mtrx":
            branchOffset = 6000; // Example offset for 'mtrx'
            break;
        case "civil":
            branchOffset = 7000; // Example offset for 'civil'
            break;
    }

    // Determine the offset for the semester
    switch (semester) {
        case "1":
            semesterOffset = 0; // Example offset for semester 1
            break;
        case "3":
            semesterOffset = 300; // Example offset for semester 3
            break;
        case "5":
            semesterOffset = 600; // Example offset for semester 5
            break;
        case "7":
            semesterOffset = 900; // Example offset for semester 7
            break;
    }

    return baseSeatNo + branchOffset + semesterOffset;
}

}

