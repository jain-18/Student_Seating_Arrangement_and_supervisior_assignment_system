/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.student;
import java.sql.*;
import java.util.Random;
import static com.mycompany.student.SahilTest2.*;

/**
 *
 * @author 91877
 */
public class SahilTest4 {
       private static String queryy;
    private static ResultSet rr;
    private static String check_full;

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/studentdata?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String username = "user";
        String password = "password";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM supervisor_assignments");
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            check_full = Checkfull(connection,4);
            queryy = ("SELECT `" + check_full + "` FROM supervisor_assignments WHERE `" + check_full + "` = 'Extra'");
            rr = connection.createStatement().executeQuery(queryy);
            if (!rr.next()) {
//                assignReliever(connection, columnCount);
                assignExtraStaff(connection,columnCount);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void assignExtraStaff(Connection connection,int columnCount){
        try {
            int totalSupervisors = 0;

            // Assign relievers randomly to columns
            Random random = new Random();
            for (int i = 3; i <= columnCount; i++) {
                String examDateColumn = getColumnName(connection, "supervisor_Assignments", i);
                int assignedSupervisors = getAssignedSupervisorsCount(connection, examDateColumn);
                int relieversToAssign = assignedSupervisors / 10; // 1 reliever for every 10 working supervisors in a column
                System.out.println("extra column "+i);

                outer : while (relieversToAssign > 0) {
                    int jain = 0;
                    int blockNumber = getRandomBlockNumber(connection, examDateColumn);
                    String relieverName = getRandomSupervisorName(connection);

                        // Check if the block is not already assigned to a supervisor, the supervisor is not on leave,
                        // and the reliever is not already assigned
                        if (!isBlockAssigned(connection, examDateColumn, blockNumber) &&
                                isSupervisorOnLeave(connection, relieverName, examDateColumn) &&
                                !isSupervisorAlreadyAssigned(relieverName, examDateColumn) && ShiftCheck(connection,relieverName,examDateColumn)) {

                            System.out.println(relieverName);
                            // Assign the reliever to the block in the specific column
                            String updateQuery = "UPDATE supervisor_assignments SET `" + examDateColumn + "` = 'Extra' WHERE supervisor_name = '"+relieverName+"' ";
                            supervisorsAssigned.contains(relieverName + "_" + examDateColumn);
                            System.out.println("Assign extra " + relieverName);
                            try (Statement statement = connection.createStatement()) {
                                statement.executeUpdate(updateQuery);
                            }

//                        System.out.println("Assigned reliever " + relieverName + " to Block " + blockNumber + " on " + examDateColumn + ".");
                            relieversToAssign--;
                        }
                    }
                }
//            }
            try {
                // Closing the connection in a finally block to ensure it always gets closed
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
//            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


}
