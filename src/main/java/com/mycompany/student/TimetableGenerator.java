
package com.mycompany.student;

import com.toedter.calendar.JDateChooser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class TimetableGenerator extends JFrame {
    private final JComboBox<Integer> subjectCountDropdown;
    private final JComboBox<String> branchDropdown;
    private final JComboBox<Integer> semDropdown;
    private final JPanel subjectPanel;
    String usernamee;
    public TimetableGenerator(String username) {
        usernamee = username;
        setTitle("Timetable Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 700);
        setLocationRelativeTo(null);
        

        subjectCountDropdown = new JComboBox<>(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 });
        branchDropdown = new JComboBox<>(new String[] { "Comps", "Extx", "Electronics", "Mech", "Civil", "I.T", "Mtrx" });
        semDropdown = new JComboBox<>(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8 });
        JButton generateButton = new JButton("Generate Fields");
        JButton submitButton = new JButton("Submit");
        JButton backButton = new JButton("Back");
        JButton view = new JButton("View Timetables");
        subjectPanel = new JPanel();
        subjectPanel.setLayout(new BoxLayout(subjectPanel, BoxLayout.Y_AXIS));
        subjectPanel.setBackground(new Color(255, 224, 223));

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateTimetable();
            }
        });
        
         view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                View_timetable viewJFrame = new View_timetable(username);
                viewJFrame.setVisible(true);            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTimetableToDatabase();
            }
        });
        
         backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 dispose();
        Menu1 menu1 = new Menu1(usernamee);
        menu1.setVisible(true);
        
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(null);
        controlPanel.setBackground(new Color(204, 255, 255));

        branchDropdown.setBounds(280, 30, 150, 30);
        controlPanel.add(branchDropdown);

        semDropdown.setBounds(600, 30, 150, 30);
        controlPanel.add(semDropdown);

        JLabel branchLabel = new JLabel("Select Branch:");
        branchLabel.setBounds(180, 30, 150, 30);
        controlPanel.add(branchLabel);

        JLabel semLabel = new JLabel("Select Semester:");
        semLabel.setBounds(490, 30, 150, 30);
        controlPanel.add(semLabel);

        subjectCountDropdown.setBounds(350, 80, 150, 30);
        controlPanel.add(subjectCountDropdown);

        JLabel subjectCountLabel = new JLabel("Select Number of Subjects:");
        subjectCountLabel.setBounds(180, 80, 200, 30);
        controlPanel.add(subjectCountLabel);

        generateButton.setBounds(40, 130, 150, 30);
        controlPanel.add(generateButton);

        submitButton.setBounds(570, 130, 150, 30);
        controlPanel.add(submitButton);
        
        view.setBounds(200, 130, 150, 30);
        controlPanel.add(view);
        
        backButton.setBounds(40, 30, 70, 30);
        controlPanel.add(backButton);

        controlPanel.setPreferredSize(new Dimension(800, 180));

        setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.NORTH);
        add(new JScrollPane(subjectPanel), BorderLayout.CENTER);
    }

    private void generateTimetable() {
        subjectPanel.removeAll();
        
        int subjectCount = (int) subjectCountDropdown.getSelectedItem();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 1; i <= subjectCount; i++) {
            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(null);
            rowPanel.setBackground(new Color(255, 224, 223));
            
            JLabel subjectLabel = new JLabel("Subject:");
            subjectLabel.setBounds(20, 10, 50, 30);
            rowPanel.add(subjectLabel);

            JTextField subjectTextField = new JTextField();
            subjectTextField.setBounds(70, 10, 150, 30);
            rowPanel.add(subjectTextField);

            JLabel dateLabel = new JLabel("Date:");
            dateLabel.setBounds(250, 10, 50, 30);
            rowPanel.add(dateLabel);

            JDateChooser dateChooser = new JDateChooser();
            dateChooser.setDateFormatString("yyyy-MM-dd");
            dateChooser.setBounds(300, 10, 150, 30);
            rowPanel.add(dateChooser);

            JLabel startTimeLabel = new JLabel("Start Time:");
            startTimeLabel.setBounds(480, 10, 80, 30);
            rowPanel.add(startTimeLabel);

            JTextField startTimeTextField = new JTextField();
            startTimeTextField.setBounds(560, 10, 100, 30);
            rowPanel.add(startTimeTextField);

            JLabel endTimeLabel = new JLabel("End Time:");
            endTimeLabel.setBounds(690, 10, 80, 30);
            rowPanel.add(endTimeLabel);

            JTextField endTimeTextField = new JTextField();
            endTimeTextField.setBounds(770, 10, 100, 30);
            rowPanel.add(endTimeTextField);

            rowPanel.setPreferredSize(new Dimension(950, 50));
            subjectPanel.add(rowPanel);
        }

        revalidate();
        repaint();
    }

    private void saveTimetableToDatabase() {
        // Replace these placeholders with your actual database connection details
        String url = "jdbc:mysql://localhost:3306/studentdata?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String user = "user";
        String password = "password";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            int subjectCount = (int) subjectCountDropdown.getSelectedItem();

            for (int i = 1; i <= subjectCount; i++) {
                String branch = (String) branchDropdown.getSelectedItem();
                int semester = (int) semDropdown.getSelectedItem();
                String subject = ((JTextField) ((JPanel) subjectPanel.getComponent(i - 1)).getComponent(1)).getText();
                String date = new SimpleDateFormat("yyyy-MM-dd").format(
                        ((JDateChooser) ((JPanel) subjectPanel.getComponent(i - 1)).getComponent(3)).getDate());
                String startTime = ((JTextField) ((JPanel) subjectPanel.getComponent(i - 1)).getComponent(5)).getText();
                String endTime = ((JTextField) ((JPanel) subjectPanel.getComponent(i - 1)).getComponent(7)).getText();

                // Insert data into the database (replace with your actual SQL query)
                String insertQuery = "INSERT INTO time_table(Date,Branch,Semester,Subject,Start_Time,End_Time) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(1, date);
                preparedStatement.setString(2, branch);
                preparedStatement.setInt(3, semester);
                preparedStatement.setString(4, subject);
                preparedStatement.setString(5, startTime);
                preparedStatement.setString(6, endTime);
                preparedStatement.executeUpdate();
            }

            // Clear subjectPanel
            subjectPanel.removeAll();
            subjectPanel.revalidate();
            subjectPanel.repaint();

            JOptionPane.showMessageDialog(this, "Timetable data saved to the database.");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving data to the database.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TimetableGenerator timetableGenerator = new TimetableGenerator("jain");
            timetableGenerator.setVisible(true);
        });
    }
}


