
package com.mycompany.student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Class_Csv extends JFrame implements ActionListener {
    public static void main(String[] args) {
        new Class_Csv("jain");
    }
    JButton select,add1,back;
    JTextField path;
    String pathh="";
    String username;

    Class_Csv(String username){
        username = this.username;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(1000,600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Classroom Data input");

        //        ######################## background label #######################3
        JLabel l1 = new JLabel();
        l1.setBounds(0,0,985,562);
        l1.setBorder(BorderFactory.createLineBorder(Color.black,2));
        l1.setBackground(Color.white);
        l1.setOpaque(true);
        this.add(l1);


//        ################################ navbar ###############################
        JLabel nav = new JLabel();
        nav.setBounds(3,3,980,40);
        nav.setOpaque(true);
        nav.setBackground(Color.decode("#d9d9d9"));
        l1.add(nav);

        JLabel title = new JLabel("Classroom Data");
        title.setBounds(10,7,160,25);
        title.setFont(new Font("Baskerville Old Face",Font.BOLD,22));
//        title.setBorder(BorderFactory.createLineBorder(Color.black,3));
        title.setForeground(Color.decode("#5e17eb"));
        nav.add(title);

        JLabel text = new JLabel("CSV file should have these coloumns :- ");
        text.setBounds(15,55,320,30);
        text.setFont(new Font("Baskerville Old Face",Font.PLAIN,20));
//        text.setBorder(BorderFactory.createLineBorder(Color.black,3));
        text.setForeground(Color.decode("#5e17eb"));
        l1.add(text);

        ImageIcon i1 = new ImageIcon(new ImageIcon("C:\\Users\\91877\\Documents\\NetBeansProjects\\Student\\src\\main\\java\\img\\sample.png").getImage().getScaledInstance(400,280,100));

        JLabel imgg = new JLabel(i1);
        imgg.setBounds(370,55,400,280);
        imgg.setBorder(BorderFactory.createLineBorder(Color.black,3));
        l1.add(imgg);

//        ####################### file selection ######################
        JLabel enter_file = new JLabel("Select file :- ");
        enter_file.setBounds(15,390,200,30);
        enter_file.setFont(new Font("Baskerville Old Face",Font.PLAIN,20));
//        enter_file.setBorder(BorderFactory.createLineBorder(Color.black,3));
        enter_file.setForeground(Color.decode("#5e17eb"));
        l1.add(enter_file);

        path = new JTextField();
        path.setBounds(130,390,350,30);
        path.setFont(new Font("Baskerville Old Face",Font.PLAIN,15));
//        path.setBackground(Color.decode("#d9d9d9"));
        path.setBorder(BorderFactory.createLineBorder(Color.black,1));
        path.setEditable(false);
        l1.add(path);

        select = new JButton("Select File");
        select.setBounds(500,390,100,30);
        select.setFont(new Font("Baskerville Old Face",Font.PLAIN,15));
        select.setHorizontalAlignment(2);
        select.setBackground(Color.decode("#d9d9d9"));
        select.setForeground(Color.decode("#5e17eb"));
        select.addActionListener(this);
        select.setHorizontalAlignment(JButton.CENTER);
        select.setFocusable(false);
        l1.add(select);

//        ####################### buttons #################
        add1 = new JButton("Add");
        add1.setBounds(390,430,90,30);
        add1.setFont(new Font("Baskerville Old Face",Font.PLAIN,15));
        add1.setHorizontalAlignment(2);
        add1.setBackground(Color.decode("#d9d9d9"));
        add1.setForeground(Color.decode("#5e17eb"));
        add1.addActionListener(this);
        add1.setHorizontalAlignment(JButton.CENTER);
        add1.setFocusable(false);
        l1.add(add1);

        back = new JButton("Back");
        back.setBounds(130,430,90,30);
        back.setFont(new Font("Baskerville Old Face",Font.PLAIN,15));
        back.setHorizontalAlignment(2);
        back.setBackground(Color.decode("#d9d9d9"));
        back.setForeground(Color.decode("#5e17eb"));
        back.addActionListener(this);
        back.setHorizontalAlignment(JButton.CENTER);
        back.setFocusable(false);
        l1.add(back);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==select){
            JFileChooser j = new JFileChooser();
            int response = j.showOpenDialog(null);
            if(response == JFileChooser.APPROVE_OPTION){
//                File file = new File(j.getSelectedFile().getAbsolutePath());
                pathh = j.getSelectedFile().getAbsolutePath();
                if(!(pathh.contains(".csv") || pathh.contains(".excel"))){
                    JOptionPane.showMessageDialog(null,"Select only csv file","Wrong file!",JOptionPane.ERROR_MESSAGE);
                    path.setText("");
                    pathh = "";
                }else {
                    path.setText(pathh);
                }


            }

        }else if(e.getSource()==back){
            this.dispose();
            new Class_Upload(username);
        }else if(e.getSource()==add1){
            if(path.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Select a file first.", "No file selected!", JOptionPane.ERROR_MESSAGE);

            } else {
                String paa = pathh;
                try {
                    for (int i = 1; i<pathh.length()-2;i++){
                        char ch = paa.charAt(i);
                        if(ch == '\\'){
                            paa = paa.substring(0,i)+"\\"+paa.substring(i,paa.length());
                            i=i+1;
                        }
                    }
                    System.out.println(paa);
                    Conn c = new Conn();
                    String query = ("load data INFILE '" + paa + "' into table classroom fields terminated by ',' ignore 1 rows;");
//                    System.out.println(query);
                    c.s.executeUpdate(query);
                    JOptionPane.showMessageDialog(null,"Record saved successfully!","Data Saved",JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "CSV file wrong data", "Wrong data!", JOptionPane.ERROR_MESSAGE);

                }
            }


        }

    }
}

