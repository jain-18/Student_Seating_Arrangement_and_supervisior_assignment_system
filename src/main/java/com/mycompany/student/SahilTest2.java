
package com.mycompany.student;

//import java.sql.*;
//import java.util.*;
//
//public class SahilTest2 {
//    private static final Set<String> supervisorsAssigned = new HashSet<>();
//    private static final Map<String, Set<Integer>> assignedBlocks = new HashMap<>();
//    static String query = "";
//    static int j = 0, tot = 0;
//    static String queryy = "";
//    static ResultSet rr;
//    static String check_full = "";
//    static int req = 0;
//    static int done = 0;
//
//    public static void main(String[] args) {
//        String jdbcUrl = "jdbc:mysql://localhost:3306/studentdata?zeroDateTimeBehavior=CONVERT_TO_NULL";
//        String username = "user";
//        String password = "password";
//        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM supervisor_assignments");
//            ResultSetMetaData metaData = resultSet.getMetaData();
//            int columnCount = metaData.getColumnCount();
//            ResultSet rrq = connection.createStatement().executeQuery("select count(supervisor_name) from supervisor_assignments");
//            if (rrq.next()) {
//                tot = Integer.parseInt(rrq.getString(1));
//            }
//
//            //iterating different columns first
//            for (int i = 3; i <= columnCount; i++) {
//                query = ("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'supervisor_Assignments' AND ORDINAL_POSITION = '" + i + "'");
//                String examDateColumn = metaData.getColumnName(i);
//                j--;
//
//                resultSet = statement.executeQuery("SELECT * FROM supervisor_assignments");
//                if (i > 3) {
//                    resultSet = connection.createStatement().executeQuery("select * from supervisor_assignments where sup_id > '" + (j) + "'");
//                }
//
//
//                //then iterating rows
//                while (resultSet.next()) {
//
//                    //incrementing position everytime
//                    j = (j + 1) % tot;
//
//
//                    String supervisorName = resultSet.getString("supervisor_name");
//                    // Initialize set of assigned blocks for the supervisor if not already present
//                    assignedBlocks.putIfAbsent(supervisorName, new HashSet<>());
//
//                    queryy = ("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'supervisor_Assignments' AND ORDINAL_POSITION = '" + i + "'");
//                    rr = connection.createStatement().executeQuery(queryy);
//                    check_full = "";
//                    if (rr.next()) {
//                        check_full = rr.getString(1);
//                    }
//                    String[] parts = check_full.split("_");
//                    String session;
//                    if(parts[1]=="M"){
//                        session = "morning_classrooms_required";
//                    }
//                    else {
//                        session = "evening_classrooms_required";
//                    }
//                    queryy = ("select `"+session+"` from classroom_req where exam_date = '" + parts[0] + "'");
//                    rr = connection.createStatement().executeQuery(queryy);
//                    req = 0;
//                    if (rr.next()) {
//                        req = Integer.parseInt(rr.getString(1));
//                    }
//                    queryy = ("SELECT COUNT(`" + check_full + "`) FROM supervisor_assignments WHERE `" + check_full + "` IS NOT NULL;");
//                    rr = connection.createStatement().executeQuery(queryy);
//                    done = 0;
//                    if (rr.next()) {
//                        done = Integer.parseInt(rr.getString(1));
//                    }
//                    if (done >= req) {
//                        break;
//                    } else {
//
//
//                        // Check if the block for this date is not already assigned for the supervisor
//                        if (resultSet.getString(i) == null || resultSet.getString(i).isEmpty()) {
//
//                            // Check if supervisor is not on leave and exam time doesn't clash
//                            if (isSupervisorOnLeave(connection, supervisorName, examDateColumn) &&
//                                    isExamTimeClashing(connection, examDateColumn) &&
//                                    !isSupervisorAlreadyAssigned(supervisorName, examDateColumn)) {
//
//                                // Check if it's morning or evening session based on column name
//                                String examTimeType = examDateColumn.endsWith("_M") ? "M" : "E";
//
//                                //getting blocks list in available blocks
//                                List<Integer> availableBlocks = getAvailableBlocks(connection, examDateColumn);
//                                Collections.shuffle(availableBlocks);
//
//                                for (int blockNumber : availableBlocks) {
//                                    if (!assignedBlocks.get(supervisorName).contains(blockNumber)) {
//                                        assignSupervisorToBlock(connection, supervisorName, examDateColumn, blockNumber, examTimeType);
//                                        supervisorsAssigned.add(supervisorName + "_" + examDateColumn);
////                                        System.out.println("Assigned " + supervisorName + " to Block " + blockNumber + " on " + examDateColumn + " at " + examTimeType);
//                                        break;
//                                    }
//                                }
//
//
//                            }
//                        }
//                    }
//                }
//
//            }
//            CompleteingTable(connection,columnCount);
//
//            String rell = null;
//            queryy = ("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'supervisor_Assignments' AND ORDINAL_POSITION = '" + 4 + "'");
//            rr = connection.createStatement().executeQuery(queryy);
//            check_full = "";
//            if (rr.next()) {
//                check_full = rr.getString(1);
//            }
//            queryy = ("SELECT `" + check_full + "` FROM supervisor_assignments WHERE `" + check_full + "` = 'Reliever'");
//            rr = connection.createStatement().executeQuery(queryy);
//            if (!rr.next()) {
//                assignReliever(connection, columnCount);
//                assignExtraStaff(connection,columnCount);
//            }
//
//
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//
//    }
//
//    private static boolean isSupervisorAlreadyAssigned(String supervisorName, String examDateColumn) {
//        return supervisorsAssigned.contains(supervisorName + "_" + examDateColumn);
//    }
//
//    private static void assignSupervisorToBlock(Connection connection, String supervisorName, String examDateColumn, int blockNumber, String examTimeType) throws SQLException {
//        String oppositeSession = examTimeType.equals("M") ? "E" : "M";
//        String oppositeSessionColumn = examDateColumn.replace(examTimeType, oppositeSession);
//
//        // Check if supervisor is already assigned to the opposite session on the same date
//        if (isSupervisorAlreadyAssigned(supervisorName, oppositeSessionColumn)) {
//            System.out.println("Skipping assignment for " + supervisorName + " on " + examDateColumn + ". Already assigned to the opposite session.");
//            return;
//        }
//
//        String updateQuery = "UPDATE supervisor_assignments SET `" + examDateColumn + "` = '" + blockNumber + "_" + examTimeType + "' WHERE supervisor_name = '" + supervisorName + "'";
//
//        try (Statement statement = connection.createStatement()) {
//            statement.executeUpdate(updateQuery);
//        }
//
//        // Update the set of assigned blocks for the supervisor on the specific date
//        assignedBlocks.get(supervisorName).add(blockNumber);
//        supervisorsAssigned.add(supervisorName + "_" + examDateColumn);
////        System.out.println("Assigned " + supervisorName + " to Block " + blockNumber + " on " + examDateColumn + " at " + examTimeType);
//    }
//
//    private static List<Integer> getAvailableBlocks(Connection connection, String examDateColumn) throws SQLException {
//        List<Integer> availableBlocks = new ArrayList<>();
//        String blockNumberQuery = "SELECT block_no FROM classroom " +
//                "WHERE bench_count > 0 AND " +
//                "block_no NOT IN (SELECT SUBSTRING_INDEX(`" + examDateColumn + "`, 'Block', -1) FROM supervisor_assignments WHERE `" + examDateColumn + "` IS NOT NULL) " ;
//        try (PreparedStatement preparedStatement = connection.prepareStatement(blockNumberQuery)) {
////            preparedStatement.setString(1, examDateColumn);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                availableBlocks.add(resultSet.getInt("block_no"));
//            }
//        }
//        return availableBlocks;
//    }
//
//    private static boolean isSupervisorOnLeave(Connection connection, String supervisorName, String examDateColumn) throws SQLException {
//        String leaveCheckQuery = "SELECT * FROM supervisor WHERE supervisor_name = ? AND ? BETWEEN leave_start_date AND leave_end_date";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(leaveCheckQuery)) {
//            preparedStatement.setString(1, supervisorName);
//            preparedStatement.setString(2, examDateColumn);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            return !resultSet.next();
//        }
//    }
//
//    private static boolean isExamTimeClashing(Connection connection, String examDateColumn) throws SQLException {
//        String startTimeColumn = "start_time"; // Replace with the actual column name for exam start time
//        String endTimeColumn = "end_time"; // Replace with the actual column name for exam end time
//
//        String clashCheckQuery = "SELECT * FROM time_table WHERE Date = ? AND ((? < " + endTimeColumn + " AND ? > " + startTimeColumn + ") OR (? <= " + startTimeColumn + " AND ? >= " + endTimeColumn + "))";
//
//        try (PreparedStatement preparedStatement = connection.prepareStatement(clashCheckQuery)) {
//            preparedStatement.setString(1, examDateColumn);
//            // Set the exam start time and end time parameters (replace with actual values)
//            preparedStatement.setString(2, "exam_start_time");
//            preparedStatement.setString(3, "exam_end_time");
//            preparedStatement.setString(4, "exam_start_time");
//            preparedStatement.setString(5, "exam_end_time");
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            // If there are any rows in the result set, there is a clash
//            return !resultSet.next();
//        }
//    }
//
//    public static void CompleteingTable(Connection connection, int columnCount) {
//        try {
//            for (int i = 3; i <= columnCount; i++) {
//                queryy = ("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'supervisor_Assignments' AND ORDINAL_POSITION = '" + i + "'");
//                rr = connection.createStatement().executeQuery(queryy);
//                check_full = "";
//                if (rr.next()) {
//                    check_full = rr.getString(1);
//                } String[] parts = check_full.split("_");
//                String session;
//                if(parts[1]=="M"){
//                    session = "morning_classrooms_required";
//                }
//                else {
//                    session = "evening_classrooms_required";
//                }
//                queryy = ("select `"+session+"` from classroom_req where exam_date = '" + parts[0] + "'");
//                rr = connection.createStatement().executeQuery(queryy);
//                req = 0;
//                if (rr.next()) {
//                    req = Integer.parseInt(rr.getString(1));
//                }
//                queryy = ("SELECT COUNT(`" + check_full + "`) FROM supervisor_assignments WHERE `" + check_full + "` IS NOT NULL;");
//                rr = connection.createStatement().executeQuery(queryy);
//                done = 0;
//                if (rr.next()) {
//                    done = Integer.parseInt(rr.getString(1));
//                }
//                if (done >= req) {
//                    continue;
//                } else {
//
//
//                    Conn c = new Conn();
//                    queryy = ("SELECT * FROM supervisor_assignments WHERE `" + check_full + "` IS NULL");
//                    ResultSet emptySlots = c.s.executeQuery(queryy);
//
//                    while (emptySlots.next()) {
//                        queryy = ("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'supervisor_Assignments' AND ORDINAL_POSITION = '" + i + "'");
//                        rr = connection.createStatement().executeQuery(queryy);
//                        check_full = "";
//                        if (rr.next()) {
//                            check_full = rr.getString(1);
//                        }
//                        parts = check_full.split("_");
//                        if(parts[1]=="M"){
//                            session = "morning_classrooms_required";
//                        }
//                        else {
//                            session = "evening_classrooms_required";
//                        }
//                        queryy = ("select `"+session+"` from classroom_req where exam_date = '" + parts[0] + "'");
//                        rr = connection.createStatement().executeQuery(queryy);
//                        req = 0;
//                        if (rr.next()) {
//                            req = Integer.parseInt(rr.getString(1));
//                        }
//                        queryy = ("SELECT COUNT(`" + check_full + "`) FROM supervisor_assignments WHERE `" + check_full + "` IS NOT NULL;");
//                        rr = connection.createStatement().executeQuery(queryy);
//                        done = 0;
//                        if (rr.next()) {
//                            done = Integer.parseInt(rr.getString(1));
//                        }
//                        if (done >= req) {
//                            break;
//                        }
//                        String supervisorName = getRandomSupervisorName(connection);
//                        int blockNumber = getRandomBlockNumber(connection, check_full);
//                        String examTimeType = check_full.endsWith("_M") ? "M" : "E";
//
//                        if (isSupervisorOnLeave(connection, supervisorName, check_full) &&
//                                isExamTimeClashing(connection, check_full) &&
//                                !isSupervisorAlreadyAssigned(supervisorName, check_full)){
//                            assignSupervisorToBlock(connection, supervisorName, check_full, blockNumber, examTimeType);
//                        }
//
//
//
//                        System.out.println("Assigned " + supervisorName + " to Block " + blockNumber + " on " + check_full + " at " + examTimeType);
//                    }
//                }
//            }
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//    }
//
//    // Helper method to get a random supervisor name
//    private static String getRandomSupervisorName(Connection connection) throws SQLException {
//        String randomSupervisorName = null;
//        String query = "SELECT supervisor_name FROM supervisor ORDER BY RAND() LIMIT 1";
//        try (Statement statement = connection.createStatement()) {
//            ResultSet resultSet = statement.executeQuery(query);
//            if (resultSet.next()) {
//                randomSupervisorName = resultSet.getString("supervisor_name");
//            }
//        }
//        return randomSupervisorName;
//    }
//
//    // Helper method to get a random block number for a specific exam date column
//    private static int getRandomBlockNumber(Connection connection, String examDateColumn) throws SQLException {
//        int randomBlockNumber = 0;
//        String query = "SELECT block_no FROM classroom " +
//                "WHERE bench_count > 0 AND block_no NOT IN (SELECT SUBSTRING_INDEX(`" + examDateColumn + "`, 'Block', -1) FROM supervisor_assignments WHERE `" + examDateColumn + "` IS NOT NULL) " +
//                "ORDER BY RAND() LIMIT 1";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
////            preparedStatement.setString(1, examDateColumn);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                randomBlockNumber = resultSet.getInt("block_no");
//            }
//        }
//        return randomBlockNumber;
//    }
//
//    public static void assignReliever(Connection connection, int columnCount) {
//        try {
//            int totalSupervisors = 0;
//            for (int i = 3; i <= columnCount; i++) {
//                String examDateColumn = getColumnName(connection, "supervisor_Assignments", i);
//                int assignedSupervisors = getAssignedSupervisorsCount(connection, examDateColumn);
//                totalSupervisors += assignedSupervisors;
//            }
//
//            int totalRelievers = totalSupervisors / 10; // 1 reliever for every 10 working supervisors
//
//            // Assign relievers randomly to columns
//            Random random = new Random();
//            for (int i = 3; i <= columnCount && totalRelievers > 0; i++) {
//                String examDateColumn = getColumnName(connection, "supervisor_Assignments", i);
//                int assignedSupervisors = getAssignedSupervisorsCount(connection, examDateColumn);
//                int relieversToAssign = assignedSupervisors / 10; // 1 reliever for every 10 working supervisors in a column
//
//                while (relieversToAssign > 0 && totalRelievers > 0) {
//                    int blockNumber = getRandomBlockNumber(connection, examDateColumn);
//                    String relieverName = getRandomSupervisorName(connection);
//
//                    // Check if the block is not already assigned to a supervisor, the supervisor is not on leave,
//                    // and the reliever is not already assigned
//                    if (!isBlockAssigned(connection, examDateColumn, blockNumber) &&
//                            isSupervisorOnLeave(connection, relieverName, examDateColumn) &&
//                            !isSupervisorAlreadyAssigned(relieverName, examDateColumn)) {
//
//                        // Assign the reliever to the block in the specific column
//                        String updateQuery = "UPDATE supervisor_assignments SET `" + examDateColumn + "` = 'Reliever' WHERE sup_id = " + blockNumber;
//                        try (Statement statement = connection.createStatement()) {
//                            statement.executeUpdate(updateQuery);
//                        }
//
//                        System.out.println("Assigned reliever " + relieverName + " to Block " + blockNumber + " on " + examDateColumn + ".");
//                        totalRelievers--;
//                        relieversToAssign--;
//                    }
//                }
//            }
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//    }
//
//
//
//    // Helper method to get the column name based on ordinal position
//    private static String getColumnName(Connection connection, String tableName, int ordinalPosition) throws SQLException {
//        String query = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ? AND ORDINAL_POSITION = ?";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//            preparedStatement.setString(1, tableName);
//            preparedStatement.setInt(2, ordinalPosition);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                return resultSet.getString("COLUMN_NAME");
//            }
//        }
//        return null;
//    }
//
//    // Helper method to get the count of assigned supervisors in a specific column
//    private static int getAssignedSupervisorsCount(Connection connection, String examDateColumn) throws SQLException {
//        String query = "SELECT COUNT(*) FROM supervisor_assignments WHERE `" + examDateColumn + "` IS NOT NULL AND `" + examDateColumn + "` NOT LIKE 'Rel'";
//        try (Statement statement = connection.createStatement()) {
//            ResultSet resultSet = statement.executeQuery(query);
//            if (resultSet.next()) {
//                return resultSet.getInt(1);
//            }
//        }
//        return 0;
//    }
//    private static boolean isBlockAssigned(Connection connection, String examDateColumn, int blockNumber) throws SQLException {
//        String query = "SELECT `" + examDateColumn + "` FROM supervisor_assignments WHERE sup_id = " + blockNumber;
//        try (Statement statement = connection.createStatement()) {
//            ResultSet resultSet = statement.executeQuery(query);
//            return resultSet.next() && resultSet.getString(1) != null;
//        }
//    }
//
//    public static void assignExtraStaff(Connection connection,int columnCount){
//        try {
//            int totalSupervisors = 0;
//            for (int i = 3; i <= columnCount; i++) {
//                String examDateColumn = getColumnName(connection, "supervisor_Assignments", i);
//                int assignedSupervisors = getAssignedSupervisorsCount(connection, examDateColumn);
//                totalSupervisors += assignedSupervisors;
//            }
//
//            int totalRelievers = totalSupervisors / 10; // 1 reliever for every 10 working supervisors
//
//            // Assign relievers randomly to columns
//            Random random = new Random();
//            for (int i = 3; i <= columnCount && totalRelievers > 0; i++) {
//                String examDateColumn = getColumnName(connection, "supervisor_Assignments", i);
//                int assignedSupervisors = getAssignedSupervisorsCount(connection, examDateColumn);
//                int relieversToAssign = assignedSupervisors / 10; // 1 reliever for every 10 working supervisors in a column
//
//                while (relieversToAssign > 0 && totalRelievers > 0) {
//                    int blockNumber = getRandomBlockNumber(connection, examDateColumn);
//                    String relieverName = getRandomSupervisorName(connection);
//
//                    // Check if the block is not already assigned to a supervisor, the supervisor is not on leave,
//                    // and the reliever is not already assigned
//                    if (!isBlockAssigned(connection, examDateColumn, blockNumber) &&
//                            isSupervisorOnLeave(connection, relieverName, examDateColumn) &&
//                            !isSupervisorAlreadyAssigned(relieverName, examDateColumn)) {
//
//                        // Assign the reliever to the block in the specific column
//                        String updateQuery = "UPDATE supervisor_assignments SET `" + examDateColumn + "` = 'Extra' WHERE sup_id = " + blockNumber;
//                        try (Statement statement = connection.createStatement()) {
//                            statement.executeUpdate(updateQuery);
//                        }
//
//                        System.out.println("Assigned reliever " + relieverName + " to Block " + blockNumber + " on " + examDateColumn + ".");
//                        totalRelievers--;
//                        relieversToAssign--;
//                    }
//                }
//            }
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//    }
//
//}



import java.sql.*;
import java.util.*;

public class SahilTest2 {
    public static final Set<String> supervisorsAssigned = new HashSet<>();
    private static final Set<String> Limit = new HashSet<>();
    private static final Map<String, Set<Integer>> assignedBlocks = new HashMap<>();
    static String query = "";
    static int j = 0, tot = 0;
    static String queryy = "";
    static ResultSet rr;
    static String check_full = "";
    static int req = 0;
    static int done = 0;

    public static void main(String[] args) {
        
        String jdbcUrl = "jdbc:mysql://localhost:3306/studentdata?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String username = "user";
        String password = "password";
        
//        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM supervisor_assignments");
//            ResultSetMetaData metaData = resultSet.getMetaData();
//            int columnCount = metaData.getColumnCount();
//            ResultSet rrq = connection.createStatement().executeQuery("select count(supervisor_name) from supervisor_assignments");
//            if (rrq.next()) {
//                tot = Integer.parseInt(rrq.getString(1));
//            }
//
//            //iterating different columns first
//            for (int i = 3; i <= columnCount; i++) {
//                for(int s = 0;s<=2;s++) {
//                j=0;
//                query = ("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'supervisor_Assignments' AND ORDINAL_POSITION = '" + i + "'");
//                String examDateColumn = metaData.getColumnName(i);
//                j--;
//
//                resultSet = statement.executeQuery("SELECT * FROM supervisor_assignments");
//                if (i > 3) {
//                    resultSet = connection.createStatement().executeQuery("select * from supervisor_assignments where sup_id > '" + (j) + "'");
//                }
//
//
//                //then iterating rows
//                while (resultSet.next()) {
//
//                    //incrementing position everytime
//                    j = (j + 1) % tot;
//
//                    String supervisorName = resultSet.getString("supervisor_name");
//                    // Initialize set of assigned blocks for the supervisor if not already present
//                    assignedBlocks.putIfAbsent(supervisorName, new HashSet<>());
//
//                    check_full = Checkfull(connection,i);
//                    String[] parts = check_full.split("_");
//                    String session;
//                    if(Objects.equals(parts[1], "M")){
//                        session = "morning_classrooms_required";
//                    }
//                    else {
//                        session = "evening_classrooms_required";
//                    }
//
//                    //getting required number of block on particular day
//                    req = REQ(connection,session,parts[0]);
//
//                    //how much assignment is done for that day
//                    done = DONE(connection,check_full);
//                    if (done >= req) {
//                        break;
//                    } else {
//
//                        if(Limit.contains(supervisorName)){
//                            continue;
//                        }else if(!LimitedAssign(connection, supervisorName)){
//                            continue;
//                        }
//
//
//                        // Check if the block for this date is not already assigned for the supervisor
//                        if (resultSet.getString(i) == null || resultSet.getString(i).isEmpty()) {
//
//                            // Check if supervisor is not on leave and exam time doesn't clash
//                            if (isSupervisorOnLeave(connection, supervisorName, examDateColumn) &&
//                                    isExamTimeClashing(connection, examDateColumn) &&
//                                    !isSupervisorAlreadyAssigned(supervisorName, examDateColumn)) {
//                                System.out.println(i);
//
//                                // Check if it's morning or evening session based on column name
//                                String examTimeType = examDateColumn.endsWith("_M") ? "M" : "E";
//
//                                //getting blocks list in available blocks
//                                List<Integer> availableBlocks = getAvailableBlocks(connection, examDateColumn);
//                                Collections.shuffle(availableBlocks);
//
//                                for (int blockNumber : availableBlocks) {
//                                    if (!assignedBlocks.get(supervisorName).contains(blockNumber)) {
//                                        System.out.println(i);
//                                        assignSupervisorToBlock(connection, supervisorName, examDateColumn, blockNumber, examTimeType);
//                                        supervisorsAssigned.add(supervisorName + "_" + examDateColumn);
////                                        System.out.println("Assigned " + supervisorName + " to Block " + blockNumber + " on " + examDateColumn + " at " + examTimeType);
//                                        break;
//                                    }
//                                }
//
//
//                            }
//                        }
//                    }
//                }
//
//            }
//            }
//            //if some day assignment of supervisor is complete then this method will assign required supervisor
//           // CompleteingTable(connection,columnCount);
//
//            //the below logic check whether reliever or extra staff is assign or not.
//            check_full = Checkfull(connection,4);
//            queryy = ("SELECT `" + check_full + "` FROM supervisor_assignments WHERE `" + check_full + "` = 'Reliever'");
//            rr = connection.createStatement().executeQuery(queryy);
//            if (!rr.next()) {
//                assignReliever(connection, columnCount);
//                assignExtraStaff(connection,columnCount);
//            }
//            try {
//                // Closing the connection in a finally block to ensure it always gets closed
//                if (connection != null && !connection.isClosed()) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//
//    }

      try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM supervisor_assignments");
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            ResultSet rrq = connection.createStatement().executeQuery("select count(supervisor_name) from supervisor_assignments");
            if (rrq.next()) {
                tot = Integer.parseInt(rrq.getString(1));
            }

            //iterating different columns first
            for(int s = 0;s<=2;s++) {
                j=0;
                for (int i = 3; i <= columnCount; i++) {
                    query = ("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'supervisor_Assignments' AND ORDINAL_POSITION = '" + i + "'");
                    String examDateColumn = metaData.getColumnName(i);
                    j--;

                    resultSet = statement.executeQuery("SELECT * FROM supervisor_assignments");
                    if (i > 3) {
                        resultSet = connection.createStatement().executeQuery("select * from supervisor_assignments where sup_id > '" + (j) + "'");
                    }


                    //then iterating rows
                    while (resultSet.next()) {

                        //incrementing position everytime
                        j = (j + 1) % tot;

                        String supervisorName = resultSet.getString("supervisor_name");
                        // Initialize set of assigned blocks for the supervisor if not already present
                        assignedBlocks.putIfAbsent(supervisorName, new HashSet<>());

                        check_full = Checkfull(connection, i);
                        String[] parts = check_full.split("_");
                        String session;
                        if (Objects.equals(parts[1], "M")) {
                            session = "morning_classrooms_required";
                        } else {
                            session = "evening_classrooms_required";
                        }

                        //getting required number of block on particular day
                        req = REQ(connection, session, parts[0]);
//                    System.out.println(req);

                        //how much assignment is done for that day
                        done = DONE(connection, check_full);
                        if (done >= req) {
                            break;
                        } else {

                            if (Limit.contains(supervisorName)) {
                                continue;
                            } else if (!LimitedAssign(connection, supervisorName)) {
                                continue;
                            }


                            // Check if the block for this date is not already assigned for the supervisor
                            if (resultSet.getString(i) == null || resultSet.getString(i).isEmpty()) {

                                // Check if supervisor is not on leave and exam time doesn't clash
                                if (isSupervisorOnLeave(connection, supervisorName, examDateColumn) &&
                                        isExamTimeClashing(connection, examDateColumn) &&
                                        !isSupervisorAlreadyAssigned(supervisorName, examDateColumn)) {
                                    System.out.println(i);

                                    // Check if it's morning or evening session based on column name
                                    String examTimeType = examDateColumn.endsWith("_M") ? "M" : "E";

                                    //getting blocks list in available blocks
                                    List<Integer> availableBlocks = getAvailableBlocks(connection, examDateColumn);
                                    Collections.shuffle(availableBlocks);

                                    for (int blockNumber : availableBlocks) {
                                        if (!assignedBlocks.get(supervisorName).contains(blockNumber)) {
//                                            System.out.println("assssss " + i);
                                            assignSupervisorToBlock(connection, supervisorName, examDateColumn, blockNumber, examTimeType);
                                            supervisorsAssigned.add(supervisorName + "_" + examDateColumn);
//                                        System.out.println("Assigned " + supervisorName + " to Block " + blockNumber + " on " + examDateColumn + " at " + examTimeType);
                                            break;
                                        }
                                    }


                                }
                            }
                        }
                    }

                }
            }
            //if some day assignment of supervisor is complete then this method will assign required supervisor
//            CompleteingTable(connection,columnCount);

            //the below logic check whether reliever or extra staff is assign or not.
            check_full = Checkfull(connection,4);
            queryy = ("SELECT `" + check_full + "` FROM supervisor_assignments WHERE `" + check_full + "` = 'Reliever'");
            rr = connection.createStatement().executeQuery(queryy);
            if (!rr.next()) {
                assignReliever(connection, columnCount);
                assignExtraStaff(connection,columnCount);
            }
            try {
                // Closing the connection in a finally block to ensure it always gets closed
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }

     static boolean isSupervisorAlreadyAssigned(String supervisorName, String examDateColumn) {
        return supervisorsAssigned.contains(supervisorName + "_" + examDateColumn);
    }

     static void assignSupervisorToBlock(Connection connection, String supervisorName, String examDateColumn, int blockNumber, String examTimeType) throws SQLException {
        String oppositeSession = examTimeType.equals("M") ? "E" : "M";
        String oppositeSessionColumn = examDateColumn.replace(examTimeType, oppositeSession);

        // Check if supervisor is already assigned to the opposite session on the same date
        if (isSupervisorAlreadyAssigned(supervisorName, oppositeSessionColumn)) {
            System.out.println("Skipping assignment for " + supervisorName + " on " + examDateColumn + ". Already assigned to the opposite session.");
            return;
        }

        String updateQuery = "UPDATE supervisor_assignments SET `" + examDateColumn + "` = '" + blockNumber + "_" + examTimeType + "' WHERE supervisor_name = '" + supervisorName + "'";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(updateQuery);
        }

        // Update the set of assigned blocks for the supervisor on the specific date
        assignedBlocks.get(supervisorName).add(blockNumber);
        supervisorsAssigned.add(supervisorName + "_" + examDateColumn);
//        System.out.println("Assigned " + supervisorName + " to Block " + blockNumber + " on " + examDateColumn + " at " + examTimeType);
    }

    static List<Integer> getAvailableBlocks(Connection connection, String examDateColumn) throws SQLException {
        List<Integer> availableBlocks = new ArrayList<>();
        String blockNumberQuery = "SELECT block_no FROM classroom " +
                "WHERE bench_count > 0 AND " +
                "block_no NOT IN (SELECT SUBSTRING_INDEX(`" + examDateColumn + "`, 'Block', -1) FROM supervisor_assignments WHERE `" + examDateColumn + "` IS NOT NULL) " ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(blockNumberQuery)) {
//            preparedStatement.setString(1, examDateColumn);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                availableBlocks.add(resultSet.getInt("block_no"));
            }
        }
        return availableBlocks;
    }
     static boolean isSupervisorOnLeave(Connection connection, String supervisorName, String examDateColumn) throws SQLException {
        String leaveCheckQuery = "SELECT * FROM supervisor WHERE supervisor_name = ? AND ? BETWEEN leave_start_date AND leave_end_date";
        try (PreparedStatement preparedStatement = connection.prepareStatement(leaveCheckQuery)) {
            preparedStatement.setString(1, supervisorName);
            preparedStatement.setString(2, examDateColumn);
            ResultSet resultSet = preparedStatement.executeQuery();
            return !resultSet.next();
        }
    }

     static boolean isExamTimeClashing(Connection connection, String examDateColumn) throws SQLException {
        String startTimeColumn = "start_time"; // Replace with the actual column name for exam start time
        String endTimeColumn = "end_time"; // Replace with the actual column name for exam end time

        String clashCheckQuery = "SELECT * FROM time_table WHERE Date = ? AND ((? < " + endTimeColumn + " AND ? > " + startTimeColumn + ") OR (? <= " + startTimeColumn + " AND ? >= " + endTimeColumn + "))";

        try (PreparedStatement preparedStatement = connection.prepareStatement(clashCheckQuery)) {
            preparedStatement.setString(1, examDateColumn);
            // Set the exam start time and end time parameters (replace with actual values)
            preparedStatement.setString(2, "exam_start_time");
            preparedStatement.setString(3, "exam_end_time");
            preparedStatement.setString(4, "exam_start_time");
            preparedStatement.setString(5, "exam_end_time");

            ResultSet resultSet = preparedStatement.executeQuery();

            // If there are any rows in the result set, there is a clash
            return !resultSet.next();
        }
    }

    public static void CompleteingTable(Connection connection, int columnCount) {
        try {
            outer1 : for (int i = 3; i <= columnCount; i++) {
                check_full = Checkfull(connection,i);
                String[] parts = check_full.split("_");
                String session;
                    if(Objects.equals(parts[1], "M")){
                    session = "morning_classrooms_required";
                    }
                    else {
                    session = "evening_classrooms_required";
                     }
                //getting required number of block on particular day
                req = REQ(connection,session,parts[0]);

                //how much assignment is done for that day
                done = DONE(connection,check_full);
                if (done >= req) {
                    continue outer1;
                } else {


                    Conn c = new Conn();
                    queryy = ("SELECT * FROM supervisor_assignments WHERE `" + check_full + "` IS NULL");
                    ResultSet emptySlots = c.s.executeQuery(queryy);

                    outer : while (emptySlots.next()) {

                        check_full = Checkfull(connection, i);
                        parts = check_full.split("_");
                        if (Objects.equals(parts[1], "M")) {
                            session = "morning_classrooms_required";
                        } else {
                            session = "evening_classrooms_required";
                        }
                        //getting required number of block on particular day
                        req = REQ(connection, session, parts[0]);

                        //getting required number of block on particular day
                        done = DONE(connection, check_full);
                        if (done >= req) {
                            break;
                        }

                        String supervisorName = getRandomSupervisorName(connection);

                        if(Limit.contains(supervisorName)){
                            continue outer;
                        }else
                        if (!LimitedAssign(connection, supervisorName)) {
                            continue outer;
                        } else {
                            int blockNumber = getRandomBlockNumber(connection, check_full);
                            String examTimeType = check_full.endsWith("_M") ? "M" : "E";

                            if (isSupervisorOnLeave(connection, supervisorName, check_full) &&
                                    isExamTimeClashing(connection, check_full) &&
                                    !isSupervisorAlreadyAssigned(supervisorName, check_full)) {

                                assignSupervisorToBlock(connection, supervisorName, check_full, blockNumber, examTimeType);
                            }


//                        System.out.println("Assigned " + supervisorName + " to Block " + blockNumber + " on " + check_full + " at " + examTimeType);
                        }
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static String Checkfull(Connection connection,int i) throws SQLException {
        queryy = ("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'supervisor_Assignments' AND ORDINAL_POSITION = '" + i + "'");
        rr = connection.createStatement().executeQuery(queryy);
        check_full = "";
        if (rr.next()) {
            check_full = rr.getString(1);
        }
        return check_full;
    }
    public static int REQ(Connection connection,String session,String parts) throws SQLException {
        queryy = ("select `"+session+"` from classroom_req where exam_date = '" + parts + "'");
        rr = connection.createStatement().executeQuery(queryy);
        req = 0;
        if (rr.next()) {
            req = Integer.parseInt(rr.getString(1));
        }
        return req;
    }
    public static int DONE(Connection connection,String check_full) throws SQLException {
        queryy = ("SELECT COUNT(`" + check_full + "`) FROM supervisor_assignments WHERE `" + check_full + "` IS NOT NULL;");
        rr = connection.createStatement().executeQuery(queryy);
        done = 0;
        if (rr.next()) {
            done = Integer.parseInt(rr.getString(1));
        }
        return done;
    }

    // Helper method to get a random supervisor name
//     static String getRandomSupervisorName(Connection connection) throws SQLException {
//        String randomSupervisorName = null;
//        String query = "SELECT supervisor_name FROM supervisor ORDER BY RAND() LIMIT 1";
//        try (Statement statement = connection.createStatement()) {
//            ResultSet resultSet = statement.executeQuery(query);
//            if (resultSet.next()) {
//                randomSupervisorName = resultSet.getString("supervisor_name");
//            }
//        }
//        return randomSupervisorName;
//    }
    
    static String getRandomSupervisorName(Connection connection) throws SQLException {
        String randomSupervisorName = null;
        do {
            String query = "SELECT supervisor_name FROM supervisor ORDER BY RAND() LIMIT 1";
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    randomSupervisorName = resultSet.getString("supervisor_name");
                }
            }
            System.out.println("rejected reliever "+randomSupervisorName);
        }while (!LimitedAssign(connection,randomSupervisorName));
        System.out.println("selected for reiever "+randomSupervisorName);
        return randomSupervisorName;
    }

    // Helper method to get a random block number for a specific exam date column
     static int getRandomBlockNumber(Connection connection, String examDateColumn) throws SQLException {
        int randomBlockNumber = 0;
        String query = "SELECT block_no FROM classroom " +
                "WHERE bench_count > 0 AND block_no NOT IN (SELECT SUBSTRING_INDEX(`" + examDateColumn + "`, 'Block', -1) FROM supervisor_assignments WHERE `" + examDateColumn + "` IS NOT NULL) " +
                "ORDER BY RAND() LIMIT 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//            preparedStatement.setString(1, examDateColumn);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                randomBlockNumber = resultSet.getInt("block_no");
            }
        }
        return randomBlockNumber;
    }
//    public static void assignReliever(Connection connection, int columnCount) {
//        try {
//            int totalSupervisors = 0;
//
//            // Assign relievers randomly to columns
//            Random random = new Random();
//            for (int i = 3; i <= columnCount; i++) {
//                String examDateColumn = getColumnName(connection, "supervisor_Assignments", i);
//                int assignedSupervisors = getAssignedSupervisorsCount(connection, examDateColumn);
//                int relieversToAssign = assignedSupervisors / 10; // 1 reliever for every 10 working supervisors in a column
//
//                outer : while (relieversToAssign > 0) {
////                for (int z = 0; relieversToAssign > 0; z++) {
//                    int jain = 0;
//                    int blockNumber = getRandomBlockNumber(connection, examDateColumn);
//                    String relieverName = getRandomSupervisorName(connection);
////                    while (!LimitedAssign(connection, relieverName)) {
////                        relieverName = getRandomSupervisorName(connection);
////                    }
//
//                    // Check if the block is not already assigned to a supervisor, the supervisor is not on leave,
//                    // and the reliever is not already assigned
//                    if (!isBlockAssigned(connection, examDateColumn, blockNumber) &&
//                            isSupervisorOnLeave(connection, relieverName, examDateColumn) &&
//                            !isSupervisorAlreadyAssigned(relieverName, examDateColumn) && ShiftCheck(connection,relieverName,examDateColumn)) {
//
//
//                        // Assign the reliever to the block in the specific column
//                        String updateQuery = "UPDATE supervisor_assignments SET `" + examDateColumn + "` = 'Reliever' WHERE supervisor_name = '"+relieverName+"'";
//                        try (Statement statement = connection.createStatement()) {
//                            statement.executeUpdate(updateQuery);
//                        }
//
////                        System.out.println("Assigned reliever " + relieverName + " to Block " + blockNumber + " on " + examDateColumn + ".");
//                        relieversToAssign--;
//                    }
//                }
//                }
//
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//    }
     
     
     public static void assignReliever(Connection connection, int columnCount) throws SQLException {
        try {
            int totalSupervisors = 0;

            // Assign relievers randomly to columns
            for (int i = 3; i <= columnCount; i++) {
                String examDateColumn = getColumnName(connection, "supervisor_Assignments", i);
                int assignedSupervisors = getAssignedSupervisorsCount(connection, examDateColumn);
                int relieversToAssign = assignedSupervisors / 10; // 1 reliever for every 10 working supervisors in a column
                System.out.println("reliever column "+i);

                outer : while (relieversToAssign > 0) {
                    int blockNumber = getRandomBlockNumber(connection, examDateColumn);
                    String relieverName = getRandomSupervisorName(connection);

                    // Check if the block is not already assigned to a supervisor, the supervisor is not on leave,
                    // and the reliever is not already assigned
                    if (!isBlockAssigned(connection, examDateColumn, blockNumber) &&
                            isSupervisorOnLeave(connection, relieverName, examDateColumn) &&
                            !isSupervisorAlreadyAssigned(relieverName, examDateColumn) && ShiftCheck(connection,relieverName,examDateColumn)) {

                        System.out.println(relieverName);
                        // Assign the reliever to the block in the specific column
                        String updateQuery = "UPDATE supervisor_assignments SET `" + examDateColumn + "` = 'Reliever' WHERE supervisor_name = '"+relieverName+"'";
                        supervisorsAssigned.contains(relieverName + "_" + examDateColumn);
                        try (Statement statement = connection.createStatement()) {

                            statement.executeUpdate(updateQuery);
                        }

//                        System.out.println("Assigned reliever " + relieverName + " to Block " + blockNumber + " on " + examDateColumn + ".");
                        relieversToAssign--;
                    }
                }
            }

//            try {
//                // Closing the connection in a finally block to ensure it always gets closed
//                if (connection != null && !connection.isClosed()) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }



    // Helper method to get the column name based on ordinal position
     static String getColumnName(Connection connection, String tableName, int ordinalPosition) throws SQLException {
        String query = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ? AND ORDINAL_POSITION = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, tableName);
            preparedStatement.setInt(2, ordinalPosition);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("COLUMN_NAME");
            }
        }
        return null;
    }

    // Helper method to get the count of assigned supervisors in a specific column
     static int getAssignedSupervisorsCount(Connection connection, String examDateColumn) throws SQLException {
        String query = "SELECT COUNT(*) FROM supervisor_assignments WHERE `" + examDateColumn + "` IS NOT NULL AND `" + examDateColumn + "` NOT LIKE 'Reliever'";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        return 0;
    }
     static boolean isBlockAssigned(Connection connection, String examDateColumn, int blockNumber) throws SQLException {
        String query = "SELECT `" + examDateColumn + "` FROM supervisor_assignments WHERE sup_id = " + blockNumber;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            return resultSet.next() && resultSet.getString(1) != null;
        }
    }

//    public static void assignExtraStaff(Connection connection,int columnCount){
//        try {
//            int totalSupervisors = 0;
//
//            // Assign relievers randomly to columns
//            Random random = new Random();
//            for (int i = 3; i <= columnCount; i++) {
//                String examDateColumn = getColumnName(connection, "supervisor_Assignments", i);
//                int assignedSupervisors = getAssignedSupervisorsCount(connection, examDateColumn);
//                int relieversToAssign = assignedSupervisors / 10; // 1 reliever for every 10 working supervisors in a column
//
//                outer : while (relieversToAssign > 0) {
////                for (int z = 0; relieversToAssign > 0; z++) {
//                    int jain = 0;
//                    int blockNumber = getRandomBlockNumber(connection, examDateColumn);
//                    String relieverName = getRandomSupervisorName(connection);
////                    if (Limit.contains(relieverName)) {
////                        continue outer;
////                    } else
////                    while (!LimitedAssign(connection, relieverName)) {
////                        relieverName = getRandomSupervisorName(connection);
////                    }
//                    // Check if the block is not already assigned to a supervisor, the supervisor is not on leave,
//                    // and the reliever is not already assigned
//                    if (!isBlockAssigned(connection, examDateColumn, blockNumber) &&
//                            isSupervisorOnLeave(connection, relieverName, examDateColumn) &&
//                            !isSupervisorAlreadyAssigned(relieverName, examDateColumn) && ShiftCheck(connection,relieverName,examDateColumn)) {
//
//                        // Assign the reliever to the block in the specific column
//                        String updateQuery = "UPDATE supervisor_assignments SET `" + examDateColumn + "` = 'Extra' WHERE supervisor_name = '"+relieverName+"'";
//                        try (Statement statement = connection.createStatement()) {
//                            statement.executeUpdate(updateQuery);
//                        }
//
////                        System.out.println("Assigned reliever " + relieverName + " to Block " + blockNumber + " on " + examDateColumn + ".");
//                        relieversToAssign--;
//                    }
//
//                }
//                }
//
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//    }
     
     
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
                            String updateQuery = "UPDATE supervisor_assignments SET `" + examDateColumn + "` = 'Extra' WHERE supervisor_name = '" + relieverName + "' ";
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
//            try {
//                // Closing the connection in a finally block to ensure it always gets closed
//                if (connection != null && !connection.isClosed()) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
     
     
     
    public static boolean LimitedAssign(Connection connection, String supervisor_name) {
        try {
            Map<String, Integer> maxAssignments = new HashMap<>();
            maxAssignments.put("Professor", 6);
            maxAssignments.put("Ass.Prof.", 4);
            maxAssignments.put("Doctorate", 2);
            maxAssignments.put("Junior", 10); // Range from 10 to 12 assignments

            String positionQuery = "SELECT position FROM supervisor WHERE supervisor_name = ?";
            try (PreparedStatement positionStatement = connection.prepareStatement(positionQuery)) {
                positionStatement.setString(1, supervisor_name);
                ResultSet positionResultSet = positionStatement.executeQuery();

                if (positionResultSet.next()) {
                    String position = positionResultSet.getString("position");
                    int currentAssignments = getCurrentAssignments(connection, supervisor_name,j);
                    int maxAssign = maxAssignments.getOrDefault(position, 3);

                    if(currentAssignments == maxAssign || (currentAssignments > maxAssign)){
                        Limit.add(supervisor_name);
                    }
                    // Check if the supervisor can be assigned more based on their position
                    return currentAssignments < maxAssign;
                } else {
                    System.out.println("Supervisor not found: " + supervisor_name);
                    return true;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return true;
        }
    }

     static int getCurrentAssignments(Connection connection, String supervisor_name,int k) throws SQLException {

        String op = null;
        int j=0;
        Conn c =new Conn();
         ResultSet resultSet = c.s.executeQuery("SELECT * FROM supervisor_assignments");
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        for(int i = 3;i<columnCount;i++){
//            queryy = ("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'supervisor_Assignments' AND ORDINAL_POSITION = '" + i + "'");
//            rr = connection.createStatement().executeQuery(queryy);
//            check_full = "";
//            if(rr.next()){
//                check_full = rr.getString(1);
//            }
            check_full = Checkfull(connection,i);
            queryy = ("select `"+check_full+"` from supervisor_assignments where supervisor_name = '"+supervisor_name+"'");
            rr = connection.createStatement().executeQuery(queryy);
            if (rr.next()){
                op = rr.getString(1);
            }
            if(op != null ){
                j++;
            }
        }
//        System.out.println(j);
        return j;
    }
    
    static boolean ShiftCheck(Connection connection,String relieverName,String examDateColumn) throws SQLException{
        String examTimeType = examDateColumn.endsWith("_M") ? "M" : "E";
        String oppositeSession = examTimeType.equals("M") ? "E" : "M";
        String oppositeSessionColumn = examDateColumn.replace(examTimeType, oppositeSession);
        if (isSupervisorAlreadyAssigned(relieverName, oppositeSessionColumn)) {
            System.out.println("Skipping assignment for " + relieverName + " on " + examDateColumn + ". Already assigned to the opposite session.");
            return false;
        }
        return true;
    }

}
    
    
    
    



