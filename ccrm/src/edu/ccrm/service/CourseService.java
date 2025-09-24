package edu.ccrm.service;

import edu.ccrm.config.DataStore;
import edu.ccrm.domain.*;
import java.util.Scanner;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Course management: create/list/search/assign instructor
 */
public class CourseService {
    private final DataStore store = DataStore.getInstance();

    public void menu(Scanner sc) {
        System.out.println("\n-- Course Menu --");
        System.out.println("1. Add Course");
        System.out.println("2. List Courses");
        System.out.println("3. Search Courses (by dept/instructor/semester)");
        System.out.print("Choose: ");
        String ch = sc.nextLine().trim();
        switch (ch) {
            case "1" -> addCourse(sc);
            case "2" -> listCourses();
            case "3" -> searchCourses(sc);
            default -> System.out.println("Invalid");
        }
    }

    public void addCourse(Scanner sc) {
        System.out.print("Course code: ");
        String code = sc.nextLine().trim();
        System.out.print("Title: ");
        String title = sc.nextLine().trim();
        System.out.print("Credits: ");
        int credits = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Department: ");
        String dept = sc.nextLine().trim();
        System.out.print("Semester (SPRING/SUMMER/FALL): ");
        String sem = sc.nextLine().trim().toUpperCase();

        Semester semester = switch (sem) {
            case "SUMMER" -> Semester.SUMMER;
            case "FALL" -> Semester.FALL;
            default -> Semester.SPRING;
        };

        // optionally create a simple instructor
        Instructor instr = null;
        System.out.print("Assign instructor? (y/n): ");
        String a = sc.nextLine().trim();
        if (a.equalsIgnoreCase("y")) {
            System.out.print("Instructor name: ");
            String nm = sc.nextLine().trim();
            System.out.print("Instructor email: ");
            String em = sc.nextLine().trim();
            System.out.print("Department: ");
            String d = sc.nextLine().trim();
            instr = new Instructor(UUID.randomUUID().toString(), nm, em, d);
            store.getInstructors().add(instr);
        }

        Course course = new Course.Builder(code)
                .title(title)
                .credits(credits)
                .department(dept)
                .instructor(instr)
                .semester(semester)
                .build();

        store.getCourses().add(course);
        System.out.println("Added: " + course);
    }

    public void listCourses() {
        if (store.getCourses().isEmpty()) {
            System.out.println("No courses.");
            return;
        }
        store.getCourses().forEach(System.out::println);
    }

    private void searchCourses(Scanner sc) {
        System.out.print("Filter by (dept/instructor/semester): ");
        String f = sc.nextLine().trim().toLowerCase();
        List<Course> results = store.getCourses();
        switch (f) {
            case "dept" -> {
                System.out.print("Department: ");
                String dept = sc.nextLine().trim();
                results = results.stream().filter(c -> c.getDepartment().equalsIgnoreCase(dept)).collect(Collectors.toList());
            }
            case "instructor" -> {
                System.out.print("Instructor name: ");
                String instr = sc.nextLine().trim();
                results = results.stream()
                        .filter(c -> c.getInstructor() != null && c.getInstructor().getFullName().equalsIgnoreCase(instr))
                        .collect(Collectors.toList());
            }
            case "semester" -> {
                System.out.print("Semester (SPRING/SUMMER/FALL): ");
                String sem = sc.nextLine().trim().toUpperCase();
                Semester s = sem.equals("FALL") ? Semester.FALL : sem.equals("SUMMER") ? Semester.SUMMER : Semester.SPRING;
                results = results.stream().filter(c -> c.getSemester().toString().equals(s.toString())).collect(Collectors.toList());
            }
            default -> System.out.println("Unknown filter.");
        }
        if (results.isEmpty()) System.out.println("No matches.");
        else results.forEach(System.out::println);
    }

    public Course findByCode(String code) {
        return store.getCourses().stream().filter(c -> c.getCode().equalsIgnoreCase(code)).findFirst().orElse(null);
    }
}
