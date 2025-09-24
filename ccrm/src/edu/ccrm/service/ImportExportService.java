package edu.ccrm.service;

import edu.ccrm.config.AppConfig;
import edu.ccrm.config.DataStore;
import edu.ccrm.domain.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;
import java.util.Scanner;

/**
 * Very simple CSV-like import/export using NIO.2 and Streams.
 */
public class ImportExportService {
    private final DataStore store = DataStore.getInstance();
    private final Path dataRoot = AppConfig.getInstance().getDataFolder();

    public void menu(Scanner sc) {
        System.out.println("\n-- I/O Menu --");
        System.out.println("1. Export Data (students.csv, courses.csv)");
        System.out.println("2. Import Data (from test-data)");
        System.out.print("Choose: ");
        String ch = sc.nextLine().trim();
        switch (ch) {
            case "1" -> {
                try { exportAll(); } catch (IOException e) { System.out.println("Export failed: " + e.getMessage()); }
            }
            case "2" -> {
                try { importSample(); } catch (IOException e) { System.out.println("Import failed: " + e.getMessage()); }
            }
            default -> System.out.println("Invalid");
        }
    }

    public void exportAll() throws IOException {
        Files.createDirectories(dataRoot);
        Path studentsFile = dataRoot.resolve("students.csv");
        Path coursesFile = dataRoot.resolve("courses.csv");

        List<String> studentsLines = new ArrayList<>();
        for (Student s : store.getStudents()) {
            studentsLines.add(String.join(",", s.getId(), s.getRegNo(), s.getFullName(), s.getEmail(), Boolean.toString(s.isActive())));
        }
        Files.write(studentsFile, studentsLines, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        List<String> courseLines = new ArrayList<>();
        for (Course c : store.getCourses()) {
            courseLines.add(String.join(",", c.getCode(), c.getTitle(), Integer.toString(c.getCredits()), c.getDepartment(), c.getSemester().toString(), c.getInstructor() == null ? "" : c.getInstructor().getFullName()));
        }
        Files.write(coursesFile, courseLines, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        System.out.println("Exported to " + dataRoot.toAbsolutePath());
    }

    public void importSample() throws IOException {
        // looks for test-data in working directory
        Path sample = Paths.get(System.getProperty("user.dir"), "test-data");
        if (!Files.exists(sample)) { System.out.println("No test-data folder found at " + sample); return; }

        Path studentsFile = sample.resolve("students.csv");
        Path coursesFile = sample.resolve("courses.csv");

        if (Files.exists(studentsFile)) {
            try (Stream<String> lines = Files.lines(studentsFile)) {
                lines.forEach(line -> {
                    String[] parts = line.split(",");
                    if (parts.length >= 4) {
                        Student s = new Student(UUID.randomUUID().toString(), parts[2], parts[3], parts[1], java.time.LocalDate.now());
                        store.getStudents().add(s);
                    }
                });
            }
            System.out.println("Imported students from sample.");
        } else {
            System.out.println("students.csv not found in test-data.");
        }

        if (Files.exists(coursesFile)) {
            try (Stream<String> lines = Files.lines(coursesFile)) {
                lines.forEach(line -> {
                    String[] p = line.split(",");
                    if (p.length >= 3) {
                        Course c = new Course.Builder(p[0]).title(p[1]).credits(Integer.parseInt(p[2])).department(p.length>3?p[3]:"General").build();
                        store.getCourses().add(c);
                    }
                });
            }
            System.out.println("Imported courses from sample.");
        } else {
            System.out.println("courses.csv not found in test-data.");
        }
    }
}
