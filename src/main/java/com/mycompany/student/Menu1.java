
package com.mycompany.student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;

public class Menu1 extends JFrame {
    public static void main(String[] args) {
        new Menu1("jain");
    }

    Cursor cur = new Cursor(Cursor.HAND_CURSOR);
    JLabel in_stud, in_sup, in_class,in_time,stud_repo,sup_repo,class_repo;
    Menu1(String username){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(1000,600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Dasboard");



        JLabel background = new JLabel();
        background.setBounds(0,0,986,563);
        background.setBackground(Color.white);
        background.setOpaque(true);
        background.setBorder(BorderFactory.createLineBorder(Color.black,3));
        this.add(background);

        JLabel left_label = new JLabel();
        left_label.setBounds(5,5,420,553);
        left_label.setOpaque(true);
        left_label.setBackground(Color.decode("#ebecf0"));
        background.add(left_label);

        ImageIcon i1 = new ImageIcon("C:\\Users\\91877\\Documents\\NetBeansProjects\\Student\\src\\main\\java\\img\\right.png");
        Image i2 = i1.getImage().getScaledInstance(560,553,0);
        ImageIcon i3 = new ImageIcon(i2);

        JLabel right_label = new JLabel(i3);
        right_label.setBounds(421,5,560,553);
        right_label.setOpaque(true);
        right_label.setBackground(Color.decode("#ebecf0"));
//        right_label.setBorder(BorderFactory.createLineBorder(Color.black));
        background.add(right_label);

        JButton back = new JButton("Add User");
        back.setBounds(400,10,100,30);
        back.setFocusable(false);
//        back.addActionListener(this);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==back){
                    dispose();
                    new AddUser(username);
                }
            }
        });
        back.setCursor(cur);
        right_label.add(back);

        try{
            Conn c = new Conn();
            String q = ("select * from users where username = '"+username+"'");
            ResultSet rs = c.s.executeQuery(q);
            if(rs.next()){
                String pos = rs.getString("position");
                if(pos.equals("user")){
                    back.setVisible(false);
                }
                else {
                    back.setVisible(true);
                }
            }
        }catch (Exception exception){

        }

        JLabel title1 = new JLabel("Student Seating Arrangement and");
        title1.setBounds(40,25,340,30);
        title1.setFont(new Font("Baskerville Old Face", Font.BOLD, 23));
        left_label.add(title1);

        JLabel title2 = new JLabel("Supervisor Assign System");
        title2.setBounds(80,50,320,30);
        title2.setFont(new Font("Baskerville Old Face", Font.BOLD, 23));
        left_label.add(title2);



//        ############# Menu 1 ###################
//        ImageIcon i4 = new ImageIcon((new ImageIcon(ClassLoader.getSystemResource("img/instud.png"))).getImage().getScaledInstance(40,40,0));

          ImageIcon i4 = new ImageIcon(new ImageIcon("C:\\Users\\91877\\Documents\\NetBeansProjects\\Student\\src\\main\\java\\img\\instud.png").getImage().getScaledInstance(40,40,0));
        
        JLabel stu_img = new JLabel(i4);
        stu_img.setBounds(70,110,40,40);
        left_label.add(stu_img);

        in_stud = new JLabel("Insert Student Data");
        in_stud.setBounds(120,120,170,30);
//        in_stud.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
        in_stud.setFont(new Font("Baskerville Old Face", Font.BOLD, 18));
        in_stud.setForeground(Color.decode("#5e1bea"));
        in_stud.setCursor(cur);
        in_stud.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
               dispose();
                NewJFrame newJFrame = new NewJFrame(username);
                newJFrame.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                in_stud.setForeground(Color.black);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                in_stud.setForeground(Color.red);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                in_stud.setForeground(Color.decode("#5e1bea"));
            }
        });
        left_label.add(in_stud);



        //        ############# Menu 2 ###################
        ImageIcon i5 = new ImageIcon(new ImageIcon("C:\\Users\\91877\\Documents\\NetBeansProjects\\Student\\src\\main\\java\\img\\teacher.png").getImage().getScaledInstance(40,40,0));

        JLabel sup_img = new JLabel(i5);
        sup_img.setBounds(70,173,40,40);
        left_label.add(sup_img);

        in_sup = new JLabel("Insert Supervisor Data");
        in_sup.setBounds(120,180,190,30);
//        in_sup.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
        in_sup.setFont(new Font("Baskerville Old Face", Font.BOLD, 18));
        in_sup.setForeground(Color.decode("#5e1bea"));
        in_sup.setCursor(cur);
        in_sup.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                Supervisorinfo supervisor = new Supervisorinfo(username);
                supervisor.setVisible(true);

            }

            @Override
            public void mousePressed(MouseEvent e) {
                in_sup.setForeground(Color.black);
            }

            @Override
            public void mouseReleased(MouseEvent e) {


            }

            @Override
            public void mouseEntered(MouseEvent e) {
                in_sup.setForeground(Color.red);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                in_sup.setForeground(Color.decode("#5e1bea"));
            }
        });
        left_label.add(in_sup);


        //        ############# Menu 3 ###################
        ImageIcon i6 = new ImageIcon(new ImageIcon("C:\\Users\\91877\\Documents\\NetBeansProjects\\Student\\src\\main\\java\\img\\class.png").getImage().getScaledInstance(40,40,0));

        JLabel class_img = new JLabel(i6);
        class_img.setBounds(70,235,40,40);
        left_label.add(class_img);

        in_class = new JLabel("Insert Classroom Data");
        in_class.setBounds(120,240,190,30);
//        in_class.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
        in_class.setFont(new Font("Baskerville Old Face", Font.BOLD, 18));
        in_class.setForeground(Color.decode("#5e1bea"));
        in_class.setCursor(cur);
        in_class.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                in_class.setForeground(Color.decode("#5e1bea"));
                dispose();
                new Class_Upload(username);

            }

            @Override
            public void mousePressed(MouseEvent e) {
                in_class.setForeground(Color.black);
            }

            @Override
            public void mouseReleased(MouseEvent e) {


            }

            @Override
            public void mouseEntered(MouseEvent e) {
                in_class.setForeground(Color.red);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                in_class.setForeground(Color.decode("#5e1bea"));
            }
        });
        left_label.add(in_class);

        //        ############# Menu 4 ###################
        ImageIcon i7 = new ImageIcon(new ImageIcon("C:\\Users\\91877\\Documents\\NetBeansProjects\\Student\\src\\main\\java\\img\\timetable.png").getImage().getScaledInstance(30,30,0));

        JLabel time_img = new JLabel(i7);
        time_img.setBounds(70,293,40,40);
        left_label.add(time_img);

        in_time = new JLabel("Insert Time Table Data");
        in_time.setBounds(120,300,200,30);
//        in_time.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
        in_time.setFont(new Font("Baskerville Old Face", Font.BOLD, 18));
        in_time.setForeground(Color.decode("#5e1bea"));
        in_time.setCursor(cur);
        in_time.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                TimetableGenerator timetable = new TimetableGenerator(username);
                timetable.setVisible(true);

            }

            @Override
            public void mousePressed(MouseEvent e) {
                in_time.setForeground(Color.black);
            }

            @Override
            public void mouseReleased(MouseEvent e) {


            }

            @Override
            public void mouseEntered(MouseEvent e) {
                in_time.setForeground(Color.red);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                in_time.setForeground(Color.decode("#5e1bea"));
            }
        });
        left_label.add(in_time);



        //        ############# Menu 5 ###################
        ImageIcon i8 = new ImageIcon(new ImageIcon("C:\\Users\\91877\\Documents\\NetBeansProjects\\Student\\src\\main\\java\\img\\studreport.png").getImage().getScaledInstance(35,35,0));

        JLabel studrepo_img = new JLabel(i8);
        studrepo_img.setBounds(70,355,40,40);
        left_label.add(studrepo_img);


        stud_repo = new JLabel("Student Report");
        stud_repo.setBounds(120,360,140,30);
//        stud_repo.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
        stud_repo.setFont(new Font("Baskerville Old Face", Font.BOLD, 18));
        stud_repo.setForeground(Color.decode("#5e1bea"));
        stud_repo.setCursor(cur);
        stud_repo.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Student Seating Arrangement Report Under Construction","Message!!",JOptionPane.WARNING_MESSAGE);
                System.out.println("supervisor report under construction");
                stud_repo.setForeground(Color.decode("#5e1bea"));

            }

            @Override
            public void mousePressed(MouseEvent e) {
                stud_repo.setForeground(Color.black);
            }

            @Override
            public void mouseReleased(MouseEvent e) {


            }

            @Override
            public void mouseEntered(MouseEvent e) {
                stud_repo.setForeground(Color.red);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                stud_repo.setForeground(Color.decode("#5e1bea"));
            }
        });
        left_label.add(stud_repo);


        //        ############# Menu 6  ###################
        JLabel suprepo_img = new JLabel(i5);
        suprepo_img.setBounds(70,415,40,40);
        left_label.add(suprepo_img);

        sup_repo = new JLabel("Supervisor Report");
        sup_repo.setBounds(120,420,160,30);
//        sup_repo.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
        sup_repo.setFont(new Font("Baskerville Old Face", Font.BOLD, 18));
        sup_repo.setForeground(Color.decode("#5e1bea"));
        sup_repo.setCursor(cur);
        sup_repo.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                 dispose();
                Supervisor_Report supervisorreport = new Supervisor_Report(username);
                supervisorreport.setVisible(true);

            }

            @Override
            public void mousePressed(MouseEvent e) {
                sup_repo.setForeground(Color.black);
            }

            @Override
            public void mouseReleased(MouseEvent e) {


            }

            @Override
            public void mouseEntered(MouseEvent e) {
                sup_repo.setForeground(Color.red);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                sup_repo.setForeground(Color.decode("#5e1bea"));
            }
        });
        left_label.add(sup_repo);


        //        ############# Menu 7 ###################
        JLabel classrepo_img = new JLabel(i5);
        classrepo_img.setBounds(70,475,40,40);
        left_label.add(classrepo_img);

        class_repo= new JLabel("Classroom Report");
        class_repo.setBounds(120,480,160,30);
//        class_repot.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
        class_repo.setFont(new Font("Baskerville Old Face", Font.BOLD, 18));
        class_repo.setForeground(Color.decode("#5e1bea"));
        class_repo.setCursor(cur);
        class_repo.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Classroom Arrangement Report Under Construction","Message!!",JOptionPane.WARNING_MESSAGE);
                System.out.println("supervisor report under construction");
                class_repo.setForeground(Color.decode("#5e1bea"));

            }

            @Override
            public void mousePressed(MouseEvent e) {
                class_repo.setForeground(Color.black);
            }

            @Override
            public void mouseReleased(MouseEvent e) {


            }

            @Override
            public void mouseEntered(MouseEvent e) {
                class_repo.setForeground(Color.red);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                class_repo.setForeground(Color.decode("#5e1bea"));
            }
        });
        left_label.add(class_repo);
    }


}

