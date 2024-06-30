package com.mycompany.student;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Arrays;
//
//public class ClassroomAssignmentUpdater {
//    public static void main(String[] args) {
//        // Define database connection parameters
//        String jdbcUrl = "jdbc:mysql://localhost:3306/studentdata?zeroDateTimeBehavior=CONVERT_TO_NULL";
//        String username = "user";
//        String password = "password";
//        
//        // Initialize database connection
//        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
//            // Retrieve morning session assigned block numbers
//            List<Integer> assignedBlockNumbers = getMorningSessionAssignedBlocks(connection);
//            
//            // Update the studentclassroomassignment table with block numbers
//            updateStudentClassroomAssignment(connection, assignedBlockNumbers);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    
//    private static List<Integer> getMorningSessionAssignedBlocks(Connection connection) throws SQLException {
//        List<Integer> assignedBlockNumbers = new ArrayList<>();
//        
//        // Query to retrieve morning session assigned block numbers as a string
//        String sql = "SELECT morning_session_assignments FROM classroom_assignment";
//        
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
//             ResultSet resultSet = preparedStatement.executeQuery()) {
//            while (resultSet.next()) {
//                String blockNumbersString = resultSet.getString("morning_session_assignments");
//                List<String> blockNumbersList = Arrays.asList(blockNumbersString.split(","));
//
//                // Convert and add individual block numbers to the list
//                for (String blockNumberString : blockNumbersList) {
//                    int blockNumber = Integer.parseInt(blockNumberString.trim());
//                    assignedBlockNumbers.add(blockNumber);
//                }
//            }
//        }
//        
//        return assignedBlockNumbers;
//    }
//    
//    private static void updateStudentClassroomAssignment(Connection connection, List<Integer> assignedBlockNumbers)
//            throws SQLException {
//        // SQL query to update studentclassroomassignment table with block numbers
//        String updateSql = "UPDATE studentclassroomassignment SET block_number = ?";
//        
//        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
//            for (int i = 0; i < 32; i++) {
//                // Set the block number in the prepared statement
//                preparedStatement.setInt(1, assignedBlockNumbers.get(i));
//                preparedStatement.executeUpdate();
//            }
//        }
//    }
//}

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.HashSet;
//import java.util.Random;
//import java.util.Set;
//
//public class ClassroomAssignmentUpdater {
//    public static void main(String[] args) {
//        // Define database connection parameters
//        String jdbcUrl = "jdbc:mysql://localhost:3306/studentdata?zeroDateTimeBehavior=CONVERT_TO_NULL";
//        String username = "user";
//        String password = "password";
//        
//        // Initialize database connection
//        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
//            // Get the number of rows in studentclassroomassignment table
//            int rowCount = getRowCount(connection, "studentclassroomassignment");
//            
//            // Read and update rows with random block numbers
//            updateStudentClassroomAssignment(connection, rowCount);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    
//    private static int getRowCount(Connection connection, String tableName) throws SQLException {
//        // Query to get the number of rows in a table
//        String sql = "SELECT COUNT(*) FROM " + tableName;
//        
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
//             ResultSet resultSet = preparedStatement.executeQuery()) {
//            if (resultSet.next()) {
//                return resultSet.getInt(1);
//            }
//        }
//        
//        return 0;
//    }
//    
//    private static void updateStudentClassroomAssignment(Connection connection, int rowCount) throws SQLException {
//        // SQL query to add a new column 'block_number' to studentclassroomassignment table
//        String alterTableSql = "ALTER TABLE studentclassroomassignment ADD COLUMN block_number INT";
//        
//        // SQL query to update studentclassroomassignment table with random block numbers
//        String updateSql = "UPDATE studentclassroomassignment SET block_number = ? WHERE id = ?";
//        
//        try (PreparedStatement alterTableStatement = connection.prepareStatement(alterTableSql);
//             PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
//            // Execute the ALTER TABLE statement to add the 'block_number' column
//            alterTableStatement.execute();
//            
//            // Create a set to track assigned block numbers
//            Set<Integer> assignedBlockNumbers = new HashSet<>();
//            
//            for (int rowId = 1; rowId <= rowCount; rowId++) {
//                int blockNumber;
//                do {
//                    blockNumber = getRandomBlockNumber();
//                } while (assignedBlockNumbers.contains(blockNumber));
//                
//                // Update the row with the random block number
//                preparedStatement.setInt(1, blockNumber);
//                preparedStatement.setInt(2, rowId);
//                preparedStatement.executeUpdate();
//                
//                // Add the assigned block number to the set
//                assignedBlockNumbers.add(blockNumber);
//            }
//        }
//    }
//    
//    private static int getRandomBlockNumber() {
//        Random rand = new Random();
//        return rand.nextInt(40) + 1; // Generates a random number between 1 and 40
//    }
//}


