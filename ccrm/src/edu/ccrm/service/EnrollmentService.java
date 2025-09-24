package edu.ccrm.service;

import edu.ccrm.config.DataStore;
import edu.ccrm.domain.*;
import edu.ccrm.exception.*;
import java.util.Scanner;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles enroll/unenroll, assign grade, compute GPA.
 */
public class EnrollmentService {
    private final DataStore store = DataStore.getInstance();
    private final StudentService studentService = new StudentService();
    private final CourseService courseService = new CourseService();
    private static final int MAX_CREDITS_PER_SEM = 18;

    public void menu(Scanner sc) {
        System.out.println("\n-- Enrollment Menu --");
        System.out.println("1. Enroll student");
        System.out.println("2. Unenroll student");
        System.out.println("3. Assign grade");
        System.out.println("4. Print transcript");
        System.out.print("Choose: ");
        String ch = sc.nextLine().trim();
        switch (ch) {
            case "1" -> enroll(sc);
            case "2" -> unenroll(sc);
            case "3" -> assignGrade(sc);
            case "4" -> printTranscript(sc);
            default -> System.out.println("Invalid");
        }
    }

    public void enroll(Scanner sc) {
        try {
            System.out.print("Student RegNo: ");
            String reg = sc.nextLine().trim();
            Student s = studentService.findByRegNo(reg);
            if (s == null) { System.out.println("Student not found"); return; }

            System.out.print("Course code: ");
            String code = sc.nextLine().trim();
            Course c = courseService.findByCode(code);
            if (c == null) { System.out.println("Course not found"); return; }

            // check duplicate
            boolean dup = store.getEnrollments().stream()
                    .anyMatch(e -> e.getStudent().getRegNo().equalsIgnoreCase(reg) && e.getCourse().getCode().equalsIgnoreCase(code));
            if (dup) throw new DuplicateEnrollmentException("Student already enrolled in this course.");

            // check max credits
            int currentCredits = store.getEnrollments().stream()
                    .filter(e -> e.getStudent().getRegNo().equalsIgnoreCase(reg))
                    .mapToInt(e -> e.getCourse().getCredits()).sum();
            if (currentCredits + c.getCredits() > MAX_CREDITS_PER_SEM) {
                throw new MaxCreditLimitExceededException("Enrolling would exceed max credits (" + MAX_CREDITS_PER_SEM + ").");
            }

            Enrollment e = new Enrollment(s, c);
            store.getEnrollments().add(e);
            s.addEnrollment(e);
            System.out.println("Enrolled: " + e);
        } catch (DuplicateEnrollmentException | MaxCreditLimitExceededException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void unenroll(Scanner sc) {
        System.out.print("Student RegNo: ");
        String reg = sc.nextLine().trim();
        System.out.print("Course code: ");
        String code = sc.nextLine().trim();
        List<Enrollment> toRemove = store.getEnrollments().stream()
                .filter(e -> e.getStudent().getRegNo().equalsIgnoreCase(reg) && e.getCourse().getCode().equalsIgnoreCase(code))
                .collect(Collectors.toList());
        if (toRemove.isEmpty()) {
            System.out.println("No such enrollment.");
            return;
        }
        toRemove.forEach(e -> {
            e.getStudent().removeEnrollment(e);
            store.getEnrollments().remove(e);
            System.out.println("Removed: " + e);
        });
    }

    public void assignGrade(Scanner sc) {
        System.out.print("Student RegNo: ");
        String reg = sc.nextLine().trim();
        System.out.print("Course code: ");
        String code = sc.nextLine().trim();
        Enrollment e = store.getEnrollments().stream()
                .filter(en -> en.getStudent().getRegNo().equalsIgnoreCase(reg) && en.getCourse().getCode().equalsIgnoreCase(code))
                .findFirst().orElse(null);
        if (e == null) { System.out.println("Enrollment not found"); return; }
        System.out.print("Grade letter (S/A/B/C/F): ");
        String g = sc.nextLine().trim().toUpperCase();
        Grade grade = switch (g) {
            case "S" -> Grade.S;
            case "A" -> Grade.A;
            case "B" -> Grade.B;
            case "C" -> Grade.C;
            default -> Grade.F;
        };
        e.assignGrade(grade);
        System.out.println("Assigned: " + e);
    }

    public void printTranscript(Scanner sc) {
        System.out.print("Student RegNo: ");
        String reg = sc.nextLine().trim();
        Student s = studentService.findByRegNo(reg);
        if (s == null) { System.out.println("Not found"); return; }
        printTranscriptForStudent(s);
    }

    public void printTranscriptForStudent(Student s) {
        System.out.println("\nTranscript for: " + s.getFullName() + " (" + s.getRegNo() + ")");
        List<Enrollment> ens = store.getEnrollments().stream()
                .filter(e -> e.getStudent().getRegNo().equalsIgnoreCase(s.getRegNo()))
                .collect(Collectors.toList());
        if (ens.isEmpty()) { System.out.println("No enrollments."); return; }
        ens.forEach(e -> System.out.println(e.getCourse().getCode() + " | " + e.getCourse().getTitle() +
                " | credits=" + e.getCourse().getCredits() + " | grade=" + (e.getGrade() == null ? "N/A" : e.getGrade())));
        double gpa = computeGpa(ens);
        System.out.println("GPA: " + String.format("%.2f", gpa));
    }

    public double computeGpa(List<Enrollment> enrollments) {
        double totalPoints = 0.0;
        int totalCredits = 0;
        for (Enrollment e : enrollments) {
            Grade g = e.getGrade();
            if (g != null) {
                totalPoints += g.getPoints() * e.getCourse().getCredits();
                totalCredits += e.getCourse().getCredits();
            }
        }
        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }

    public void printGpaDistribution() {
        System.out.println("\n-- GPA Report --");
        // compute GPA per student
        var gpamap = store.getStudents().stream()
                .collect(Collectors.toMap(s -> s.getRegNo(), s -> computeGpa(
                        store.getEnrollments().stream().filter(e -> e.getStudent().getRegNo().equals(s.getRegNo())).collect(Collectors.toList())
                )));
        if (gpamap.isEmpty()) {
            System.out.println("No data.");
            return;
        }
        gpamap.forEach((reg, gpa) -> System.out.println(reg + " => " + String.format("%.2f", gpa)));
    }
}
