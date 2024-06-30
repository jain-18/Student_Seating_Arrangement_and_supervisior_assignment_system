/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.student;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
/**
 *
 * @author 91877
 */
public class ViewAssignReport extends JFrame{
    JTable tb;
    JButton download;
    public static void main(String[] args) {
        new ViewAssignReport();
    }
    ViewAssignReport(){
        this.getDefaultCloseOperation();
        this.setLayout(null);
        this.setSize(1500,750);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Supervisor Assignment: Admin View");

        // Bbackground label
        JLabel l1 = new JLabel();
        l1.setBounds(1,0,1500,798);
        l1.setBorder(BorderFactory.createLineBorder(Color.black,2));
        l1.setBackground(Color.white);
        l1.setOpaque(true);
        this.add(l1);

        // Supervisor report table

        tb = new JTable();
        tb.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Set auto-resize mode to allow cells to take required size
        JScrollPane jsp = new JScrollPane(tb);
        jsp.setBounds(1, 40, 1480, 670);
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        l1.add(jsp); // Add JScrollPane to the panel first

        try {
            Conn c = new Conn();
            String query = ("select * from supervisor_assignments");
            ResultSet rs = c.s.executeQuery(query);
            tb.setModel(DbUtils.resultSetToTableModel(rs));

            // Adjust column widths based on cell content
            for (int column = 0; column < tb.getColumnCount(); column++) {
                TableColumn tableColumn = tb.getColumnModel().getColumn(column);
                int preferredWidth = 0;
                for (int row = 0; row < tb.getRowCount(); row++) {
                    TableCellRenderer cellRenderer = tb.getCellRenderer(row, column);
                    Component c1 = tb.prepareRenderer(cellRenderer, row, column);
                    int width = c1.getPreferredSize().width + tb.getIntercellSpacing().width;
                    preferredWidth = Math.max(preferredWidth, width);
                }
                tableColumn.setPreferredWidth(preferredWidth);
            }

            // Set the height of the JTableHeader
            JTableHeader header = tb.getTableHeader();
            header.setPreferredSize(new Dimension(80, 40)); // Set the desired height for the column headers

            // Set custom cell renderer for wrapping text and tooltips
            tb.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
//                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//                    if (value != null) {
//                        if (c instanceof JLabel label) {
//                            label.setText("<html><body style='width: 200px;'>" + value + "</body></html>"); // Set the desired width for cell content
//                            label.setToolTipText(value.toString()); // Set tooltip for cell content
//                        }
//                    } else {
//                        if (c instanceof JLabel label) {
//                            label.setText(""); // Set empty text for null values
//                            label.setToolTipText(""); // Set empty tooltip for null values
//                        }
//                    }
//                    return c;
//                }
            });

            // Set custom header renderer
            tb.getTableHeader().setDefaultRenderer(new CustomHeaderRenderer(tb));

        } catch (Exception e1) {
            e1.printStackTrace();
        }

        //button
        download = new JButton("Download Report");
        download.setBounds(9,5,160,30);
        download.setFocusable(false);
        download.setCursor(new Cursor(Cursor.HAND_CURSOR));
        download.setBackground(Color.decode("#d9d9d9"));
        download.setForeground(Color.decode("#5e17eb"));
        download.setFont(new Font("Baskerville Old Face",Font.PLAIN,15));
        download.addActionListener(e -> {
            String filePath = "C:\\Users\\91877\\Documents\\jasper reports/Supervisor_Assignment.csv";
            exportToCSV(tb, filePath);
            JOptionPane.showMessageDialog(ViewAssignReport.this, "CSV file downloaded successfully in Jasper Report Folder.", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        l1.add(download);






    }
    public static void exportToCSV(JTable table, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            TableModel model = table.getModel();

            // Write column headers
            for (int col = 0; col < model.getColumnCount(); col++) {
                writer.append(model.getColumnName(col));
                if (col < model.getColumnCount() - 1) {
                    writer.append(",");
                }
            }
            writer.append("\n");

            // Write data rows
            for (int row = 0; row < model.getRowCount(); row++) {
                for (int col = 0; col < model.getColumnCount(); col++) {
                    Object value = model.getValueAt(row, col);
                    writer.append(value != null ? value.toString() : "");
                    if (col < model.getColumnCount() - 1) {
                        writer.append(",");
                    }
                }
                writer.append("\n");
            }

            System.out.println("CSV file exported successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while exporting to CSV.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


class CustomHeaderRenderer implements TableCellRenderer {
    DefaultTableCellRenderer renderer;

    public CustomHeaderRenderer(JTable table) {
        renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER); // Set the alignment if needed
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Set different colors for different columns (customize this logic as needed)
        if (column == 0) {
            component.setForeground(Color.RED);
        } else if (column == 1) {
            component.setForeground(Color.GREEN);
        } else {
            component.setForeground(Color.BLUE);
        }

        return component;
    }
}