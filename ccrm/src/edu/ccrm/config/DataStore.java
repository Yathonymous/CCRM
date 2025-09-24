package edu.ccrm.config;

import java.util.ArrayList;
import java.util.List;
import edu.ccrm.domain.*;

/**
 * In-memory singleton store (replaces a DB).
 */
public class DataStore {
    private static DataStore instance;

    private final List<Student> students = new ArrayList<>();
    private final List<Course> courses = new ArrayList<>();
    private final List<Enrollment> enrollments = new ArrayList<>();
    private final List<Instructor> instructors = new ArrayList<>();

    private DataStore() {}

    public static synchronized DataStore getInstance() {
        if (instance == null) instance = new DataStore();
        return instance;
    }

    public List<Student> getStudents() { return students; }
    public List<Course> getCourses() { return courses; }
    public List<Enrollment> getEnrollments() { return enrollments; }
    public List<Instructor> getInstructors() { return instructors; }
}

