package com.realestate;

import javax.swing.*;

public class MainDashboard extends JFrame {

    public MainDashboard(String role, int userId) {
        setTitle("Real Estate Management System - Dashboard");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Agencies", new AgencyPanel());
        tabs.add("Properties", new PropertyPanel());
        tabs.add("Transactions", new Transactions());
        tabs.add("Payments", new PaymentPanel());
        tabs.add("Documents", new DocumentPanel());

        add(tabs);
        setVisible(true);
    }
}
