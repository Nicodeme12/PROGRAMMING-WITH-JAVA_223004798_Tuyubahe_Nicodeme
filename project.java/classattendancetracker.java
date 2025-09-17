package Supermarket;
import java.util.Scanner;
public class classattendancetracker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Class Attendance Tracker ===");
        
        // Get total number of students
        int totalStudents = 0;
        while (true) {
            System.out.print("Enter the total number of students in the class: ");
            if (scanner.hasNextInt()) {
                totalStudents = scanner.nextInt();
                if (totalStudents > 0) {
                    break;
                } else {
                    System.out.println("Please enter a positive number.");
                }
            } else {
                System.out.println("Please enter a valid number.");
                scanner.next(); // Clear invalid input
            }
        }
        
        // Initialize array for attendance (max 30 days)
        int[] attendance = new int[30];
        int dayCount = 0;
        String continueEntry;
        
        // Do-while loop to record attendance
        do {
            System.out.println("\n--- Day " + (dayCount + 1) + " ---");
            
            // Get number of students present
            int present = -1;
            while (present < 0 || present > totalStudents) {
                System.out.print("Enter number of students present: ");
                if (scanner.hasNextInt()) {
                    present = scanner.nextInt();
                    if (present < 0 || present > totalStudents) {
                        System.out.println("Please enter a value between 0 and " + totalStudents);
                    }
                } else {
                    System.out.println("Please enter a valid number.");
                    scanner.next(); // Clear invalid input
                }
            }
            
            // Store attendance for the day
            attendance[dayCount] = present;
            dayCount++;
            
            // Ask if user wants to continue
            if (dayCount < 30) {
                System.out.print("Do you want to enter attendance for another day? (yes/no): ");
                continueEntry = scanner.next().toLowerCase();
            } else {
                System.out.println("Maximum of 30 days reached.");
                continueEntry = "no";
            }
            
        } while (continueEntry.equals("yes") && dayCount < 30);
        
        // Calculate statistics
        int totalPresent = 0;
        int lowAttendanceDays = 0;
        double fiftyPercentThreshold = totalStudents * 0.5;
        
        for (int i = 0; i < dayCount; i++) {
            totalPresent += attendance[i];
            if (attendance[i] < fiftyPercentThreshold) {
                lowAttendanceDays++;
            }
        }
        
        double averageAttendance = dayCount > 0 ? (double) totalPresent / dayCount : 0;
        double lowAttendancePercentage = dayCount > 0 ? (double) lowAttendanceDays / dayCount * 100 : 0;
        
        // Print attendance table
        System.out.println("\n=== ATTENDANCE REPORT ===");
        System.out.println("Total students: " + totalStudents);
        System.out.println("Days recorded: " + dayCount);
        System.out.println("\nDaily Attendance:");
        System.out.println("Day\tPresent\tPercentage");
        System.out.println("----------------------------");
        
        for (int i = 0; i < dayCount; i++) {
            double dayPercentage = (double) attendance[i] / totalStudents * 100;
            System.out.printf("%d\t%d\t%.1f%%%n", i + 1, attendance[i], dayPercentage);
        }
        
        // Print summary statistics
        System.out.println("\n=== SUMMARY STATISTICS ===");
        System.out.printf("Average daily attendance: %.2f students%n", averageAttendance);
        System.out.printf("Days with low attendance (<50%%): %d%n", lowAttendanceDays);
        System.out.printf("Percentage of days with low attendance: %.1f%%%n", lowAttendancePercentage);
        
        scanner.close();
    }
}
