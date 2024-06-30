
package com.mycompany.student;
import java.sql.Connection;
public class StudentTest {
    
    
     public static void main(String[] args) {
        Connection connection = null;
        try {
            String dbUrl = "jdbc:mysql://localhost:3306/studentdata?zeroDateTimeBehavior=CONVERT_TO_NULL";
            String dbUser = "user";
            String dbPassword = "password";

            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

}
        catch(
                )
     }
     
