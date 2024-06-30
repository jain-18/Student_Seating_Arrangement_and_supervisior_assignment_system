/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.mycompany.student;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class ProgressBar {
    public static void main(String[] args) {
        new ProgressBar();
    }

    JFrame frame = new JFrame();
    JProgressBar bar = new JProgressBar();
    Timer timer;

    ProgressBar() {
        JLabel head = new JLabel("Wait for a while your Report is being Generated !!");
        head.setBounds(40, 20, 700, 40);
        head.setFont(new Font("Elephant", Font.BOLD, 25));
        frame.add(head);

        Color goldenColor = new Color(30, 144, 255);
        bar.setValue(0);
        bar.setBounds(20, 80, 700, 40);
        bar.setStringPainted(true);
        bar.setFont(new Font("MV Boli", Font.BOLD, 25));
        bar.setForeground(goldenColor);
        frame.add(bar);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(800, 220);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bar.getValue() < 100) {
                    bar.setValue(bar.getValue() + 2);
                } else {
                    bar.setString("Done!!");
                    frame.dispose(); 
                    timer.stop(); 
                  
        
                }
            }
        });
        timer.start();
    }
    
}
