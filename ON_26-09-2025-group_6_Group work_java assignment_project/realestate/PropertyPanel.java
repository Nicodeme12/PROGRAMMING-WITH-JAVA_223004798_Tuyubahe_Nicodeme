package com.realestate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PropertyPanel extends JPanel implements ActionListener {

    private JTextField idTxt, titleTxt, descTxt, typeTxt, locationTxt, priceTxt, agentIdTxt, statusTxt;
    private JButton addBtn, updateBtn, deleteBtn, loadBtn;
    private JTable table;
    private DefaultTableModel model;

    public PropertyPanel() {
        setLayout(null);

        // Fields
        idTxt = new JTextField();
        titleTxt = new JTextField();
        descTxt = new JTextField();
        typeTxt = new JTextField();
        locationTxt = new JTextField();
        priceTxt = new JTextField();
        agentIdTxt = new JTextField();
        statusTxt = new JTextField();

        // Buttons
        addBtn = new JButton("Add");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");
        loadBtn = new JButton("Load");

        // Table
        String[] labels = {"Property ID", "Title", "Description", "Type", "Location", "Price", "Agent ID", "Status"};
        model = new DefaultTableModel(labels, 0);
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 300, 750, 200);
        add(sp);

        // Layout fields
        int y = 20;
        addField("Property ID", idTxt, y); y += 30;
        addField("Title", titleTxt, y); y += 30;
        addField("Description", descTxt, y); y += 30;
        addField("Type", typeTxt, y); y += 30;
        addField("Location", locationTxt, y); y += 30;
        addField("Price", priceTxt, y); y += 30;
        addField("Agent ID", agentIdTxt, y); y += 30;
        addField("Status", statusTxt, y); y += 30;

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
                String sql = "INSERT INTO property(Property_id, title, description, property_type, Location, price, agent_id, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(idTxt.getText()));
                ps.setString(2, titleTxt.getText());
                ps.setString(3, descTxt.getText());
                ps.setString(4, typeTxt.getText());
                ps.setString(5, locationTxt.getText());
                ps.setDouble(6, Double.parseDouble(priceTxt.getText()));
                ps.setInt(7, Integer.parseInt(agentIdTxt.getText()));
                ps.setString(8, statusTxt.getText());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Property added successfully!");
            } 
            else if (e.getSource() == updateBtn) {
                String sql = "UPDATE property SET title=?, description=?, property_type=?, Location=?, price=?, agent_id=?, Status=? WHERE Property_id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, titleTxt.getText());
                ps.setString(2, descTxt.getText());
                ps.setString(3, typeTxt.getText());
                ps.setString(4, locationTxt.getText());
                ps.setDouble(5, Double.parseDouble(priceTxt.getText()));
                ps.setInt(6, Integer.parseInt(agentIdTxt.getText()));
                ps.setString(7, statusTxt.getText());
                ps.setInt(8, Integer.parseInt(idTxt.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Property updated successfully!");
            } 
            else if (e.getSource() == deleteBtn) {
                String sql = "DELETE FROM property WHERE Property_id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(idTxt.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Property deleted successfully!");
            } 
            else if (e.getSource() == loadBtn) {
                model.setRowCount(0);
                String sql = "SELECT * FROM property";
                ResultSet rs = con.createStatement().executeQuery(sql);
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("Property_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getString("property_type"),
                            rs.getString("Location"),
                            rs.getDouble("price"),
                            rs.getInt("agent_id"),
                            rs.getString("Status")
                    });
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Property Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new PropertyPanel());
        frame.setVisible(true);
    }
}
