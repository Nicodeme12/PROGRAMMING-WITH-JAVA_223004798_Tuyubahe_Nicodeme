package com.allinone;
import java.util.Scanner;
public class studentgrading  {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Student Grading System ===");
        System.out.println("Enter student marks (0-100) or -1 to finish");
        
        // Initialize counters
        int totalStudents = 0;
        int passCount = 0;
        int failCount = 0;
        
        while (true) {
            System.out.print("\nEnter mark for student " + (totalStudents + 1) + ": ");
            
            // Check if input is a valid number
            
            if (!scanner.hasNextDouble()) {
                System.out.println("Please enter a valid number");
                scanner.next(); // Clear the invalid input
                continue;
            }
            
            double mark = scanner.nextDouble();
            
            // Check for sentinel value
            if (mark == -1) {
                break;
            }
            
            // Validate mark range
            if (mark < 0 || mark > 100) {
                System.out.println("Please enter a mark between 0 and 100");
                continue;
            }
            
            // Process valid mark
            totalStudents++;
            String grade;
            
            // Determine grade
            if (mark >= 80) {
                grade = "A";
                passCount++;
            } else if (mark >= 70) {
                grade = "B";
                passCount++;
            } else if (mark >= 60) {
                grade = "C";
                passCount++;
            } else if (mark >= 50) {
                grade = "D";
                passCount++;
            } else {
                grade = "F";
                failCount++;
            }
            
            // Print student grade
            System.out.println("Student " + totalStudents + " - Mark: " + mark + ", Grade: " + grade);
        }
        
        // Generate summary report
        System.out.println("\n=== SUMMARY REPORT ===");
        System.out.println("Total students: " + totalStudents);
        System.out.println("Passed: " + passCount);
        System.out.println("Failed: " + failCount);
        
        // Calculate and display pass rate (avoid division by zero)
        if (totalStudents > 0) {
            double passRate = (double) passCount / totalStudents * 100;
            System.out.printf("Pass rate: %.2f%\n", passRate);
        } else {
            System.out.println("No student data entered");
        }
        
        scanner.close();
    }
}