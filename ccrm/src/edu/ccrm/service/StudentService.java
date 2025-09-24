package edu.ccrm.service;

import edu.ccrm.config.DataStore;
import edu.ccrm.domain.Student;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;

public class StudentService {
    private final DataStore store = DataStore.getInstance();

    public void menu(Scanner sc) {
        System.out.println("\n-- Student Menu --");
        System.out.println("1. Add Student");
        System.out.println("2. List Students");
        System.out.println("3. Deactivate Student");
        System.out.print("Choose: ");
        String ch = sc.nextLine().trim();
        switch (ch) {
            case "1" -> addStudent(sc);
            case "2" -> listStudents();
            case "3" -> deactivateStudent(sc);
            default -> System.out.println("Invalid");
        }
    }

    public void addStudent(Scanner sc) {
        System.out.print("Full name: ");
        String name = sc.nextLine().trim();
        System.out.print("Email: ");
        String email = sc.nextLine().trim();
        System.out.print("RegNo: ");
        String regNo = sc.nextLine().trim();
        String id = UUID.randomUUID().toString();
        Student s = new Student(id, name, email, regNo, LocalDate.now());
        store.getStudents().add(s);
        System.out.println("Added: " + s);
    }

    public void listStudents() {
        if (store.getStudents().isEmpty()) {
            System.out.println("No students.");
            return;
        }
        store.getStudents().forEach(System.out::println);
    }

    public Student findByRegNo(String regNo) {
        return store.getStudents().stream()
                .filter(st -> st.getRegNo().equalsIgnoreCase(regNo))
                .findFirst().orElse(null);
    }

    private void deactivateStudent(Scanner sc) {
        System.out.print("RegNo to deactivate: ");
        String reg = sc.nextLine().trim();
        Student s = findByRegNo(reg);
        if (s == null) { System.out.println("Not found"); return; }
        s.deactivate();
        System.out.println("Deactivated: " + s.getRegNo());
    }
}
