package com.realestate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;

public class LoginForm extends JFrame implements ActionListener {
	
	JTextField usertxt=new JTextField("enter username");
	JPasswordField passtxt=new JPasswordField("password");
	JButton loginbtn=new JButton("login");
	JButton cancelbtn=new JButton("cancel");
	
	
	//constructor
	public LoginForm(){
		setTitle("Login Form");
		setBounds(100,100,400,400);
		setLayout(null);
		usertxt.setBounds(50,30,120,25);
		passtxt.setBounds(50,70,120,25);
		
		loginbtn.setBounds(50,120,100,30);
		cancelbtn.setBounds(160,120,100,30);
		
		add(usertxt);
		add(passtxt);
		add(loginbtn);
		add(cancelbtn);
		
		loginbtn.addActionListener(this);
		cancelbtn.addActionListener(this);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
	
	
	public void actionPerformed(ActionEvent arg0) {
		try(Connection con=DB.getConnection()){
			
			String sql="select* FROM user WHERE username=? AND password=?";
			
		PreparedStatement ps=con.prepareCall(sql);
		ps.setString(1,usertxt.getText());
		ps.setString(2, new String(passtxt.getPassword()));
		ResultSet rs=ps.executeQuery();
		
		if(rs.next()){
			String role=rs.getString("role");
			dispose();
			
			new SMIS(role,rs.getInt("userid"));
				
		}
		
		else{
			JOptionPane.showMessageDialog(this, "invalid login");
		}
			
		}
		
		catch(Exception ex){
			ex.printStackTrace();
		    }
	}



}