package com.realestate;

import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class LoginForm extends JFrame implements ActionListener {

    private JTextField userTxt;
    private JPasswordField passTxt;
    private JButton loginBtn, cancelBtn;

    public LoginForm() {
        setTitle("Login Form");
        setBounds(100, 100, 350, 200);
        setLayout(null);

        JLabel userLbl = new JLabel("Username:");
        userLbl.setBounds(30, 30, 80, 25);
        add(userLbl);

        userTxt = new JTextField();
        userTxt.setBounds(120, 30, 150, 25);
        add(userTxt);

        JLabel passLbl = new JLabel("Password:");
        passLbl.setBounds(30, 70, 80, 25);
        add(passLbl);

        passTxt = new JPasswordField();
        passTxt.setBounds(120, 70, 150, 25);
        add(passTxt);

        loginBtn = new JButton("Login");
        loginBtn.setBounds(50, 120, 100, 30);
        cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(170, 120, 100, 30);

        add(loginBtn);
        add(cancelBtn);

        loginBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginBtn) {
            login();
        } else if (e.getSource() == cancelBtn) {
            System.exit(0);
        }
    }

    private void login() {
        try (Connection con = DB.getConnection()) {
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, userTxt.getText().trim());
            ps.setString(2, new String(passTxt.getPassword()));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                int userId = rs.getInt("userid");

                JOptionPane.showMessageDialog(this, "✅ Login Successful! Welcome " + role);

                dispose(); // close login
                new MainDashboard(role, userId); // go to main dashboard
            } else {
                JOptionPane.showMessageDialog(this, "❌ Invalid Username or Password");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new LoginForm();
    }
}
