package edu.ccrm.cli;

import java.util.Scanner;
import edu.ccrm.service.*;
import edu.ccrm.config.AppConfig;

/**
 * Entry point - menu loop delegates to services.
 */
public class MainCLI {
    public static void main(String[] args) {
        AppConfig.getInstance().initDefaults();

        Scanner sc = new Scanner(System.in);
        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();
        EnrollmentService enrollmentService = new EnrollmentService();
        ImportExportService ioService = new ImportExportService();
        BackupService backupService = new BackupService();

        boolean running = true;
        while (running) {
            System.out.println("\n--- Campus Course & Records Manager (CCRM) ---");
            System.out.println("1. Student Management");
            System.out.println("2. Courses Management");
            System.out.println("3. Enrollments & Grades");
            System.out.println("4. Import / Export Data");
            System.out.println("5. Backup & Reports");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> studentService.menu(sc);
                case "2" -> courseService.menu(sc);
                case "3" -> enrollmentService.menu(sc);
                case "4" -> ioService.menu(sc);
                case "5" -> {
                    backupService.createBackup();
                    enrollmentService.printGpaDistribution();
                }
                case "0" -> running = false;
                default -> System.out.println("Invalid choice, try again.");
            }
        }
        sc.close();
        System.out.println("Goodbye!");
    }
}