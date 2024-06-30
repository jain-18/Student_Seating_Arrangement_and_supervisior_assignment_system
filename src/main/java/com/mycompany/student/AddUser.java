

package com.mycompany.student;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AddUser extends JFrame implements ActionListener {
    public static void main(String[] args) {
        new AddUser("jain");
    }

//    String qq[] = {"Who is your best Friend?","Favourite Superhero?","Favourite movie?"};

    JButton sub,back,delete;
    JTable tb;

    JTextField namee,usernamee,password,ans;
    Choice security;
    Font font = new Font("",Font.PLAIN,15);
    Cursor cur = new Cursor(Cursor.HAND_CURSOR);
    String username;
    AddUser(String username){
        username = this.username;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(0,0,1000,600);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(true);
        this.getContentPane().setBackground(Color.white);


//        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("image/white.png"));
//        Image i2 = i1.getImage().getScaledInstance(700,900,0);
//        ImageIcon i3 = new ImageIcon(i2);
        JLabel label = new JLabel();
        label.setBounds(1,1,983,561);
        label.setBorder(BorderFactory.createLineBorder(Color.black,5));
        this.add(label);

//        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("img/signup1.jpg"));
//        JLabel ss = new JLabel(i4);
//        ss.setBounds(400,100,700,400);
//        label.add(ss);

        tb = new JTable();
        tb.setBounds(550,130,400,350);
        tb.setAlignmentX(JTable.SOMEBITS);
        JScrollPane jsp = new JScrollPane(tb);
        jsp.setBounds(550,130,400,350);
        label.add(jsp);
        try{
            Conn c = new Conn();
            String query = ("select name,Username,position from users");
            ResultSet rs = c.s.executeQuery(query);
            tb.setModel((DbUtils.resultSetToTableModel(rs)));

        }catch (Exception e1){
            e1.printStackTrace();
        }

        JLabel head = new JLabel("Add User");
        head.setBounds(400,20,210,40);
        head.setFont(new Font("Elephant",Font.BOLD,35));
        label.add(head);

        JLabel un = new JLabel();
        un.setBounds(350,70,270,2);
        un.setBackground(Color.black);
        un.setOpaque(true);
        label.add(un);

        JLabel name = new JLabel("Name :- ");
        name.setBounds(130,140,100,25);
        name.setFont(new Font("Baskerville Old Face",Font.BOLD,25));
        name.setForeground(Color.decode("#5e17eb"));
        label.add(name);

        JLabel sernamee = new JLabel("Username :- ");
        sernamee.setBounds(90,200,150,25);
        sernamee.setFont(new Font("Baskerville Old Face",Font.BOLD,25));
        sernamee.setForeground(Color.decode("#5e17eb"));
        label.add(sernamee);


        JLabel pass = new JLabel("Password :- ");
        pass.setBounds(100,260,150,25);
        pass.setFont(new Font("Baskerville Old Face",Font.BOLD,25));
        pass.setForeground(Color.decode("#5e17eb"));
        label.add(pass);

        JLabel securityq = new JLabel("Security Ques. :-");
        securityq.setBounds(40,320,185,25);
        securityq.setFont(new Font("Baskerville Old Face",Font.BOLD,25));
//        securityq.setBorder(BorderFactory.createLineBorder(Color.black));
        securityq.setForeground(Color.decode("#5e17eb"));
        label.add(securityq);

        JLabel securitya = new JLabel("Answer :-");
        securitya.setBounds(115,380,160,25);
        securitya.setFont(new Font("Baskerville Old Face",Font.BOLD,25));
        securitya.setForeground(Color.decode("#5e17eb"));
        label.add(securitya);


        namee = new JTextField();
        namee.setBounds(230,143,250,25);
        namee.setCursor(cur);
        namee.setFont(font);
        label.add(namee);

        usernamee = new JTextField();
        usernamee.setBounds(230,203,250,25);
        usernamee.setCursor(cur);
        usernamee.setFont(font);
        label.add(usernamee);


        password = new JTextField();
        password.setBounds(230,263,250,25);
        password.setCursor(cur);
        password.setFont(font);
        label.add(password);

        security = new Choice();
        security.add("Who is your best Friend?");
        security.add("Favourite Superhero?");
        security.add("Favourite movie?");
        security.setBounds(230,323,250,25);
        security.setCursor(cur);
        security.setFont(new Font("",Font.PLAIN,14));
        label.add(security);

        ans = new JTextField();
        ans.setBounds(230,383,250,25);
        ans.setCursor(cur);
        ans.setFont(font);
        label.add(ans);


        back = new JButton("Back");
        back.setBounds(70,455,100,30);
        back.setFocusable(false);
        back.addActionListener(this);
        back.setCursor(cur);
        label.add(back);

        sub = new JButton("Add");
        sub.setBounds(220,455,100,30);
        sub.setFocusable(false);
        sub.setCursor(cur);
        sub.addActionListener(this);
        label.add(sub);

        delete = new JButton("Delete");
        delete.setBounds(370,455,100,30);
        delete.setFocusable(false);
        delete.setCursor(cur);
        delete.addActionListener(this);
        label.add(delete);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==sub){
            if(namee.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Enter Name!","Incomplete data",JOptionPane.ERROR_MESSAGE);
            }
            else if(usernamee.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Enter Username!","Incomplete data",JOptionPane.ERROR_MESSAGE);
            }
            else if(password.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Enter Password!","Incomplete data",JOptionPane.ERROR_MESSAGE);
            }
            else if(ans.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Enter Answer!","Incomplete data",JOptionPane.ERROR_MESSAGE);
            }
            else {
                try{
                    Conn c = new Conn();
                    String q = ("select * from users where username = '"+usernamee.getText()+"'");
                    ResultSet rs = c.s.executeQuery(q);
                    if(rs.next()){
                        JOptionPane.showMessageDialog(null,"This email-id is already taken!","Data problem",JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        String query = "insert into users values('"+namee.getText()+"','"+usernamee.getText()+"','"+password.getText()+"','"+String.valueOf(security.getSelectedItem())+"','"+ans.getText()+"','user')";
                        c.s.executeUpdate(query);
                        JOptionPane.showMessageDialog(null,"Data Saved Successfully","Message",JOptionPane.PLAIN_MESSAGE);
                    }
                    String query = ("select name,Username,position from users");
                    rs = c.s.executeQuery(query);
                    tb.setModel((DbUtils.resultSetToTableModel(rs)));
            } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }

        }else if(e.getSource()==delete){
            if(usernamee.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Enter Username!","Incomplete data",JOptionPane.ERROR_MESSAGE);
            }
            else {
                try{
                    Conn c = new Conn();
                    String q = ("delete from users where username = '"+usernamee.getText()+"'");
                    c.s.executeUpdate(q);
                    JOptionPane.showMessageDialog(null,"User deleted Successfully","Message",JOptionPane.PLAIN_MESSAGE);
                    String query = ("select name,Username,position from users");
                    ResultSet rs = c.s.executeQuery(query);
                    tb.setModel((DbUtils.resultSetToTableModel(rs)));
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        }
        else if(e.getSource()==back){
            this.dispose();
            new Menu1(username);
        }

    }
}

