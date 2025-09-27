package com.realestate;

import java.awt.BorderLayout;

import javax.swing.*;

public class SMIS extends JFrame{
	JTabbedPane tabs=new JTabbedPane();
	
	//constructor
	
	public SMIS(String role, int userid){
		setTitle("school management system");
		setSize(900,600);
		setLayout(new BorderLayout());
		if(role.equalsIgnoreCase("admin")){
			tabs.add("users",new UserPanel());
//			tabs.add("teachers",new TeacherPanel());
//			tabs.add("courses",new CoursePanel());
//			tabs.add("students",new StudentPanel());
//			tabs.add("marks",new MarkPanel());
			
			
		}
		
		else if(role.equalsIgnoreCase("teacher")){
//			tabs.add("courses",new CoursePanel());
//			tabs.add("marks",new MarkPanel());
			
		}
		else if(role.equalsIgnoreCase("students")){
//			tabs.add("my marks",new MarkPanel());
		}
		add(tabs,BorderLayout.CENTER);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	
	
	
}
}