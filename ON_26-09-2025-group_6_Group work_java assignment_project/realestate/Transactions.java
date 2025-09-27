package com.realestate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Transactions extends JPanel implements ActionListener {

    private JTextField idTxt, propertyIdTxt, clientIdTxt, agentIdTxt, typeTxt, priceTxt, dateTxt, statusTxt;
    private JButton addBtn, updateBtn, deleteBtn, loadBtn;
    private JTable table;
    private DefaultTableModel model;

    public Transactions() {
        setLayout(null);

        // Fields
        idTxt = new JTextField();
        propertyIdTxt = new JTextField();
        clientIdTxt = new JTextField();
        agentIdTxt = new JTextField();
        typeTxt = new JTextField();
        priceTxt = new JTextField();
        dateTxt = new JTextField();
        statusTxt = new JTextField();

        // Buttons
        addBtn = new JButton("Add");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");
        loadBtn = new JButton("Load");

        // Table
        String[] labels = {"Transaction ID", "Property ID", "Client ID", "Agent ID", "Type", "Price", "Date", "Status"};
        model = new DefaultTableModel(labels, 0);
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 300, 750, 200);
        add(sp);

        // Layout fields
        int y = 20;
        addField("Transaction ID", idTxt, y); y += 30;
        addField("Property ID", propertyIdTxt, y); y += 30;
        addField("Client ID", clientIdTxt, y); y += 30;
        addField("Agent ID", agentIdTxt, y); y += 30;
        addField("Type", typeTxt, y); y += 30;
        addField("Price", priceTxt, y); y += 30;
        addField("Date", dateTxt, y); y += 30;
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
                String sql = "INSERT INTO transactions(transaction_id, property_id, client_id, agent_id, transaction_type, price, transaction_date, payment_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(idTxt.getText()));
                ps.setInt(2, Integer.parseInt(propertyIdTxt.getText()));
                ps.setInt(3, Integer.parseInt(clientIdTxt.getText()));
                ps.setInt(4, Integer.parseInt(agentIdTxt.getText()));
                ps.setString(5, typeTxt.getText());
                ps.setDouble(6, Double.parseDouble(priceTxt.getText()));
                ps.setString(7, dateTxt.getText()); // Format: YYYY-MM-DD
                ps.setString(8, statusTxt.getText());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Transaction added successfully!");
            } 
            else if (e.getSource() == updateBtn) {
                String sql = "UPDATE transactions SET property_id=?, client_id=?, agent_id=?, transaction_type=?, price=?, transaction_date=?, payment_status=? WHERE transaction_id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(propertyIdTxt.getText()));
                ps.setInt(2, Integer.parseInt(clientIdTxt.getText()));
                ps.setInt(3, Integer.parseInt(agentIdTxt.getText()));
                ps.setString(4, typeTxt.getText());
                ps.setDouble(5, Double.parseDouble(priceTxt.getText()));
                ps.setString(6, dateTxt.getText());
                ps.setString(7, statusTxt.getText());
                ps.setInt(8, Integer.parseInt(idTxt.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Transaction updated successfully!");
            } 
            else if (e.getSource() == deleteBtn) {
                String sql = "DELETE FROM transactions WHERE transaction_id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(idTxt.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Transaction deleted successfully!");
            } 
            else if (e.getSource() == loadBtn) {
                model.setRowCount(0);
                String sql = "SELECT * FROM transactions";
                ResultSet rs = con.createStatement().executeQuery(sql);
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("transaction_id"),
                            rs.getInt("property_id"),
                            rs.getInt("client_id"),
                            rs.getInt("agent_id"),
                            rs.getString("transaction_type"),
                            rs.getDouble("price"),
                            rs.getString("transaction_date"),
                            rs.getString("payment_status")
                    });
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Transaction Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new Transactions());
        frame.setVisible(true);
    }
}
