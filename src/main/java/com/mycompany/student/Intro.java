
package com.mycompany.student;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

public class Intro extends JFrame implements ActionListener {
    public static void main(String[] args) {
        new Intro();
    }
;
    Cursor cur = new Cursor(Cursor.HAND_CURSOR);
    JButton sign;
    JTextField username;
    JPasswordField password;

    Intro() {
        this.setVisible(true);
        this.setSize(1000, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.white);
        this.setLayout(null);


        ImageIcon i1 = new ImageIcon("C:\\Users\\91877\\Documents\\NetBeansProjects\\Student\\src\\main\\java\\img\\intro.jpg");
        Image i2 = i1.getImage().getScaledInstance(500, 600, 100);
        ImageIcon i3 = new ImageIcon(i2);

        JLabel l1 = new JLabel(i3);
        l1.setBounds(0, 0, 500, 563);
        this.add(l1);


        JLabel wel = new JLabel("WELCOME BACK !");
        wel.setBounds(570, 40, 400, 50);
        wel.setFont(new Font("ALICE", Font.BOLD, 40));
        this.add(wel);

        ImageIcon i4 = new ImageIcon("C:\\Users\\91877\\Documents\\NetBeansProjects\\Student\\src\\main\\java\\img\\intro_user.png");
        Image i5 = i4.getImage().getScaledInstance(230, 190, 1000);
        ImageIcon i6 = new ImageIcon(i5);

        JLabel user = new JLabel(i6);
        user.setBounds(630, 70, 230, 190);
        this.add(user);

        JLabel usern = new JLabel("USERNAME :");
        usern.setBounds(540, 240, 400, 50);
        usern.setFont(new Font("ALICE", Font.BOLD, 20));
        usern.setForeground(Color.decode("#0159e8"));
        this.add(usern);

        username = new JTextField();
        username.setBounds(540, 280, 420, 30);
        username.setFont(new Font("Alice", Font.PLAIN, 18));
        username.setBackground(Color.decode("#d9d9d9"));
        username.setCursor(cur);
        username.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    password.requestFocusInWindow();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    password.requestFocusInWindow();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        this.add(username);

        JLabel pass = new JLabel("PASSWORD :");
        pass.setBounds(540, 340, 400, 50);
        pass.setFont(new Font("ALICE", Font.BOLD, 20));
        pass.setForeground(Color.decode("#0159e8"));
        this.add(pass);

        JLabel fpass = new JLabel("Forgot Password?");
        fpass.setBounds(810, 410, 145, 20);
        fpass.setFont(new Font("ALICE", Font.BOLD, 15));
        fpass.setForeground(Color.decode("#0159e8"));
//        fpass.setBorder(BorderFactory.createLineBorder(Color.black,1));
        fpass.setCursor(cur);
        fpass.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new Forget();
                fpass.setForeground(Color.decode("#5e1bea"));

            }

            @Override
            public void mousePressed(MouseEvent e) {
                fpass.setForeground(Color.black);
            }

            @Override
            public void mouseReleased(MouseEvent e) {


            }

            @Override
            public void mouseEntered(MouseEvent e) {
                fpass.setForeground(Color.red);
                fpass.setBounds(790, 410, 165, 20);
                fpass.setFont(new Font("ALICE", Font.BOLD, 18));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                fpass.setForeground(Color.decode("#5e1bea"));
                fpass.setBounds(810, 410, 145, 20);
                fpass.setFont(new Font("ALICE", Font.BOLD, 15));
            }
        });
        this.add(fpass);

        password = new JPasswordField();
        password.setBounds(540, 380, 420, 30);
        password.setFont(new Font("Alice", Font.PLAIN, 18));
        password.setBackground(Color.decode("#d9d9d9"));
        password.setCursor(cur);
        password.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyCode()== KeyEvent.VK_ENTER){
                    sign.doClick();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()== KeyEvent.VK_ENTER){
                    sign.doClick();
                }
                else if(e.getKeyCode()==KeyEvent.VK_UP){
                    username.requestFocusInWindow();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        this.add(password);

        sign = new JButton("SIGN IN");
        sign.setFont(new Font("ALICE", Font.BOLD, 18));
        sign.setBackground(Color.decode("#d9d9d9"));
        sign.setForeground(Color.decode("#0159e8"));
        sign.setFocusable(false);
        sign.setBounds(540, 470, 100, 40);
        sign.addActionListener(this);
        sign.setCursor(cur);
        this.add(sign);

        JButton exit = new JButton("CLEAR");
        exit.setFont(new Font("ALICE", Font.BOLD, 18));
        exit.setBackground(Color.decode("#d9d9d9"));
        exit.setForeground(Color.decode("#0159e8"));
        exit.setBounds(850, 470, 100, 40);
        exit.setFocusable(false);
        exit.setCursor(cur);
        this.add(exit);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sign) {
            try {
                if (username.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Enter username", "Incomplete data!", JOptionPane.ERROR_MESSAGE);
                } else if (password.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Enter password", "Incomplete data!", JOptionPane.ERROR_MESSAGE);
                } else {
                    String usernamee = username.getText();
                    String passwaord = password.getText();

                    String query = ("select *  from users where username = '" + usernamee + "' and password = '" + passwaord + "'");
                    Conn c = new Conn();
                    ResultSet rs = c.s.executeQuery(query);
                    if (rs.next()) {
                        this.dispose();
                        new Menu1(usernamee);
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect Username and password! ", "Incomplete data", JOptionPane.ERROR_MESSAGE);
                    }
                }


                } catch(Exception exception){
                    exception.printStackTrace();
                }
            }
        }
    }

