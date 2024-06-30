
package com.mycompany.student;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class Forget extends JFrame implements ActionListener {
    public static void main(String[] args) {
        new Forget();
    }
    JButton ret,check,back;
    String users;
    JTextField username,name,secur_q,secur_a,password;
    Cursor cur = new Cursor(Cursor.HAND_CURSOR);

    Forget(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setBounds(0,0,1000,600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(true);
        this.getContentPane().setBackground(Color.white);

//        ImageIcon i1 = new ImageIcon((new ImageIcon(ClassLoader.getSystemResource("img/forget.jpg")).getImage().getScaledInstance(1000,800,5)));
        JLabel label = new JLabel();
        label.setBounds(0,0,986,563);
        label.setBorder(BorderFactory.createLineBorder(Color.black,3));
        this.add(label);

        JLabel head = new JLabel("Forget Password ");
        head.setBounds(380,40,300,35);
        head.setFont(new Font("Elephant",Font.BOLD,30));
        head.setForeground(Color.decode("#5e17eb"));
        label.add(head);

        JLabel user = new JLabel("Username :- ");
        user.setBounds(190,150,160,25);
        user.setFont(new Font("Baskerville Old Face",Font.BOLD,25));
        user.setForeground(Color.decode("#5e17eb"));
        label.add(user);


        JLabel sec_q = new JLabel("Security Question :- ");
        sec_q.setBounds(110,210,230,25);
        sec_q.setFont(new Font("Baskerville Old Face",Font.BOLD,25));
        sec_q.setForeground(Color.decode("#5e17eb"));
        label.add(sec_q);

        JLabel sec_a = new JLabel("Answer :- ");
        sec_a.setBounds(220,270,130,25);
        sec_a.setFont(new Font("Baskerville Old Face",Font.BOLD,25));
        sec_a.setForeground(Color.decode("#5e17eb"));
        label.add(sec_a);

        JLabel pass = new JLabel("Password :- ");
        pass.setBounds(200,330,130,25);
        pass.setFont(new Font("Baskerville Old Face",Font.BOLD,25));
        pass.setForeground(Color.decode("#5e17eb"));
        label.add(pass);

        username = new JTextField();
        username.setBounds(340,150,300,28);
        username.setFont(new Font("Baskerville Old Face",Font.BOLD,22));
        username.setBorder(BorderFactory.createLineBorder(Color.black,1));
        username.setCursor(cur);
        label.add(username);


        secur_q= new JTextField();
        secur_q.setBounds(340,210,300,28);
        secur_q.setFont(new Font("Baskerville Old Face",Font.BOLD,22));
        secur_q.setBorder(BorderFactory.createLineBorder(Color.black,1));
        secur_q.setCursor(cur);
        label.add(secur_q);

        secur_a= new JTextField();
        secur_a.setBounds(340,270,300,28);
        secur_a.setFont(new Font("Baskerville Old Face",Font.BOLD,22));
        secur_a.setBorder(BorderFactory.createLineBorder(Color.black,1));
        secur_a.setCursor(cur);
        label.add(secur_a);

        password = new JTextField();
        password.setBounds(340,330,300,28);
        password.setFont(new Font("Baskerville Old Face",Font.BOLD,22));
        password.setBorder(BorderFactory.createLineBorder(Color.black,1));
        password.setCursor(cur);
        label.add(password);

        ret = new JButton("Retrieve");
        ret.setBounds(650,150,100,28);
        ret.setFocusable(false);
        ret.setBorder(BorderFactory.createLineBorder(Color.black,1));
        ret.setBackground(Color.lightGray);
        ret.setFont(new Font("Baskerville Old Face",Font.BOLD,20));
        ret.setCursor(cur);
        ret.addActionListener(this);
        label.add(ret);

        check = new JButton("check");
        check.setBounds(650,270,100,28);
        check.setFocusable(false);
        check.setBorder(BorderFactory.createLineBorder(Color.black,1));
        check.setBackground(Color.lightGray);
        check.setFont(new Font("Baskerville Old Face",Font.BOLD,22));
        check.setCursor(cur);
        check.addActionListener(this);
        label.add(check);

        back = new JButton("Back");
        back.setBounds(440,410,150,28);
        back.setFocusable(false);
        back.setBorder(BorderFactory.createLineBorder(Color.black,1));
        back.setBackground(Color.lightGray);
        back.setFont(new Font("Baskerville Old Face",Font.BOLD,25));
        back.setCursor(cur);
        back.addActionListener(this);
        label.add(back);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==ret)
        {
            if(username.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Enter Username","Incomplete data",JOptionPane.ERROR_MESSAGE);
            }
            else {
                users = username.getText();
                try{
                    String query = ("Select * from users where username = '"+users+"'");
                    Conn c = new Conn();
                    ResultSet rs = c.s.executeQuery(query);


                    if(rs.isBeforeFirst()){
                        while(rs.next()){
                            secur_q.setText(rs.getString("security_ques"));
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"No such user exits!","Wrong data",JOptionPane.ERROR_MESSAGE);
                    }

                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        }
        else if(e.getSource() == check){
            try{
                if(username.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Enter Username","Incomplete data",JOptionPane.ERROR_MESSAGE);
                }
                else {
                    String query = ("Select * from users where username = '" + username.getText() + "' and security_ans = '" + secur_a.getText() + "'");
                    Conn c = new Conn();
                    ResultSet rs = c.s.executeQuery(query);

//                while(rs.next()){
//                    password.setText(rs.getString("password"));
//                }
                    if (rs.isBeforeFirst()) {
                        while (rs.next()) {
                            password.setText(rs.getString("password"));
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Wrong answer!", "Wrong data", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }catch (Exception exception){
                exception.printStackTrace();
            }
        }
        else if(e.getSource()==back){
            this.dispose();
            new Intro();
        }
    }
}

