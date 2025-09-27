package com.realestate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class DocumentPanel extends JPanel implements ActionListener {

    private JTextField docIdTxt, propertyIdTxt, clientIdTxt, transactionIdTxt, dateTxt;
    private JButton addBtn, updateBtn, deleteBtn, loadBtn;
    private JTable table;
    private DefaultTableModel model;

    public DocumentPanel() {
        setLayout(null);

        // Fields
        docIdTxt = new JTextField();
        propertyIdTxt = new JTextField();
        clientIdTxt = new JTextField();
        transactionIdTxt = new JTextField();
        dateTxt = new JTextField();

        // Buttons
        addBtn = new JButton("Add");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");
        loadBtn = new JButton("Load");

        // Table
        String[] labels = {"Document ID", "Property ID", "Client ID", "Transaction ID", "Uploaded Date"};
        model = new DefaultTableModel(labels, 0);
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 250, 750, 200);
        add(sp);

        // Layout fields
        int y = 20;
        addField("Document ID", docIdTxt, y); y += 30;
        addField("Property ID", propertyIdTxt, y); y += 30;
        addField("Client ID", clientIdTxt, y); y += 30;
        addField("Transaction ID", transactionIdTxt, y); y += 30;
        addField("Uploaded Date", dateTxt, y); y += 30;

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
                String sql = "INSERT INTO documents(document_id, Property_id, client_id, transaction_id, uploaded_date) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(docIdTxt.getText()));
                ps.setInt(2, Integer.parseInt(propertyIdTxt.getText()));
                ps.setInt(3, Integer.parseInt(clientIdTxt.getText()));
                ps.setInt(4, Integer.parseInt(transactionIdTxt.getText()));
                ps.setString(5, dateTxt.getText()); // Format: YYYY-MM-DD
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Document added successfully!");
            } 
            else if (e.getSource() == updateBtn) {
                String sql = "UPDATE documents SET Property_id=?, client_id=?, transaction_id=?, uploaded_date=? WHERE document_id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(propertyIdTxt.getText()));
                ps.setInt(2, Integer.parseInt(clientIdTxt.getText()));
                ps.setInt(3, Integer.parseInt(transactionIdTxt.getText()));
                ps.setString(4, dateTxt.getText());
                ps.setInt(5, Integer.parseInt(docIdTxt.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Document updated successfully!");
            } 
            else if (e.getSource() == deleteBtn) {
                String sql = "DELETE FROM documents WHERE document_id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(docIdTxt.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Document deleted successfully!");
            } 
            else if (e.getSource() == loadBtn) {
                model.setRowCount(0);
                String sql = "SELECT * FROM documents";
                ResultSet rs = con.createStatement().executeQuery(sql);
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("document_id"),
                            rs.getInt("Property_id"),
                            rs.getInt("client_id"),
                            rs.getInt("transaction_id"),
                            rs.getString("uploaded_date")
                    });
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
    	LoginForm login = new LoginForm();
        JFrame frame = new JFrame("Document Panel");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new DocumentPanel());
        frame.setVisible(true);
    }
}
