
package com.mycompany.student;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class Class_Upload extends JFrame implements ActionListener{
    public static void main(String[] args) {
        new Class_Upload("jain");
    }

    public void Numvalidator(JTextField txtField)
    {
        txtField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_ENTER))) {
                    e.consume();
                    JOptionPane.showMessageDialog(null, "Only numbers are allowed!","WARNING!!",JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }
    JButton add1,clear,update,delete;
    JTextField block_no, bench_no;
    JTable tb;
    JComboBox floor;
    JButton dis,dela;

    Class_Upload(String username){
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

        JLabel upload = new JLabel("Upload through CSV");
        upload.setBounds(680,7,210,25);
        upload.setFont(new Font("Baskerville Old Face",Font.BOLD,22));
//        upload.setBorder(BorderFactory.createLineBorder(Color.black,1));
        upload.setHorizontalAlignment(JLabel.CENTER);
        upload.setVerticalAlignment(JLabel.CENTER);
        upload.setForeground(Color.decode("#5e17eb"));
        upload.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new Class_Csv(username);
                upload.setForeground(Color.decode("#5e1bea"));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                upload.setForeground(Color.black);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                upload.setForeground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                upload.setForeground(Color.decode("#5e1bea"));
            }
        });
        nav.add(upload);

        ImageIcon i1 = new ImageIcon(new ImageIcon("C:\\Users\\91877\\Documents\\NetBeansProjects\\Student\\src\\main\\java\\img\\arrow.png").getImage().getScaledInstance(30,30,0));
        JLabel arrow = new JLabel(i1);
        arrow.setBounds(895,4,40,30);
//        arrow.setBorder(BorderFactory.createLineBorder(Color.black,3));
        nav.add(arrow);



//        ################################ back button #################################
        JButton back = new JButton("Back");
        back.setBounds(24,70,65,30);
        back.setFont(new Font("Baskerville Old Face",Font.PLAIN,15));
        back.setHorizontalAlignment(2);
        back.setBackground(Color.decode("#d9d9d9"));
        back.setForeground(Color.decode("#5e17eb"));
        back.setFocusable(false);
        back.addActionListener(e -> {
            if(e.getSource()==back){
                dispose();
                new Menu1(username);
            }
        });
        l1.add(back);

//        ########################## text-fields and buttons ####################3
        JLabel blockno = new JLabel("Block No :- ");
        blockno.setBounds(40,150,100,30);
        blockno.setFont(new Font("Baskerville Old Face",Font.PLAIN,20));
//        blockno.setBorder(BorderFactory.createLineBorder(Color.black,3));
        blockno.setForeground(Color.decode("#5e17eb"));
        l1.add(blockno);

        block_no = new JTextField();
        block_no.setBounds(40,185,300,30);
        block_no.setFont(new Font("Baskerville Old Face",Font.PLAIN,20));
//        block_no.setBackground(Color.decode("#d9d9d9"));
        block_no.setBorder(BorderFactory.createLineBorder(Color.black,1));
        block_no.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if(c == KeyEvent.VK_ENTER){
                    bench_no.requestFocusInWindow();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        l1.add(block_no);

        JLabel bench = new JLabel("Bench Count :-  ");
        bench.setBounds(40,260,135,30);
        bench.setFont(new Font("Baskerville Old Face",Font.PLAIN,20));
//        bench.setBorder(BorderFactory.createLineBorder(Color.black,3));
        bench.setForeground(Color.decode("#5e17eb"));
        l1.add(bench);

        bench_no = new JTextField();
        bench_no.setBounds(40,295,300,30);
        bench_no.setFont(new Font("Baskerville Old Face",Font.PLAIN,20));
//        bench_no.setBackground(Color.decode("#d9d9d9"));
        bench_no.setBorder(BorderFactory.createLineBorder(Color.black,1));
        bench_no.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if(c == KeyEvent.VK_ENTER){
                    block_no.requestFocusInWindow();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        l1.add(bench_no);

//        ********************** buttons ******************

        clear = new JButton("CLEAR");
        clear.setBounds(40,380,130,30);
        clear.setFont(new Font("Baskerville Old Face",Font.PLAIN,15));
        clear.setHorizontalAlignment(2);
        clear.setBackground(Color.decode("#d9d9d9"));
        clear.setForeground(Color.decode("#5e17eb"));
        clear.addActionListener(this);
        clear.setHorizontalAlignment(JButton.CENTER);
        clear.setFocusable(false);
        l1.add(clear);


        update = new JButton("UPDATE");
        update.setBounds(210,380,130,30);
        update.setFont(new Font("Baskerville Old Face",Font.PLAIN,13));
        update.setHorizontalAlignment(2);
        update.setBackground(Color.decode("#d9d9d9"));
        update.setForeground(Color.decode("#5e17eb"));
        update.setFocusable(false);
        update.setHorizontalAlignment(JButton.CENTER);
        update.addActionListener(this);
        l1.add(update);

        delete = new JButton("DELETE");
        delete.setBounds(40,440,130,30);
        delete.setFont(new Font("Baskerville Old Face",Font.PLAIN,13));
        delete.setHorizontalAlignment(2);
        delete.setBackground(Color.decode("#d9d9d9"));
        delete.setForeground(Color.decode("#5e17eb"));
        delete.setFocusable(false);
        delete.setHorizontalAlignment(JButton.CENTER);
        delete.addActionListener(this);
        l1.add(delete);

        add1 = new JButton("ADD");
        add1.setBounds(210,440,130,30);
        add1.setFont(new Font("Baskerville Old Face",Font.PLAIN,15));
        add1.setHorizontalAlignment(2);
        add1.setBackground(Color.decode("#d9d9d9"));
        add1.setHorizontalAlignment(JButton.CENTER);
        add1.setForeground(Color.decode("#5e17eb"));
        add1.setFocusable(false);
        add1.addActionListener(this);
        l1.add(add1);

//        ########################### JTable ################################

        tb = new JTable();
        tb.setBounds(500,150,400,350);
        JScrollPane jsp = new JScrollPane(tb);
        jsp.setBounds(500,150,400,350);
        l1.add(jsp);
        try{
            Conn c = new Conn();
            String query = ("select * from classroom");
            ResultSet rs = c.s.executeQuery(query);
            tb.setModel((DbUtils.resultSetToTableModel(rs)));

        }catch (Exception e1){
            e1.printStackTrace();
        }

        String[] floors = {"0th floor","1st floor","2nd floor","3rd floor","4th floor","5th floor"};
        floor = new JComboBox(floors);
        floor.setBounds(500,110,100,30);
        floor.addActionListener(this);
        l1.add(floor);

        dis = new JButton("All Data");
        dis.setBounds(620,110,130,30);
        dis.setFont(new Font("Baskerville Old Face",Font.PLAIN,18));
        dis.setHorizontalAlignment(2);
        dis.setBackground(Color.decode("#d9d9d9"));
        dis.setForeground(Color.decode("#5e17eb"));
        dis.addActionListener(this);
        dis.setHorizontalAlignment(JButton.CENTER);
        dis.setFocusable(false);
        l1.add(dis);

        dela = new JButton("Delete All");
        dela.setBounds(770,110,130,30);
        dela.setFont(new Font("Baskerville Old Face",Font.PLAIN,15));
        dela.setHorizontalAlignment(2);
        dela.setBackground(Color.decode("#d9d9d9"));
        dela.setForeground(Color.decode("#5e17eb"));
        dela.addActionListener(this);
        dela.setHorizontalAlignment(JButton.CENTER);
        dela.setFocusable(false);
        l1.add(dela);

        Numvalidator(block_no);
        Numvalidator(bench_no);
    }



    //    ######################## All Operations ##########################
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==clear){
            bench_no.setText("");
            block_no.setText("");
        }
        if (e.getSource()==add1){
            if(block_no.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Enter block number","Incomplete data!",JOptionPane.ERROR_MESSAGE);
            }
            else if (bench_no.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Enter Bench count","Incomplete data!",JOptionPane.ERROR_MESSAGE);
            }
            else {
                try{
                    int blockk = Integer.parseInt(block_no.getText());
                    int benchh = Integer.parseInt(bench_no.getText());
                    Conn con = new Conn();
                    String query = ("insert into classroom values ('"+blockk+"','"+benchh+"')");
                    con.s.executeUpdate(query);
                    JOptionPane.showMessageDialog(null,"Record saved successfully!","Data Saved",JOptionPane.INFORMATION_MESSAGE);

                    query = ("select block_no as Block_no, bench_count as Bench_no from classroom");
                    ResultSet rs = con.s.executeQuery(query);
                    tb.setModel((DbUtils.resultSetToTableModel(rs)));
                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        }
        if(e.getSource()==delete){
            if(block_no.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Enter block number","Incomplete data!",JOptionPane.ERROR_MESSAGE);
            }
            else {
                try{
                    int blockk = Integer.parseInt(block_no.getText());

                    Conn c = new Conn();
                    String query = ("select * from classroom where block_no = '"+blockk+"'");
                    ResultSet rs = c.s.executeQuery(query);
                    if (rs.isBeforeFirst()) {
                        String q = ("delete from classroom where block_no = '"+blockk+"'");
                        c.s.executeUpdate(q);
                        JOptionPane.showMessageDialog(null,"Block Deleted successfully!","Data Saved",JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "No such block exist in database", "Wrong date", JOptionPane.ERROR_MESSAGE);
                    }

                    query = ("select block_no as Block_no, bench_count as Bench_no from classroom");
                    rs = c.s.executeQuery(query);
                    tb.setModel((DbUtils.resultSetToTableModel(rs)));
                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        }
        if(e.getSource()==update){
            if(block_no.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Enter block number","Incomplete data!",JOptionPane.ERROR_MESSAGE);
            }
            else if (bench_no.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Enter Bench count","Incomplete data!",JOptionPane.ERROR_MESSAGE);
            }
            else {
                try{
                    int blockk = Integer.parseInt(block_no.getText());
                    int benchh = Integer.parseInt(bench_no.getText());
                    Conn c = new Conn();
                    String query = ("select * from classroom where block_no = '"+blockk+"'");
                    ResultSet rs = c.s.executeQuery(query);
                    if (rs.isBeforeFirst()) {
                        String q = ("Update classroom set bench_count = '"+benchh+"' where block_no = '"+blockk+"'");
                        c.s.executeUpdate(q);
                        JOptionPane.showMessageDialog(null,"Block Updated successfully!","Data Saved",JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "No such block exist in database", "Wrong date", JOptionPane.ERROR_MESSAGE);
                    }

                    query = ("select block_no as Block_no, bench_count as Bench_no from classroom");
                    rs = c.s.executeQuery(query);
                    tb.setModel((DbUtils.resultSetToTableModel(rs)));
                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }

        } else if (e.getSource()==floor) {
            String floors = String.valueOf(floor.getSelectedItem());
                try{
                    Conn c = new Conn();
                    ResultSet rs;
                    String query;
                    switch (floors) {
                        case "0th floor" -> {
                            query = ("select * from classroom where block_no like '0%'");
                            rs = c.s.executeQuery(query);
                            tb.setModel((DbUtils.resultSetToTableModel(rs)));
                        }
                        case "1st floor" -> {
                            query = ("select * from classroom where block_no like '1%'");
                            rs = c.s.executeQuery(query);
                            tb.setModel((DbUtils.resultSetToTableModel(rs)));
                        }
                        case "2nd floor" -> {
                            query = ("select * from classroom where block_no like '2%'");
                            rs = c.s.executeQuery(query);
                            tb.setModel((DbUtils.resultSetToTableModel(rs)));
                        }
                        case "3rd floor" -> {
                            query = ("select * from classroom where block_no like '3%'");
                            rs = c.s.executeQuery(query);
                            tb.setModel((DbUtils.resultSetToTableModel(rs)));
                        }
                        case "4th floor" -> {
                            query = ("select * from classroom where block_no like '4%'");
                            rs = c.s.executeQuery(query);
                            tb.setModel((DbUtils.resultSetToTableModel(rs)));
                        }
                        case "5th floor" -> {
                            query = ("select * from classroom where block_no like '5%'");
                            rs = c.s.executeQuery(query);
                            tb.setModel((DbUtils.resultSetToTableModel(rs)));
                        }
                    }

                }catch (Exception e1){
                    e1.printStackTrace();
                }

        }else if(e.getSource()==dis){
            try{
                Conn c = new Conn();
                String query = ("select * from classroom");
                ResultSet rs = c.s.executeQuery(query);
                tb.setModel((DbUtils.resultSetToTableModel(rs)));

            }catch (Exception e1){
                e1.printStackTrace();
            }
        } else if (e.getSource()==dela) {
            int i = JOptionPane.showConfirmDialog(null,"Do you really want to delete all Data!","Warning!",JOptionPane.YES_NO_OPTION);
            if(i==0){
                try{
                    Conn c = new Conn();
                    String query = ("delete from classroom");
                    c.s.executeUpdate(query);
                    JOptionPane.showMessageDialog(null,"All data deleted successfully!","Data Saved",JOptionPane.INFORMATION_MESSAGE);

                    query = ("select block_no as Block_no, bench_count as Bench_no from classroom");
                    ResultSet rs = c.s.executeQuery(query);
                    tb.setModel((DbUtils.resultSetToTableModel(rs)));

                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }


        }

    }


}

