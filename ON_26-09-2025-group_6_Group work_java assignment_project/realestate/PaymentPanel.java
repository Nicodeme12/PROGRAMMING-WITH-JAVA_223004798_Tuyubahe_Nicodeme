package com.realestate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PaymentPanel extends JPanel implements ActionListener {

    private JTextField paymentIdTxt, transactionIdTxt, amountTxt, dateTxt, methodTxt;
    private JButton addBtn, updateBtn, deleteBtn, loadBtn;
    private JTable table;
    private DefaultTableModel model;

    public PaymentPanel() {
        setLayout(null);

        // Fields
        paymentIdTxt = new JTextField();
        transactionIdTxt = new JTextField();
        amountTxt = new JTextField();
        dateTxt = new JTextField();
        methodTxt = new JTextField();

        // Buttons
        addBtn = new JButton("Add");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");
        loadBtn = new JButton("Load");

        // Table
        String[] labels = {"Payment ID", "Transaction ID", "Amount", "Date", "Method"};
        model = new DefaultTableModel(labels, 0);
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 250, 750, 200);
        add(sp);

        // Layout fields
        int y = 20;
        addField("Payment ID", paymentIdTxt, y); y += 30;
        addField("Transaction ID", transactionIdTxt, y); y += 30;
        addField("Amount", amountTxt, y); y += 30;
        addField("Date", dateTxt, y); y += 30;
        addField("Method", methodTxt, y); y += 30;

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
                String sql = "INSERT INTO payments(payment_id, transaction_id, amount, date, payment_method) VALUES(?,?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(paymentIdTxt.getText()));
                ps.setInt(2, Integer.parseInt(transactionIdTxt.getText()));
                ps.setDouble(3, Double.parseDouble(amountTxt.getText()));
                ps.setString(4, dateTxt.getText());
                ps.setString(5, methodTxt.getText());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "✅ Payment added successfully!");
            } 
            else if (e.getSource() == updateBtn) {
                String sql = "UPDATE payments SET transaction_id=?, amount=?, date=?, payment_method=? WHERE payment_id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(transactionIdTxt.getText()));
                ps.setDouble(2, Double.parseDouble(amountTxt.getText()));
                ps.setString(3, dateTxt.getText());
                ps.setString(4, methodTxt.getText());
                ps.setInt(5, Integer.parseInt(paymentIdTxt.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "✅ Payment updated successfully!");
            } 
            else if (e.getSource() == deleteBtn) {
                String sql = "DELETE FROM payments WHERE payment_id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(paymentIdTxt.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "🗑️ Payment deleted successfully!");
            } 
            else if (e.getSource() == loadBtn) {
                model.setRowCount(0);
                String sql = "SELECT * FROM payments";
                ResultSet rs = con.createStatement().executeQuery(sql);
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("payment_id"),
                            rs.getInt("transaction_id"),
                            rs.getDouble("amount"),
                            rs.getString("date"),
                            rs.getString("payment_method")
                    });
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Payment Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new PaymentPanel());
        frame.setVisible(true);
    }
}
