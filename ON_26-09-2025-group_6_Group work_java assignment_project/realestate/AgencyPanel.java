package com.realestate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AgencyPanel extends JPanel implements ActionListener {

    private JTextField idTxt, nameTxt, phoneTxt, emailTxt, agencyNameTxt;
    private JButton addBtn, updateBtn, deleteBtn, loadBtn;
    private JTable table;
    private DefaultTableModel model;

    public AgencyPanel() {
        setLayout(null);

        // Fields
        idTxt = new JTextField();
        nameTxt = new JTextField();
        phoneTxt = new JTextField();
        emailTxt = new JTextField();
        agencyNameTxt = new JTextField();

        // Buttons
        addBtn = new JButton("Add");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");
        loadBtn = new JButton("Load");

        // Table
        String[] labels = {"Agent ID", "Name", "Email", "Phone", "Agency Name"};
        model = new DefaultTableModel(labels, 0);
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 250, 750, 200);
        add(sp);

        // Layout fields
        int y = 20;
        addField("Agent ID", idTxt, y); y += 30;
        addField("Name", nameTxt, y); y += 30;
        addField("Email", emailTxt, y); y += 30;
        addField("Phone", phoneTxt, y); y += 30;
        addField("Agency Name", agencyNameTxt, y); y += 30;

        // Buttons
        addButtons();
    }

    private void addField(String lbl, JComponent txt, int y) {
        JLabel l = new JLabel(lbl);
        l.setBounds(20, y, 100, 25);
        txt.setBounds(130, y, 150, 25);
        add(l);
        add(txt);
    }

    private void addButtons() {
        addBtn.setBounds(320, 20, 100, 30);
        updateBtn.setBounds(320, 60, 100, 30);
        deleteBtn.setBounds(320, 100, 100, 30);
        loadBtn.setBounds(320, 140, 100, 30);

        add(addBtn);
        add(updateBtn);
        add(deleteBtn);
        add(loadBtn);

        addBtn.addActionListener(this);
        updateBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        loadBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try (Connection con = DB.getConnection()) {

            if (e.getSource() == addBtn) {
                String sql = "INSERT INTO agency(agent_id, name, Email, Phone, Agency_name) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(idTxt.getText()));
                ps.setString(2, nameTxt.getText());
                ps.setString(3, emailTxt.getText());
                ps.setString(4, phoneTxt.getText());
                ps.setString(5, agencyNameTxt.getText());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Agent added successfully!");
            } 
            else if (e.getSource() == updateBtn) {
                String sql = "UPDATE agency SET name=?, Email=?, Phone=?, Agency_name=? WHERE agent_id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, nameTxt.getText());
                ps.setString(2, emailTxt.getText());
                ps.setString(3, phoneTxt.getText());
                ps.setString(4, agencyNameTxt.getText());
                ps.setInt(5, Integer.parseInt(idTxt.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Agent updated successfully!");
            } 
            else if (e.getSource() == deleteBtn) {
                String sql = "DELETE FROM agency WHERE agent_id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(idTxt.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Agent deleted successfully!");
            } 
            else if (e.getSource() == loadBtn) {
                model.setRowCount(0);
                String sql = "SELECT * FROM agency";
                ResultSet rs = con.createStatement().executeQuery(sql);
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("agent_id"),
                            rs.getString("name"),
                            rs.getString("Email"),
                            rs.getString("Phone"),
                            rs.getString("Agency_name")
                    });
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Agency Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new AgencyPanel());
        frame.setVisible(true);
    }
}
